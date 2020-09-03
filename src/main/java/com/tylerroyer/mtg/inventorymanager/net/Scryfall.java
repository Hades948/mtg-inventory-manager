package com.tylerroyer.mtg.inventorymanager.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.tylerroyer.mtg.Card;
import com.tylerroyer.mtg.inventorymanager.main.ConfirmCardWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Scryfall {
    private static final String API_URL = "https://api.scryfall.com";
    private static final String CARD_REQUEST_ENDPOINT = "/cards/collection";

    private static long timeOfLastPing = 0L;

    public static void getCard(ConfirmCardWindow returnWindow, String rawCardInfo) {
        Card card = new Card();
        String[] cardData = rawCardInfo.split(" ");
        if (cardData.length < 2) {
            returnWindow.onCardResult(null);
            return;
        }
        String set = cardData[0];
        String collectorNumber = cardData[1];

        // Remove leading zeros in collector number.
        if (collectorNumber.charAt(0) == '0') {
            collectorNumber = collectorNumber.substring(1);
            if (collectorNumber.length() > 0 && collectorNumber.charAt(0) == '0') {
                collectorNumber = collectorNumber.substring(1);
            }
        }
        
        try {
            // Set up connection
            URL url = new URL(API_URL + CARD_REQUEST_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String body = "{\"identifiers\": [{\"set\": \"" + set + "\",\"collector_number\": \"" + collectorNumber + "\"}]}";

            loadCard(connection, body, card);
        } catch (Exception e) {
            e.printStackTrace();
            returnWindow.onCardResult(null);
            return;
        }

        returnWindow.onCardResult(card);
    }

    public static void updateCard(Card card) {
        try {
            // Set up connection
            URL url = new URL(API_URL + CARD_REQUEST_ENDPOINT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            String body = "{\"identifiers\": [{\"id\": \"" + card.getScryfallUUID() + "\"}]}";

            while(!loadCard(connection, body, card));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private static boolean loadCard(HttpURLConnection connection, String postBody, Card card)  {
        doBusy();

        // Write body to connection.
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = postBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (Exception e) {e.printStackTrace();};

        // Get response.
        StringBuilder response = new StringBuilder();
        try {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        // Put response in JSON object
        JSONObject json = new JSONObject(response.toString());
        JSONObject data = json.getJSONArray("data").getJSONObject(0);

        // Name
        String name = data.getString("name");
        int slashIndex = name.indexOf("//");
        if (slashIndex >= 0) {
            name = name.substring(0, slashIndex);
        }
        card.setName(name);

        // Type
        String type = data.getString("type_line").replace("—", "-");
        slashIndex = type.indexOf(" // ");
        if (slashIndex >= 0) {
            type = type.substring(0, slashIndex);
        }
        card.setType(type);

        card.setSet(data.getString("set"));
        card.setSetName(data.getString("set_name"));

        // Collector number
        String collectorNum = data.getString("collector_number");
        while (collectorNum.length() < 3) {
            collectorNum = "0" + collectorNum;
        }
        card.setCollectorNumber(collectorNum);
        
        card.setImageUrl(data.getJSONObject("image_uris").getString("normal"));
        card.setScryfallUUID(data.getString("id"));

        // Prices
        JSONObject prices = data.getJSONObject("prices");
        try {
            card.setPrice(prices.getFloat("usd"));
        } catch (JSONException e) {
            System.out.println("No price listed.  Ignoring.");
            card.setPrice(0.0f);
        }
        try {
            card.setFoilPrice(prices.getFloat("usd_foil"));
        } catch (JSONException e) {
            System.out.println("No foil price listed.  Ignoring.");
            card.setFoilPrice(0.0f);
        }

        // Colors
        JSONArray shortColors = data.getJSONArray("colors");
        JSONArray longColors = new JSONArray();
        for (int i = 0; i < shortColors.length(); i++) {
            if (shortColors.get(i).equals("W")) {
                longColors.put("White");
            } else if (shortColors.get(i).equals("U")) {
                longColors.put("Blue");
            } else if (shortColors.get(i).equals("B")) {
                longColors.put("Black");
            } else if (shortColors.get(i).equals("R")) {
                longColors.put("Red");
            } else if (shortColors.get(i).equals("G")) {
                longColors.put("Green");
            } else {
                longColors.put("Unkn.");
            }
        }
        if (shortColors.length() == 0) {
            longColors.put("None");
        }
        card.setColors(longColors);

        return true;
    }

    private static void doBusy() {
        // Don't do too many requests.
        final int WAIT = 200;
        long elapsed = System.currentTimeMillis() - timeOfLastPing;
        if (elapsed < WAIT) {
            try {
                Thread.sleep(WAIT - elapsed);
            } catch (InterruptedException e1) {e1.printStackTrace();}
        }
        timeOfLastPing = System.currentTimeMillis();
    }
}