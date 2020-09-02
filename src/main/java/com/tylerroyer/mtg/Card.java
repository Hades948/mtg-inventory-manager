package com.tylerroyer.mtg;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public void incrementQuantity() {
        quantity++;
    }

    public int getFoilQuantity() {
        return foilQuantity;
    }

    public void setFoilQuantity(int foilQuantity) {
        this.foilQuantity = foilQuantity;
    }

    public void incrementFoilQuantity() {
        foilQuantity++;
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

    @Override
    public String toString() {
        return setName + " (" + set + ") #" + collectorNumber + ": " + name;
    }
}