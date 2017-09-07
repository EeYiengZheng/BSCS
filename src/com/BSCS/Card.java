package com.BSCS;

/**
 * A card object
 * has the type of card
 * has the data of card
 */
public class Card {
    private String type;
    private String data;

    /**
     * create a card
     *
     * @param type the card type. E.g. magic, black, diamond
     * @param data the card data. E.g. monster, magician, ace
     */
    public Card(String type, String data) {
        this.type = type;
        this.data = data;
    }

    /**
     * return the type of card
     *
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * return the data of card
     *
     * @return the data
     */
    public String getData() {
        return this.data;
    }

    /**
     * print the type and data
     */
    @Override
    public String toString() {
        return type + ": " + data;
    }
}