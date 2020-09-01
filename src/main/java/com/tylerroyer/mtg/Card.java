package com.tylerroyer.mtg;

import org.json.JSONArray;

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

    @Override
    public String toString() {
        return setName + " (" + set + ") #" + collectorNumber + ": " + name;
    }
}