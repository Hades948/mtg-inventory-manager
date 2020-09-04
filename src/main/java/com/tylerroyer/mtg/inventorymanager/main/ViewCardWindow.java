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
        try {
            BufferedImage cardImage = ImageIO.read(new URL(card.getImageUrl()));
            this.add(new JLabel(new ImageIcon(cardImage)));
        } catch (IOException e) {e.printStackTrace();}

        this.setTitle("View Card: " + card.getName());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}