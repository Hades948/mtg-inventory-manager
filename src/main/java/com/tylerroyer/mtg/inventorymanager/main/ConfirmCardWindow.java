package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import com.tylerroyer.mtg.Card;
import com.tylerroyer.mtg.inventorymanager.net.Scryfall;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmCardWindow extends JFrame implements ActionListener {
    private final String TITLE = "Confirm Card";
    
    private JLabel statusLabel;
    private JLabel loadingLabel;
    private JPanel topPanel;
    private JButton yesButton, noButton, okButton;

    private Card card;
    private MainWindow parentWindow;

    public ConfirmCardWindow(MainWindow parentWindow, String rawCardInfo) {
        this.parentWindow = parentWindow;
        topPanel = new JPanel();
        statusLabel = new JLabel(" Searching...");
        loadingLabel = new JLabel("");

        topPanel.setLayout(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(loadingLabel, BorderLayout.CENTER);

        Scryfall.getCard(this, rawCardInfo);

        this.setTitle(TITLE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void onCardResult(Card card) {
        this.card = card;

        if (card == null) {
            statusLabel.setText(" Card not found.");
            okButton = new JButton("Okay");
            okButton.addActionListener(this);
            this.add(okButton, BorderLayout.CENTER);
        } else {
            statusLabel.setText(" Is this the correct card?");
            loadingLabel.setText(" Loading...");

            try {
                BufferedImage cardImage = ImageIO.read(new URL(card.getImageUrl()));
                this.remove(loadingLabel);
                this.add(new JLabel(new ImageIcon(cardImage)), BorderLayout.CENTER);
            } catch (IOException e) {e.printStackTrace();}

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new BorderLayout(4, 0));
            yesButton = new JButton("Yes");
            noButton = new JButton("No");
            yesButton.addActionListener(this);
            noButton.addActionListener(this);
            optionsPanel.add(yesButton, BorderLayout.WEST);
            optionsPanel.add(noButton, BorderLayout.EAST);
            topPanel.add(optionsPanel, BorderLayout.EAST);
        }

        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            Inventory.addCardToInventory(card, false); // TODO Implement foils
            parentWindow.displayInventory();
            this.dispose();
        } else if (e.getSource() == noButton || e.getSource() == okButton) {
            this.dispose();
        }
    }
}
