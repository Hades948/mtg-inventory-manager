// Copyright (C) 2020 Tyler Royer

package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class RefreshCardsWindow extends JFrame implements WindowListener {
    private final String TITLE = "Refreshing Cards...";

    private int maxProgress;

    private MainWindow parentWindow;
    private JLabel progressLabel;
    private JProgressBar progressBar;

    public RefreshCardsWindow(MainWindow parentWindow) {
        this.parentWindow = parentWindow;

        maxProgress = Inventory.getNumberOfUniqueCards();

        progressLabel = new JLabel("Progress: 0/" + maxProgress);
        progressLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

        progressBar = new JProgressBar(0, maxProgress);
        progressBar.setPreferredSize(new Dimension(300, 40));

        new RefreshCardsThread(this).start();

        this.setLayout(new BorderLayout());
        this.add(progressLabel, BorderLayout.NORTH);
        this.add(progressBar, BorderLayout.CENTER);

        this.addWindowListener(this);
        this.setTitle(TITLE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void setProgress(int progress) {
        progressLabel.setText("Progress: " + progress + "/" + maxProgress);
        progressBar.setValue(progress);
    }

    public void onCardsRefreshed() {
        parentWindow.refreshInventoryDisplay();
        parentWindow.scrollToTop();
        parentWindow.onLoadingFinished();
        parentWindow.setStatus("Card refresh finshed.", Colors.WHITE);

        this.dispose();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        parentWindow.setStatus("Card refresh canceled.", Colors.WHITE);
        parentWindow.onLoadingFinished();
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}