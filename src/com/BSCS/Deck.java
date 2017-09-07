package com.BSCS;

/**
 * an interface Deck
 * it has a size of 0 to infinite cards
 * it can be shuffled
 * new cards can be added in
 * can remove a card to deal out
 */
public interface Deck {
    int size();

    boolean contains(Card c);

    boolean isEmpty();

    void shuffle();

    void add(Card c);

    Card deal();
}