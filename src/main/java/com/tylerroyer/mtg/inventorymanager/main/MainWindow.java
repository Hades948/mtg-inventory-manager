package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import com.tylerroyer.mtg.Card;

import java.awt.event.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class MainWindow extends JFrame implements ActionListener, KeyListener {
    private final String TITLE = "MTG Inventory Manager";
    private final Dimension CARDS_PANEL_SIZE = new Dimension(1030, 801);

    private JMenuItem addCardMenuItem;
    private JMenuItem countUniqueCardsMenuItem;
    private JMenuItem countTotalCardsMenuItem;
    private JMenuItem getTotalValueMenuItem;
    private JMenuItem getAverageValueMenuItem;
    private JMenuItem sortByColorMenuItem;
    private JMenuItem sortByQuantityMenuItem;
    private JMenuItem sortByFoilQuantityMenuItem;
    private JMenuItem sortByTotalQuantityMenuItem;
    private JPanel cardsPanel;
    private JScrollPane scrollPane;

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.addKeyListener(this);

        JMenuBar menuBar = new JMenuBar();
        JMenu actionsMenu = new JMenu("Actions");
        JMenu sortMenu = new JMenu("Sort");
        JMenu calculateMenu = new JMenu("Calculate");
        
        addCardMenuItem = new JMenuItem("Add a new card to inventory...");
        addCardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        addCardMenuItem.setMnemonic(KeyEvent.VK_N);
        addCardMenuItem.addActionListener(this);
        actionsMenu.add(addCardMenuItem);

        sortByColorMenuItem = new JMenuItem("By color");
        sortByColorMenuItem.addActionListener(this);
        sortMenu.add(sortByColorMenuItem);

        sortByQuantityMenuItem = new JMenuItem("By Quantity");
        sortByQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByQuantityMenuItem);

        sortByFoilQuantityMenuItem = new JMenuItem("By Foil Quantity");
        sortByFoilQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByFoilQuantityMenuItem);

        sortByTotalQuantityMenuItem = new JMenuItem("By Total Quantity");
        sortByTotalQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByTotalQuantityMenuItem);

        countUniqueCardsMenuItem = new JMenuItem("Count unique cards in inventory");
        countUniqueCardsMenuItem.addActionListener(this);
        calculateMenu.add(countUniqueCardsMenuItem);

        countTotalCardsMenuItem = new JMenuItem("Count total cards in inventory");
        countTotalCardsMenuItem.addActionListener(this);
        calculateMenu.add(countTotalCardsMenuItem);
        
        getTotalValueMenuItem = new JMenuItem("Get total value of all cards");
        getTotalValueMenuItem.addActionListener(this);
        calculateMenu.add(getTotalValueMenuItem);

        getAverageValueMenuItem = new JMenuItem("Get average card value");
        getAverageValueMenuItem.addActionListener(this);
        calculateMenu.add(getAverageValueMenuItem);

        menuBar.add(actionsMenu);
        menuBar.add(sortMenu);
        menuBar.add(calculateMenu);
        this.setJMenuBar(menuBar);

        this.setPreferredSize(CARDS_PANEL_SIZE);
        cardsPanel = new JPanel();
        scrollPane = new JScrollPane(cardsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        displayInventory();
        this.setVisible(true);
        scrollToTop();
    }

    public void displayInventory() {
        cardsPanel.removeAll();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        for (Card card : Inventory.getCards()) {
            JLabel label = new JLabel(card.toString(), SwingConstants.LEFT);
            label.setOpaque(true);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            Color cardColor = card.getColor();
            if (cardColor == null) {
                label.setBackground(Color.GRAY);
                label.setForeground(Color.BLACK);
            } else {
                label.setBackground(cardColor);
                if (cardColor == Color.BLACK || cardColor == Color.RED || cardColor == Color.BLUE) {
                    label.setForeground(Color.WHITE);
                    label.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                } else {
                    label.setForeground(Color.BLACK);
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            }
            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardsPanel.add(label);
        }

        this.add(scrollPane);
        this.pack();
        this.setLocationRelativeTo(null);
        scrollToBottom();
    }

    private void scrollToBottom() {
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        vScrollBar.setValue(vScrollBar.getMaximum());
    }

    private void scrollToTop() {
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        vScrollBar.setValue(vScrollBar.getMinimum());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardMenuItem) {
            String message = "Enter card set, collector number.  Example: \"AAA 123\"";
            String title = "Enter Card Info";
            String response = (String) JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
            
            if (response != null) {
                new ConfirmCardWindow(this, response);
            }
        } else if (e.getSource() == sortByColorMenuItem) {
            Inventory.sort(Inventory.SortType.BY_COLOR);
            displayInventory();
            scrollToTop();
        } else if (e.getSource() == sortByQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_QUANTITY);
            displayInventory();
            scrollToTop();
        } else if (e.getSource() == sortByFoilQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_FOIL_QUANTITY);
            displayInventory();
            scrollToTop();
        } else if (e.getSource() == sortByTotalQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_TOTAL_QUANTITY);
            displayInventory();
            scrollToTop();
        } else if (e.getSource() == countUniqueCardsMenuItem) {
            String message = "You have " + Inventory.getNumberOfUniqueCards() + " unique cards in your inventory.";
            String title = "Unique Card Count";
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == countTotalCardsMenuItem) {
            String message = "You have " + Inventory.getTotalNumberOfCards() + " total cards in your inventory.";
            String title = "Total Card Count";
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == getTotalValueMenuItem) {
            String message = String.format("Your cards are worth a total of $%.2f.", Inventory.getTotalValueOfCards());
            String title = "Total Card Value";
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == getAverageValueMenuItem) {
            String message = String.format("On average, each of your cards are worth $%.2f.", Inventory.getAverageValueOfCards());
            String title = "Average Card Value";
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_HOME) {
            scrollToTop();
        } else if (e.getKeyCode() == KeyEvent.VK_END) {
            scrollToBottom();
        }
    }
}
