package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import com.tylerroyer.mtg.Card;

import java.awt.event.*;
import java.awt.Component;

public class MainWindow extends JFrame implements ActionListener {
    private final String TITLE = "MTG Inventory Manager";

    private JMenuItem addCardMenuItem;
    private JPanel cardsPanel;

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        addCardMenuItem = new JMenuItem("Add new card to inventory...");
        addCardMenuItem.addActionListener(this);
        fileMenu.add(addCardMenuItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        cardsPanel = new JPanel();
        displayInventory();
        this.setVisible(true);
    }

    public void displayInventory() {
        cardsPanel.removeAll();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        for (Card card : Inventory.getCards()) {
            JLabel label = new JLabel(card.getName(), SwingConstants.LEFT);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardsPanel.add(label);
        }
        this.add(cardsPanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardMenuItem) {
            String message = "Enter card set, collector number.  Example: \"AAA 123\"";
            String title = "Enter Card Info";
            String response = (String) JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
            
            new ConfirmCardWindow(this, response);
        }
    }
}
