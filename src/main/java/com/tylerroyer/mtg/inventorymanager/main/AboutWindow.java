// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Dimension;
import java.awt.Font;

public class AboutWindow extends JFrame {
    private final String TITLE = "About";
    private final Dimension SIZE = new Dimension(480, 560);

    public AboutWindow() {
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        String aboutText = "<html>"
                            + "Thank you so much for downloading The MTG Inventory Manager!  My name "
                            + "is Tyler.  I created this software because I wanted a quick and easy "
                            + "way to keep track of my magic cards.  I was using Excel, but it couldn't do "
                            + "everything I wanted and I like having something where I can make it work "
                            + "exactly how I want it to.  I will take requests on how I can make it better "
                            + "as well.  See \"Help->Contact\" section to see how to make requests.<br><br><br><br>"
                           + "</html>";
        JLabel aboutLabel = new JLabel(aboutText);
        aboutLabel.setFont(new Font("Ariel", Font.PLAIN, 24));
        this.add(aboutLabel);

        String licenseText = "<html><p style=\"text-align:center;\">"
                           + "This software is licensed by the GNU GPL 3.0 license<br>"
                           + "Copyright © 2020 Tyler Royer<br>"
                           + "</p></html>";
        JLabel licenseLabel = new JLabel(licenseText, SwingConstants.CENTER);
        licenseLabel.setFont(new Font("Ariel", Font.PLAIN, 18));
        this.add(licenseLabel);

        String versionText = "<html><p style=\"text-align:center;\">Version 0.1.0</p></html>";
        JLabel versionLabel = new JLabel(versionText, SwingConstants.CENTER);
        versionLabel.setFont(new Font("Ariel", Font.PLAIN, 14));
        this.add(versionLabel);

        this.setSize(SIZE);
        this.setTitle(TITLE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}