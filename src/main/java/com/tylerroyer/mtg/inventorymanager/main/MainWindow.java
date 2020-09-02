package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.*;

import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener {
    private final String TITLE = "MTG Inventory Manager";

    private JMenuItem addCardMenuItem;

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

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCardMenuItem) {
            String message = "Enter card set, collector number.  Example: \"AAA 123\"";
            String title = "Enter Card Info";
            String response = (String) JOptionPane.showInputDialog(this, message, title, JOptionPane.PLAIN_MESSAGE);
            
            new ConfirmCardWindow(response);
        }
    }
}
