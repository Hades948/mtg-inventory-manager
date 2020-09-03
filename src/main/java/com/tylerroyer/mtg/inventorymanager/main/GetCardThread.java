package com.tylerroyer.mtg.inventorymanager.main;

import com.tylerroyer.mtg.inventorymanager.net.Scryfall;

public class GetCardThread extends Thread {
    ConfirmCardWindow returnWindow;
    String rawCardInfo;

    public GetCardThread(ConfirmCardWindow returnWindow, String rawCardInfo) {
        this.returnWindow = returnWindow;
        this.rawCardInfo = rawCardInfo;
    }
    
    @Override
    public void run() {
        Scryfall.getCard(returnWindow, rawCardInfo);
    }
}