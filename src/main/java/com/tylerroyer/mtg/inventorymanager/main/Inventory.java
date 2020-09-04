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
    public static enum SortType {
        BY_COLLECTOR_NUMBER, BY_COLOR, BY_QUANTITY, BY_FOIL_QUANTITY, BY_TOTAL_QUANTITY, BY_TOTAL_VALUE
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
            case BY_COLLECTOR_NUMBER:
                ArrayList<Card> sortedByCollectorNumber = new ArrayList<>();
                for (Card card : cards) {
                    String unsortedNumber = card.getSet() + " " + card.getCollectorNumber();
                    int index = sortedByCollectorNumber.size();
                    for (int i = 0; i < sortedByCollectorNumber.size(); i++) {
                        String sortedNumber = sortedByCollectorNumber.get(i).getSet() + " " + sortedByCollectorNumber.get(i).getCollectorNumber();
                        if (unsortedNumber.compareTo(sortedNumber) < 0) {
                            index = i;
                            break;
                        }
                    }
                    sortedByCollectorNumber.add(index, card);
                }

                cards.clear();
                cards.addAll(sortedByCollectorNumber);
                break;
            case BY_COLOR:
                ArrayList<Card> white = new ArrayList<>();
                ArrayList<Card> blue = new ArrayList<>();
                ArrayList<Card> black = new ArrayList<>();
                ArrayList<Card> red = new ArrayList<>();
                ArrayList<Card> green = new ArrayList<>();
                ArrayList<Card> blueWhite = new ArrayList<>();
                ArrayList<Card> blackWhite = new ArrayList<>();
                ArrayList<Card> blackBlue = new ArrayList<>();
                ArrayList<Card> blackRed = new ArrayList<>();
                ArrayList<Card> blackGreen = new ArrayList<>();
                ArrayList<Card> redWhite = new ArrayList<>();
                ArrayList<Card> redBlue = new ArrayList<>();
                ArrayList<Card> greenWhite = new ArrayList<>();
                ArrayList<Card> greenBlue = new ArrayList<>();
                ArrayList<Card> greenRed = new ArrayList<>();
                ArrayList<Card> none = new ArrayList<>();
                for (Card card : cards) {
                    if (card.getColor() == Colors.WHITE) {
                        white.add(card);
                    } else if (card.getColor() == Colors.BLUE) {
                        blue.add(card);
                    } else if (card.getColor() == Colors.BLACK) {
                        black.add(card);
                    } else if (card.getColor() == Colors.RED) {
                        red.add(card);
                    } else if (card.getColor() == Colors.GREEN) {
                        green.add(card);
                    } else if (card.getColor() == Colors.MULTI) {
                        String color1 = card.getColors().getString(0);
                        String color2 = card.getColors().getString(1);
                        if (color1.equals("Blue") && color2.equals("White")) {
                            blueWhite.add(card);
                        } else if (color1.equals("Black") && color2.equals("White")) {
                            blackWhite.add(card);
                        } else if (color1.equals("Black") && color2.equals("Blue")) {
                            blackBlue.add(card);
                        } else if (color1.equals("Black") && color2.equals("Red")) {
                            blackRed.add(card);
                        } else if (color1.equals("Black") && color2.equals("Green")) {
                            blackGreen.add(card);
                        } else if (color1 .equals("Red") && color2.equals("White")) {
                            redWhite.add(card);
                        } else if (color1.equals("Red") && color2.equals("Blue")) {
                            redBlue.add(card);
                        } else if (color1.equals("Green") && color2.equals("White")) {
                            greenWhite.add(card);
                        } else if (color1.equals("Green") && color2.equals("Blue")) {
                            greenBlue.add(card);
                        } else if (color1.equals("Green") && color2.equals("Red")) {
                            greenRed.add(card);
                        } else {
                            System.err.println("Unrecognized multi-color: " + color1 + "/" + color2);
                            none.add(0, card);
                        }
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
                cards.addAll(blueWhite);
                cards.addAll(blackWhite);
                cards.addAll(blackBlue);
                cards.addAll(blackRed);
                cards.addAll(blackGreen);
                cards.addAll(redWhite);
                cards.addAll(redBlue);
                cards.addAll(greenWhite);
                cards.addAll(greenBlue);
                cards.addAll(greenRed);
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
            case BY_TOTAL_VALUE:
                ArrayList<Card> sortedByTotalValue = new ArrayList<>();
                for (Card card : cards) {
                    int index = sortedByTotalValue.size();
                    for (int i = 0; i < sortedByTotalValue.size(); i++) {
                        if (sortedByTotalValue.get(i).getTotalValue() < card.getTotalValue()) {
                            index = i;
                            break;
                        }
                    }
                    sortedByTotalValue.add(index, card);
                }

                cards.clear();
                cards.addAll(sortedByTotalValue);
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