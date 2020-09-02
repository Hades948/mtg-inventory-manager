package com.tylerroyer.mtg.inventorymanager.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.tylerroyer.mtg.Card;

import org.json.JSONObject;

public class Inventory {
    private static ArrayList<Card> cards;

    public static void init() {
        cards = new ArrayList<>();
    }

    public static void loadInventory() {
        File dir = new File("./cards");
        for (File file : dir.listFiles()) {
            try (Scanner scanner = new Scanner(new FileReader(file))) {
                String data = "";
                while(scanner.hasNextLine()) {
                    data += scanner.nextLine();
                }
                JSONObject json = new JSONObject(data);
                cards.add(new Card(json));
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public static void addCardToInventory(Card newCard, boolean isFoil) {
        int index = findCard(newCard.getScryfallUUID());

        if (index == -1) { // New card
            if (isFoil) {
                newCard.setFoilQuantity(newCard.getFoilQuantity());
            } else {
                newCard.setQuantity(newCard.getQuantity());
            }
            cards.add(newCard);
            saveCard(newCard);
        } else { // Card already exists
            if (isFoil) {
                cards.get(index).setFoilQuantity(cards.get(index).getFoilQuantity() + newCard.getFoilQuantity());
            } else {
                cards.get(index).setQuantity(cards.get(index).getQuantity() + newCard.getQuantity());
            }
            saveCard(cards.get(index));
        }
    }

    private static void saveCard(Card card) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./cards/" + card.getScryfallUUID() + ".dat"))) {
            out.write(card.toJSON().toString());
        } catch (Exception e) {e.printStackTrace();}
    }

    public static int findCard(String uuid) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getScryfallUUID().equals(uuid))
                return i;
        }

        return -1;
    }

    public static ArrayList<Card> getCards() {
        return cards;
    }

    public static int getNumberOfUniqueCards() {
        return getCards().size();
    }

    public static int getTotalNumberOfCards() {
        int total = 0;
        for (Card card : getCards()) {
            total += card.getQuantity() + card.getFoilQuantity();
        }
        return total;
    }

    public static float getTotalValueOfCards() {
        float total = 0.0f;
        for (Card card : getCards()) {
            total += card.getTotalValue();
        }
        return total;
    }

    public static float getAverageValueOfCards() {
        return getTotalValueOfCards() / getTotalNumberOfCards();
    }
}