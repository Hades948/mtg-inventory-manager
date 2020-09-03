package com.tylerroyer.mtg.inventorymanager.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;

import com.tylerroyer.mtg.Card;

import org.json.JSONObject;

public class Inventory {
    public static enum SortType {
        BY_COLOR, BY_QUANTITY, BY_FOIL_QUANTITY, BY_TOTAL_QUANTITY
    }

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

    public static void sort(SortType sortType) {
        switch (sortType) {
            default:
            case BY_COLOR:
                ArrayList<Card> white = new ArrayList<>();
                ArrayList<Card> blue = new ArrayList<>();
                ArrayList<Card> black = new ArrayList<>();
                ArrayList<Card> red = new ArrayList<>();
                ArrayList<Card> green = new ArrayList<>();
                ArrayList<Card> mixed = new ArrayList<>();
                ArrayList<Card> none = new ArrayList<>();
                for (Card card : cards) {
                    if (card.getColor() == null) {
                        if (card.getColors().length() > 1) {
                            mixed.add(card);
                        } else {
                            none.add(card);
                        }
                    } else if (card.getColor() == Color.WHITE) {
                        white.add(card);
                    } else if (card.getColor() == Color.BLUE) {
                        blue.add(card);
                    } else if (card.getColor() == Color.BLACK) {
                        black.add(card);
                    } else if (card.getColor() == Color.RED) {
                        red.add(card);
                    } else if (card.getColor() == Color.GREEN) {
                        green.add(card);
                    } else {
                        none.add(card);
                    }
                }
                cards.clear();
                cards.addAll(white);
                cards.addAll(blue);
                cards.addAll(black);
                cards.addAll(red);
                cards.addAll(green);
                cards.addAll(mixed);
                cards.addAll(none);
                break;
            
            case BY_QUANTITY:
                ArrayList<Card> sortedByQuantity = new ArrayList<>();
                for (Card card : cards) {
                    int index = sortedByQuantity.size();
                    for (int i = 0; i < sortedByQuantity.size(); i++) {
                        if (sortedByQuantity.get(i).getQuantity() < card.getQuantity()) {
                            index = i;
                            break;
                        }
                    }
                    sortedByQuantity.add(index, card);
                }

                cards.clear();
                cards.addAll(sortedByQuantity);
                break;
            case BY_FOIL_QUANTITY:
                ArrayList<Card> sortedByFoilQuantity = new ArrayList<>();
                for (Card card : cards) {
                    int index = sortedByFoilQuantity.size();
                    for (int i = 0; i < sortedByFoilQuantity.size(); i++) {
                        if (sortedByFoilQuantity.get(i).getFoilQuantity() < card.getFoilQuantity()) {
                            index = i;
                            break;
                        }
                    }
                    sortedByFoilQuantity.add(index, card);
                }

                cards.clear();
                cards.addAll(sortedByFoilQuantity);
                break;
            case BY_TOTAL_QUANTITY:
                ArrayList<Card> sortedByTotalQuantity = new ArrayList<>();
                for (Card card : cards) {
                    int index = sortedByTotalQuantity.size();
                    for (int i = 0; i < sortedByTotalQuantity.size(); i++) {
                        if (sortedByTotalQuantity.get(i).getFoilQuantity() + sortedByTotalQuantity.get(i).getQuantity()
                              < card.getFoilQuantity() + card.getQuantity()) {
                            index = i;
                            break;
                        }
                    }
                    sortedByTotalQuantity.add(index, card);
                }

                cards.clear();
                cards.addAll(sortedByTotalQuantity);
                break;
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

    public static void saveCard(Card card) {
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