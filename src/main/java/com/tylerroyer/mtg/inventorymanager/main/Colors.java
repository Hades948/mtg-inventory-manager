package com.tylerroyer.mtg.inventorymanager.main;

import java.awt.Color;

public class Colors {
    public static final Color WHITE       = new Color(255, 255, 255);
    public static final Color BLUE        = new Color(128, 128, 255);
    public static final Color BLACK       = new Color(0, 0, 0);
    public static final Color RED         = new Color(255, 100, 100);
    public static final Color GREEN       = new Color(75, 180, 75);
    public static final Color GREY        = new Color(128, 128, 128);
    public static final Color MULTI        = new Color(200, 200, 200);
    public static final Color NONE        = new Color(200, 200, 200);

    public static final Color ERROR_RED   = new Color(255, 50, 50);
    public static final Color WARNING_YELLOW   = new Color(255, 255, 0);

    public static Color stringToColor(String name) {
        if (name.toUpperCase().equals("WHITE")) {
            return WHITE;
        } else if (name.toUpperCase().equals("BLUE")) {
            return BLUE;
        } else if (name.toUpperCase().equals("BLACK")) {
            return BLACK;
        } else if (name.toUpperCase().equals("RED")) {
            return RED;
        } else if (name.toUpperCase().equals("GREEN")) {
            return GREEN;
        } else if (name.toUpperCase().equals("GREY")) {
            return GREY;
        } else if (name.toUpperCase().equals("MULTI")) {
            return MULTI;
        } else {
            return NONE;
        }
    }
}