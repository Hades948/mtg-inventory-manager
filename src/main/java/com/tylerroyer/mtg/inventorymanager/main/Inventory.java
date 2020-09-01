package com.tylerroyer.mtg.inventorymanager.main;

import java.util.ArrayList;
import com.tylerroyer.mtg.Card;

public class Inventory {
    private static ArrayList<Card> cards;

    public static void init() {
        cards = new ArrayList<>();
    }

    public static void loadInventory() {
        // TODO
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
        } else { // Card already exists
            if (isFoil) {
                cards.get(index).incrementFoilQuantity();
            } else {
                cards.get(index).incrementQuantity();
            }
        }

        // TODO Save here.
        System.out.println(cards);
    }

    public static int findCard(String uuid) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getScryfallUUID().equals(uuid))
                return i;
        }

        return -1;
    }

}