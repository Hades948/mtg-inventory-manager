package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import com.tylerroyer.mtg.Card;
import com.tylerroyer.mtg.inventorymanager.net.Scryfall;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
    private JCheckBox foilCheckBox;
    private JTextField quantityTextField;
    private JButton yesButton, noButton, okButton;

    private Card card;
    private MainWindow parentWindow;

    public ConfirmCardWindow(MainWindow parentWindow, String rawCardInfo) {
        this.parentWindow = parentWindow;
        topPanel = new JPanel();
        JPanel modifiersPanel = new JPanel();
        modifiersPanel.setLayout(new BorderLayout());
        foilCheckBox = new JCheckBox();
        foilCheckBox.setText("Foil?");
        modifiersPanel.add(foilCheckBox, BorderLayout.WEST);
        statusLabel = new JLabel(" Searching...");
        loadingLabel = new JLabel("");

        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BorderLayout());
        quantityPanel.add(new JLabel("Qty: "), BorderLayout.WEST);
        quantityTextField = new JTextField();
        quantityTextField.setText("1");
        quantityTextField.setPreferredSize(new Dimension(75, 20));
        quantityPanel.add(quantityTextField, BorderLayout.EAST);
        modifiersPanel.add(quantityPanel, BorderLayout.EAST);

        topPanel.setLayout(new BorderLayout());
        topPanel.add(statusLabel, BorderLayout.WEST);
        topPanel.add(modifiersPanel, BorderLayout.CENTER);

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

            this.getRootPane().setDefaultButton(yesButton);
            quantityTextField.requestFocus();
            quantityTextField.selectAll();
        }

        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == yesButton) {
            boolean foil = foilCheckBox.isSelected();
            int quantity = Integer.parseInt(quantityTextField.getText());
            if (foil) {
                card.setFoilQuantity(quantity);
            } else {
                card.setQuantity(quantity);
            }
            Inventory.addCardToInventory(card, foil); // TODO Implement foils
            parentWindow.displayInventory();
            this.dispose();
        } else if (e.getSource() == noButton || e.getSource() == okButton) {
            this.dispose();
        }
    }
}
