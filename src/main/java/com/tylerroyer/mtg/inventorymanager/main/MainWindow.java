// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import com.tylerroyer.mtg.Card;

import java.awt.event.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;

public class MainWindow extends JFrame implements ActionListener, KeyListener {
    private final String TITLE = "MTG Inventory Manager";
    private final Dimension CARDS_PANEL_SIZE = new Dimension(1510, 801);
    private final HashMap<JButton, Card> viewButtons = new HashMap<>();
    private final HashMap<JButton, Card> editButtons = new HashMap<>();

    private boolean isLoadingInProgress = false;

    private JMenuItem addCardMenuItem;
    private JMenuItem refreshDataMenuItem;
    private JMenuItem countUniqueCardsMenuItem;
    private JMenuItem countTotalCardsMenuItem;
    private JMenuItem getTotalValueMenuItem;
    private JMenuItem getAverageValueMenuItem;
    private JMenuItem sortByNameMenuItem;
    private JMenuItem sortByTypeMenuItem;
    private JMenuItem sortByCollectorNumberMenuItem;
    private JMenuItem sortByColorMenuItem;
    private JMenuItem sortByQuantityMenuItem;
    private JMenuItem sortByFoilQuantityMenuItem;
    private JMenuItem sortByTotalQuantityMenuItem;
    private JMenuItem sortByValueMenuItem;
    private JMenuItem sortByFoilValueMenuItem;
    private JMenuItem sortByTotalValueMenuItem;
    private JMenuItem manualMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem contactMenuItem;
    private JLabel statusLabel;
    private JPanel cardsPanel;
    private JScrollPane scrollPane;

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.addKeyListener(this);
        this.setLayout(new BorderLayout());

        statusLabel = new JLabel();
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Colors.BLACK);
        this.add(statusLabel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu actionsMenu = new JMenu("Actions");
        JMenu sortMenu = new JMenu("Sort");
        JMenu calculateMenu = new JMenu("Calculate");
        JMenu helpMenu = new JMenu("Help");
        
        addCardMenuItem = new JMenuItem("Add a new card to inventory ");
        addCardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        addCardMenuItem.setMnemonic(KeyEvent.VK_N);
        addCardMenuItem.addActionListener(this);
        actionsMenu.add(addCardMenuItem);

        refreshDataMenuItem = new JMenuItem("Refresh all card data");
        refreshDataMenuItem.addActionListener(this);
        actionsMenu.add(refreshDataMenuItem);

        sortByNameMenuItem = new JMenuItem("By Name");
        sortByNameMenuItem.addActionListener(this);
        sortMenu.add(sortByNameMenuItem);

        sortByTypeMenuItem = new JMenuItem("By Type");
        sortByTypeMenuItem.addActionListener(this);
        sortMenu.add(sortByTypeMenuItem);

        sortByCollectorNumberMenuItem = new JMenuItem("By Collector #");
        sortByCollectorNumberMenuItem.addActionListener(this);
        sortMenu.add(sortByCollectorNumberMenuItem);

        sortMenu.add(new JSeparator());

        sortByColorMenuItem = new JMenuItem("By Color");
        sortByColorMenuItem.addActionListener(this);
        sortMenu.add(sortByColorMenuItem);

        sortMenu.add(new JSeparator());

        sortByQuantityMenuItem = new JMenuItem("By Quantity");
        sortByQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByQuantityMenuItem);

        sortByFoilQuantityMenuItem = new JMenuItem("By Foil Quantity");
        sortByFoilQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByFoilQuantityMenuItem);

        sortByTotalQuantityMenuItem = new JMenuItem("By Total Quantity");
        sortByTotalQuantityMenuItem.addActionListener(this);
        sortMenu.add(sortByTotalQuantityMenuItem);

        sortMenu.add(new JSeparator());

        sortByValueMenuItem = new JMenuItem("By Value");
        sortByValueMenuItem.addActionListener(this);
        sortMenu.add(sortByValueMenuItem);

        sortByFoilValueMenuItem = new JMenuItem("By Foil Value");
        sortByFoilValueMenuItem.addActionListener(this);
        sortMenu.add(sortByFoilValueMenuItem);

        sortByTotalValueMenuItem = new JMenuItem("By Total Value");
        sortByTotalValueMenuItem.addActionListener(this);
        sortMenu.add(sortByTotalValueMenuItem);

        countUniqueCardsMenuItem = new JMenuItem("Number of unique cards");
        countUniqueCardsMenuItem.addActionListener(this);
        calculateMenu.add(countUniqueCardsMenuItem);

        countTotalCardsMenuItem = new JMenuItem("Total number of cards");
        countTotalCardsMenuItem.addActionListener(this);
        calculateMenu.add(countTotalCardsMenuItem);

        calculateMenu.add(new JSeparator());
        
        getAverageValueMenuItem = new JMenuItem("Average value of all cards");
        getAverageValueMenuItem.addActionListener(this);
        calculateMenu.add(getAverageValueMenuItem);

        getTotalValueMenuItem = new JMenuItem("Total value of all cards");
        getTotalValueMenuItem.addActionListener(this);
        calculateMenu.add(getTotalValueMenuItem);

        manualMenuItem = new JMenuItem("Manual");
        manualMenuItem.addActionListener(this);
        helpMenu.add(manualMenuItem);

        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(this);
        helpMenu.add(aboutMenuItem);

        contactMenuItem = new JMenuItem("Contact");
        contactMenuItem.addActionListener(this);
        helpMenu.add(contactMenuItem);

        menuBar.add(actionsMenu);
        menuBar.add(sortMenu);
        menuBar.add(calculateMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);

        JLabel headersLabel = new JLabel(Card.getHeader());
        headersLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        this.add(headersLabel, BorderLayout.NORTH);

        this.setPreferredSize(CARDS_PANEL_SIZE);
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(cardsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        Inventory.sort(Inventory.SortType.BY_COLLECTOR_NUMBER);
        refreshInventoryDisplay();
        this.setVisible(true);
        scrollToTop();

        setStatus("Welcome to the MTG Inventory Manager.  Copyright © 2020 Tyler Royer", Colors.WHITE);
    }

    public void setStatus(String status, Color color) {
        statusLabel.setForeground(color);
        statusLabel.setText(" " + status);
    }

    public void refreshInventoryDisplay() {
        cardsPanel.removeAll();
        viewButtons.clear();
        editButtons.clear();

        for (Card card : Inventory.getCards()) {
            JPanel cardPanel = new JPanel(new BorderLayout());
            cardPanel.setOpaque(true);

            JLabel label = new JLabel(card.toString(), SwingConstants.LEFT);
            label.setBorder(BorderFactory.createLineBorder(Colors.BLACK));

            JButton viewButton = new JButton("View");
            viewButton.addActionListener(this);
            viewButtons.put(viewButton, card);

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(this);
            editButtons.put(editButton, card);

            Color cardColor = card.getColor();
            if (cardColor == Colors.MULTI) {
                Color bg = Colors.stringToColor(card.getColors().getString(0));
                Color fg = Colors.stringToColor(card.getColors().getString(1));

                if (bg == Colors.RED && fg == Colors.BLUE) {
                    fg = fg.brighter();
                }
                if (bg == Colors.GREEN && fg == Colors.RED || bg == Colors.GREEN && fg == Colors.BLUE) {
                    fg = fg.darker();
                }

                cardPanel.setBackground(bg);
                label.setForeground(fg);
            } else {
                if (cardColor == Colors.BLACK) {
                    label.setForeground(Colors.WHITE);
                } else {
                    label.setForeground(Colors.BLACK);
                }
                cardPanel.setBackground(cardColor);
            }

            cardPanel.setBorder(BorderFactory.createLineBorder(Colors.GREY));

            JPanel buttonsPanel = new JPanel(new BorderLayout(1, 0));
            buttonsPanel.add(viewButton, BorderLayout.WEST);
            buttonsPanel.add(editButton, BorderLayout.EAST);

            label.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            cardPanel.add(label, BorderLayout.CENTER);
            cardPanel.add(buttonsPanel, BorderLayout.EAST);
            cardsPanel.add(cardPanel);
        }

        this.add(scrollPane, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void scrollToBottom() {
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        vScrollBar.setValue(vScrollBar.getMaximum());
    }

    public void scrollToTop() {
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        vScrollBar.setValue(vScrollBar.getMinimum());
    }

    public void onLoadingFinished() {
        isLoadingInProgress = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isLoadingInProgress) {
            setStatus("Warning: Cannot perform any actions while a refresh is in progress.", Colors.WARNING_YELLOW);
            return;
        }

        // View buttons
        for (Entry<JButton, Card> entry : viewButtons.entrySet()) {
            if (e.getSource() == entry.getKey()) {
                new ViewCardWindow(entry.getValue());
                setStatus("View card: " + entry.getValue().getName() + ".", Colors.WHITE);

                return;
            }
        }

        // Edit buttons
        for (Entry<JButton, Card> entry : editButtons.entrySet()) {
            if (e.getSource() == entry.getKey()) {
                new EditCardWindow(this, entry.getValue());
                setStatus("Edit card: " + entry.getValue().getName() + ".", Colors.WHITE);

                return;
            }
        }

        if (e.getSource() == addCardMenuItem) {
            setStatus("Opened Add Card prompt.", Colors.WHITE);

            String message = "Enter card set and collector number.  Example: \"AAA 123\"";
            String title = "Enter Card Info";
            String response = (String) JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
            setStatus("User entered card data: " + response, Colors.WHITE);
            
            if (response != null) {
                new ConfirmCardWindow(this, response);
            }
        } else if (e.getSource() == refreshDataMenuItem) {
            setStatus("Refreshing card data...", Color.WHITE);
            isLoadingInProgress = true;
            new RefreshCardsWindow(this);
        } else if (e.getSource() == sortByNameMenuItem) {
            Inventory.sort(Inventory.SortType.BY_NAME);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by name.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByTypeMenuItem) {
            Inventory.sort(Inventory.SortType.BY_TYPE);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by type.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByCollectorNumberMenuItem) {
            Inventory.sort(Inventory.SortType.BY_COLLECTOR_NUMBER);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by set and collector number.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByColorMenuItem) {
            Inventory.sort(Inventory.SortType.BY_COLOR);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by color.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_QUANTITY);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by quantity.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByFoilQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_FOIL_QUANTITY);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by foil quantity.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByTotalQuantityMenuItem) {
            Inventory.sort(Inventory.SortType.BY_TOTAL_QUANTITY);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by total quantity.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByValueMenuItem) {
            Inventory.sort(Inventory.SortType.BY_VALUE);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by card value.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByFoilValueMenuItem) {
            Inventory.sort(Inventory.SortType.BY_FOIL_VALUE);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by foil card value.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == sortByTotalValueMenuItem) {
            Inventory.sort(Inventory.SortType.BY_TOTAL_VALUE);
            refreshInventoryDisplay();
            setStatus("Sorted inventory by total value of entry.", Color.WHITE);
            scrollToTop();
        } else if (e.getSource() == countUniqueCardsMenuItem) {
            String message = "You have " + Inventory.getNumberOfUniqueCards() + " unique cards in your inventory.";
            String title = "Unique Card Count";
            setStatus("Counted number of unique cards in inventory.", Color.WHITE);
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == countTotalCardsMenuItem) {
            String message = "You have " + Inventory.getTotalNumberOfCards() + " total cards in your inventory.";
            String title = "Total Card Count";
            setStatus("Counted total number of cards in inventory (regular card quantity + foil card quantity).", Color.WHITE);
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == getTotalValueMenuItem) {
            String message = String.format("Your cards are worth a total of $%.2f.", Inventory.getTotalValueOfCards());
            String title = "Total Card Value";
            setStatus("Calculated total value of all cards in inventory.", Color.WHITE);
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == getAverageValueMenuItem) {
            String message = String.format("On average, each of your cards are worth $%.2f.", Inventory.getAverageValueOfCards());
            String title = "Average Card Value";
            setStatus("Calculated average value of all cards in inventory.", Color.WHITE);
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == manualMenuItem) {
            // TODO
        } else if (e.getSource() == aboutMenuItem) {
            new AboutWindow();
        } else if (e.getSource() == contactMenuItem) {
            // TODO
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        // FIXME I have no idea.
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_HOME) {
            scrollToTop();
        } else if (e.getKeyCode() == KeyEvent.VK_END) {
            scrollToBottom();
        }
    }
}
