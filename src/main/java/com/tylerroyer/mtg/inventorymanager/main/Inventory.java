package com.tylerroyer.mtg.inventorymanager.main;

import java.io.BufferedReader;
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

    public static void addCardToInventory(Card card, boolean isFoil) {
        int index = findCard(card.getScryfallUUID());

        if (index == -1) { // New card
            if (isFoil) {
                card.setFoilQuantity(1);
            } else {
                card.setQuantity(1);
            }
            cards.add(card);
            saveCard(card);
        } else { // Card already exists
            if (isFoil) {
                cards.get(index).incrementFoilQuantity();
            } else {
                cards.get(index).incrementQuantity();
            }
            saveCard(cards.get(index));
        }
        
        System.out.println(cards);
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

}