// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.tylerroyer.mtg.inventorymanager.main.Colors;

public class Card {
    private String name;
    private String type;
    private String set;
    private String setName;
    private String collectorNumber;
    private String imageUrl;
    private String scryfallUUID;
    private int quantity;
    private int foilQuantity;
    private float price;
    private float foilPrice;
    private JSONArray colors;

    public Card() {
        name = "NONE";
        type = "NONE";
        set = "NONE";
        setName = "NONE";
        collectorNumber = "NONE";
        imageUrl = "NONE";
        scryfallUUID = "NONE";
        quantity = 0;
        foilQuantity = 0;
        price = 0.0f;
        foilPrice = 0.0f;

    }

    public Card(JSONObject json) {
        name = json.getString("name");
        type = json.getString("type");
        set = json.getString("set");
        setName = json.getString("set_name");
        collectorNumber = json.getString("collector_number");
        imageUrl = json.getString("image_url");
        scryfallUUID = json.getString("scryfall_uuid");
        quantity = json.getInt("quantity");
        foilQuantity = json.getInt("foil_quantity");
        price = json.getFloat("price");
        foilPrice = json.getFloat("foil_price");
        colors = json.getJSONArray("colors");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getCollectorNumber() {
        return collectorNumber;
    }

    public void setCollectorNumber(String collectorNumber) {
        this.collectorNumber = collectorNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getScryfallUUID() {
        return scryfallUUID;
    }

    public void setScryfallUUID(String scryfallUUID) {
        this.scryfallUUID = scryfallUUID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getFoilQuantity() {
        return foilQuantity;
    }

    public void setFoilQuantity(int foilQuantity) {
        this.foilQuantity = foilQuantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getFoilPrice() {
        return foilPrice;
    }

    public void setFoilPrice(float foilPrice) {
        this.foilPrice = foilPrice;
    }

    public JSONArray getColors() {
        return colors;
    }

    public Color getColor() {
        if (colors == null)
            return Colors.NONE;

        if (colors.length() == 1) {
            String color = colors.getString(0);
            if (color.equals("White")) {
                return Colors.WHITE;
            } else if (color.equals("Blue")) {
                return Colors.BLUE;
            } else if (color.equals("Black")) {
                return Colors.BLACK;
            } else if (color.equals("Red")) {
                return Colors.RED;
            } else if (color.equals("Green")) {
                return Colors.GREEN;
            }
        } else if (colors.length() == 2) {
            return Colors.MULTI;
        }

        return Colors.NONE;

    }

    public void setColors(JSONArray colors) {
        this.colors = colors;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put("name", name);
        json.put("type", type);
        json.put("set", set);
        json.put("set_name", setName);
        json.put("collector_number", collectorNumber);
        json.put("image_url", imageUrl);
        json.put("scryfall_uuid", scryfallUUID);
        json.put("quantity", quantity);
        json.put("foil_quantity", foilQuantity);
        json.put("price", price);
        json.put("foil_price", foilPrice);
        json.put("colors", colors);

        return json;
    }

    public float getTotalValue() {
        return quantity * price + foilQuantity * foilPrice;
    }

    public ImageIcon getImage() {
        if (!imageUrl.equals("null")) {
            try {
                BufferedImage cardImage = ImageIO.read(new URL(imageUrl));
                return new ImageIcon(cardImage);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private static final int NAME_WIDTH = 32;
    private static final int TYPE_WIDTH = 45;
    private static final int COLORS_WIDTH = 13;
    private static final int COLLECTOR_WIDTH = 15;
    private static final int QUANTITY_WIDTH = 6;
    private static final int FOIL_QUANTITY_WIDTH = 10;
    private static final int VALUE_WIDTH = 8;
    private static final int FOIL_VALUE_WIDTH = 10;
    private static final int TOTAL_VALUE_WIDTH = 11;

    @Override
    public String toString() {
        String s = " ";

        // Name
        String nameS = name;        
        while (nameS.length() < NAME_WIDTH) {
            nameS += " ";
        }
        s += nameS;

        // Type
        String typeS = type;
        while (typeS.length() < TYPE_WIDTH) {
            typeS += " ";
        }
        s += typeS;

        // Colors
        String colorsS = "";
        if (colors != null) {
            for (int i = 0; i < colors.length(); i++) {
                if (i != 0) {
                    colorsS += "/";
                }
                colorsS += colors.get(i);
            }
        }
        while (colorsS.length() < COLORS_WIDTH) {
            colorsS += " ";
        }
        s += colorsS;

        // Set and Collector Number
        String collectorS = set.toUpperCase() + " " + collectorNumber;
        while (collectorS.length() < COLLECTOR_WIDTH) {
            collectorS += " ";
        }
        s += collectorS;

        // Quantity
        String sQuantity = String.valueOf(quantity);
        while (sQuantity.length() < QUANTITY_WIDTH) {
            sQuantity += " ";
        }
        s += sQuantity;

        // Foil Quantity
        String sFoilQuantity = String.valueOf(foilQuantity);
        while (sFoilQuantity.length() < FOIL_QUANTITY_WIDTH) {
            sFoilQuantity += " ";
        }
        s += sFoilQuantity;

        // Value
        float value = price;
        String valueS = String.format("%.2f", value);
        while (valueS.length() < VALUE_WIDTH-2) {
            valueS = " " + valueS;
        }
        valueS = "$ " + valueS;
        s += valueS;

        s += "   ";

        // Foil value
        float foilValue = foilPrice;
        String foilValueS = String.format("%.2f", foilValue);
        while (foilValueS.length() < FOIL_VALUE_WIDTH-2) {
            foilValueS = " " + foilValueS;
        }
        foilValueS = "$ " + foilValueS;
        s += foilValueS;
        
        s += "   ";

        // Total value
        float totalValue = getTotalValue();
        String totalValueS = String.format("%.2f", totalValue);
        while (totalValueS.length() < TOTAL_VALUE_WIDTH-2) {
            totalValueS = " " + totalValueS;
        }
        totalValueS = "$ " + totalValueS;
        s += totalValueS;

        s += " ";

        return s;
    }

    public static String getHeader() {
        String s = " ";

        // Name
        String nameS = "Name";        
        while (nameS.length() < NAME_WIDTH) {
            nameS += " ";
        }
        s += nameS;

        // Type
        String typeS = "Type";
        while (typeS.length() < TYPE_WIDTH) {
            typeS += " ";
        }
        s += typeS;

        // Colors
        String colorsS = "Color";
        while (colorsS.length() < COLORS_WIDTH) {
            colorsS += " ";
        }
        s += colorsS;

        // Set and Collector Number
        String collectorS = "Collector #";
        while (collectorS.length() < COLLECTOR_WIDTH) {
            collectorS += " ";
        }
        s += collectorS;

        // Quantity
        String sQuantity = "Qty.";
        while (sQuantity.length() < QUANTITY_WIDTH) {
            sQuantity += " ";
        }
        s += sQuantity;

        // Foil Quantity
        String sFoilQuantity = "F. Qty.";
        while (sFoilQuantity.length() < FOIL_QUANTITY_WIDTH) {
            sFoilQuantity += " ";
        }
        s += sFoilQuantity;

        // Value
        String valueS = "Value";
        while (valueS.length() < VALUE_WIDTH) {
            valueS += " ";
        }
        s += valueS;
        
        s += "   ";

        // Foil value
        String foilValueS = "Foil Value";
        while (foilValueS.length() < FOIL_VALUE_WIDTH) {
            foilValueS += " ";
        }
        s += foilValueS;
        
        s += "   ";

        // Total value
        String totalValueS = "Total value";
        while (totalValueS.length() < TOTAL_VALUE_WIDTH) {
            totalValueS += " ";
        }
        s += totalValueS;

        s += " ";

        return s;
    }
}