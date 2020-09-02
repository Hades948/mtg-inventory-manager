package com.tylerroyer.mtg.inventorymanager.main;

class Driver {
    public static void main(String[] args) {
        Inventory.init();
        Inventory.loadInventory();
        new MainWindow();
    }
}