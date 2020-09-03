package com.tylerroyer.mtg;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Color;

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
        if (colors == null) return null;
        if (colors.length() != 1) return null;

        String color = colors.getString(0);
        if (color.equals("White")) {
            return Color.WHITE;
        } else if (color.equals("Blue")) {
            return Color.BLUE;
        } else if (color.equals("Black")) {
            return Color.BLACK;
        } else if (color.equals("Red")) {
            return Color.RED;
        } else if (color.equals("Green")) {
            return Color.GREEN;
        } else {
            return null;
        }
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

    private final int NAME_WIDTH = 32;
    private final int TYPE_WIDTH = 40;
    private final int COLORS_WIDTH = 13;
    private final int COLLECTOR_WIDTH = 11;
    private final int QUANTITY_WIDTH = 5;
    private final int VALUE_WIDTH = 8;

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
        while (sFoilQuantity.length() < QUANTITY_WIDTH) {
            sFoilQuantity += " ";
        }
        s += sFoilQuantity;

        // Total value
        float value = getTotalValue();
        String valueS = String.format("%.2f", value);
        while (valueS.length() < VALUE_WIDTH-2) {
            valueS = " " + valueS;
        }
        valueS = "$ " + valueS;
        s += valueS;

        s += " ";

        return s;
    }
}