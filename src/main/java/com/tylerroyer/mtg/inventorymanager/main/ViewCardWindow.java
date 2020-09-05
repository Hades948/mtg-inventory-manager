// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg.inventorymanager.main;

import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import com.tylerroyer.mtg.Card;

public class ViewCardWindow extends JFrame {

    public ViewCardWindow(Card card) {
        ImageIcon image = card.getImage();
        if (image == null) {
            this.add(new JLabel("<html>Image not found.<br>"
                                        + "Here are all of the card details instead:<br>"
                                        + "Name: " + card.getName() + "<br>"
                                        + "Type: " + card.getType() + "<br>"
                                        + "Set: " + card.getSetName() + "<br>"
                                        + "Collector #: " + card.getCollectorNumber() + "<br>"
                                        + "Colors: " + card.getColors()
                                        + "</html>"));
        } else {
            try {
                BufferedImage cardImage = ImageIO.read(new URL(card.getImageUrl()));
                this.add(new JLabel(new ImageIcon(cardImage)));
            } catch (IOException e) {e.printStackTrace();}
        }

        this.setTitle("View Card: " + card.getName());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}