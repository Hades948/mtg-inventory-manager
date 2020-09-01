package com.tylerroyer.mtg.inventorymanager.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.tylerroyer.mtg.Card;
import com.tylerroyer.mtg.inventorymanager.main.ConfirmCardWindow;

import org.json.JSONObject;

public class Scryfall {
    private static final String API_URL = "https://api.scryfall.com";
    private static final String CARD_REQUEST_ENDPOINT = "/cards/collection";

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
            // Don't do too many requests.  This should be dynamic.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {e1.printStackTrace();}

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
                card.setType(data.getString("type_line"));
                card.setSet(data.getString("set"));
                card.setSetName(data.getString("set_name"));
                card.setCollectorNumber(data.getString("collector_number"));
                card.setImageUrl(data.getJSONObject("image_uris").getString("normal"));
                card.setScryfallUUID(data.getString("id"));
                card.setPrice(Float.parseFloat(data.getJSONObject("prices").getString("usd")));
                card.setFoilPrice(Float.parseFloat(data.getJSONObject("prices").getString("usd_foil")));
                card.setColors(data.getJSONArray("colors"));

            } catch (Exception e) {
                e.printStackTrace();
                returnWindow.onCardResult(null);
                return;
            }

            returnWindow.onCardResult(card);
        }
        
    }
}