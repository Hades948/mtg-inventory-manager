package com.tylerroyer.mtg.inventorymanager.main;

import com.tylerroyer.mtg.Card;
import com.tylerroyer.mtg.inventorymanager.net.Scryfall;

public class RefreshCardsThread extends Thread {
    private RefreshCardsWindow parentWindow;

    public RefreshCardsThread(RefreshCardsWindow parentWindow) {
        this.parentWindow = parentWindow;
    }

    @Override
    public void run() {
        int count = 0;
        for (Card card : Inventory.getCards()) {
            Scryfall.updateCard(card);
            parentWindow.setProgress(++count);
        }

        for (Card card : Inventory.getCards()) {
            Inventory.saveCard(card);
        }

        parentWindow.onCardsRefreshed();
    }
}