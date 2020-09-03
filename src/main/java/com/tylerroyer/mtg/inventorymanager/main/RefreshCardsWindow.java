package com.tylerroyer.mtg.inventorymanager.main;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

public class RefreshCardsWindow extends JFrame {
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

        this.setTitle(TITLE);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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

        this.dispose();
    }
}