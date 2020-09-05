// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ContactWindow extends JFrame implements ActionListener {
    private final String TITLE = "Contact";

    JButton contactButton, donateButton;

    public ContactWindow() {
        this.setLayout(new BorderLayout());

        contactButton = new JButton("Contact Me");
        contactButton.addActionListener(this);
        contactButton.setPreferredSize(new Dimension(200, 20));
        this.add(contactButton, BorderLayout.WEST);

        donateButton = new JButton("Donate :D");
        donateButton.addActionListener(this);
        donateButton.setPreferredSize(new Dimension(200, 20));
        this.add(donateButton, BorderLayout.EAST);

        this.setTitle(TITLE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == contactButton) {
            try {
                URI uri = new URI("https://hades948.wixsite.com/tylerroyerportfolio/contact");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == donateButton) {
            URI uri;
            try {
                uri = new URI("https://www.paypal.me/TylerRoyer");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException e1) {e1.printStackTrace();}
            
        }
    }
}