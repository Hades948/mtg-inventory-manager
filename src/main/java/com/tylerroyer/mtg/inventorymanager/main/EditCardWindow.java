package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;

import com.tylerroyer.mtg.Card;

public class EditCardWindow extends JFrame implements ActionListener {
    private int newQuantity, newFoilQuantity;

    private JButton decrementQuantityButton, incrementQuantityButton;
    private JButton decrementFoilQuantityButton, incrementFoilQuantityButton;
    private JButton okButton, cancelButton;
    private JLabel quantityLabel, foilQuantityLabel;

    private MainWindow parentWindow;
    private Card card;

    public EditCardWindow(MainWindow parentWindow, Card card) {
        this.parentWindow = parentWindow;
        this.card = card;

        newQuantity = card.getQuantity();
        newFoilQuantity = card.getFoilQuantity();

        JLabel nameLabel = new JLabel(" " +card.getName() + " (" + card.getSet().toUpperCase() + " " + card.getCollectorNumber() + ")");
        
        JPanel quantityPanel = new JPanel(new BorderLayout());
        decrementQuantityButton = new JButton("-");
        decrementQuantityButton.addActionListener(this);
        incrementQuantityButton = new JButton("+");
        incrementQuantityButton.addActionListener(this);
        quantityLabel = new JLabel("Quantity: " + newQuantity);
        quantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        quantityPanel.add(decrementQuantityButton, BorderLayout.WEST);
        quantityPanel.add(incrementQuantityButton, BorderLayout.EAST);
        quantityPanel.add(quantityLabel, BorderLayout.CENTER);
        
        JPanel foilQuantityPanel = new JPanel(new BorderLayout());
        decrementFoilQuantityButton = new JButton("-");
        decrementFoilQuantityButton.addActionListener(this);
        incrementFoilQuantityButton = new JButton("+");
        incrementFoilQuantityButton.addActionListener(this);
        foilQuantityLabel = new JLabel("Foil Quantity: " + newFoilQuantity);
        foilQuantityPanel.add(decrementFoilQuantityButton, BorderLayout.WEST);
        foilQuantityPanel.add(incrementFoilQuantityButton, BorderLayout.EAST);
        foilQuantityPanel.add(foilQuantityLabel, BorderLayout.CENTER);

        JPanel quantitiesPanel = new JPanel(new BorderLayout());
        quantitiesPanel.add(quantityPanel, BorderLayout.NORTH);
        quantitiesPanel.add(foilQuantityPanel, BorderLayout.SOUTH);

        okButton = new JButton("Okay");
        okButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(okButton, BorderLayout.WEST);
        optionsPanel.add(cancelButton, BorderLayout.EAST);

        this.setLayout(new BorderLayout(0, 5));
        this.add(nameLabel, BorderLayout.NORTH);
        this.add(quantitiesPanel, BorderLayout.CENTER);
        this.add(optionsPanel, BorderLayout.SOUTH);

        this.setTitle("Edit Card: " + card.getName());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == decrementQuantityButton) {
            newQuantity--;
            if (newQuantity < 0) {
                newQuantity = 0;
            }
            quantityLabel.setText("Quantity: " + newQuantity);
        } else if (e.getSource() == incrementQuantityButton) {
            newQuantity++;
            quantityLabel.setText("Quantity: " + newQuantity);
        } else if (e.getSource() == decrementFoilQuantityButton) {
            newFoilQuantity--;
            if (newFoilQuantity < 0) {
                newFoilQuantity = 0;
            }
            foilQuantityLabel.setText("Foil Quantity: " + newFoilQuantity);
        } else if (e.getSource() == incrementFoilQuantityButton) {
            newFoilQuantity++;
            foilQuantityLabel.setText("Foil Quantity: " + newFoilQuantity);
        } else if (e.getSource() == okButton) {
            card.setQuantity(newQuantity);
            card.setFoilQuantity(newFoilQuantity);
            Inventory.saveCard(card);
            parentWindow.refreshInventoryDisplay();
            this.dispose();
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }
}