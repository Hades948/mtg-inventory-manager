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
        GetCardThread getCardThread = new GetCardThread(returnWindow, rawCardInfo);
        getCardThread.start();
    }

    private static class GetCardThread extends Thread {
        private ConfirmCardWindow returnWindow;
        private String rawCardInfo;

        public GetCardThread(ConfirmCardWindow returnWindow, String rawCardInfo) {
            this.returnWindow = returnWindow;
            this.rawCardInfo = rawCardInfo;
        }

        @Override
        public void run() {
            // Don't do too many requests.
            long elapsed = System.currentTimeMillis() - timeOfLastPing;
            if (elapsed < 100) {
                try {
                    Thread.sleep(100 - elapsed);
                } catch (InterruptedException e1) {e1.printStackTrace();}
            }
            timeOfLastPing = System.currentTimeMillis();

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

                // Write body to connection.
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = body.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception e) {e.printStackTrace();};

                // Get response.
                StringBuilder response;
                try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                    response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }

                // Put response in JSON object
                JSONObject json = new JSONObject(response.toString());
                JSONObject data = json.getJSONArray("data").getJSONObject(0);
                card.setName(data.getString("name"));
                card.setType(data.getString("type_line").replace("—", "-"));
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
                card.setPrice(prices.getFloat("usd"));
                try {
                    card.setFoilPrice(prices.getFloat("usd_foil"));
                } catch (JSONException e) {
                    e.printStackTrace();
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

            } catch (Exception e) {
                e.printStackTrace();
                returnWindow.onCardResult(null);
                return;
            }

            returnWindow.onCardResult(card);
        }
        
    }
}