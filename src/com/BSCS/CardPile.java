package com.BSCS;

import java.util.Stack;

/**
 * a stack of discarded cards
 */
public class CardPile implements Deck {
    private Stack<Card> pile;
    private Card lastAdded;

    /**
     * construct a empty stack
     * a location for discarding cards
     */
    public CardPile() {
        pile = new Stack<>();
    }

    /**
     * discard this card to the pile
     *
     * @param card the card to discard
     */
    public void add(Card card) {
        pile.push(card);
        lastAdded = card;
    }

    /**
     * take a peek at the top card in the stack
     *
     * @return the top card without removing it
     */
    public Card peekLastAdded() {
        return NumberToCard.getCard(lastAdded.getData(), lastAdded.getType());
    }

    /**
     * return the cards pile
     *
     * @return the pile
     */
    public Stack<Card> getPile() {
        return pile;
    }

    /**
     * get the size of stash
     *
     * @return the size
     */
    @Override
    public int size() {
        return pile.size();
    }

    /**
     * see if the stash contains a card
     *
     * @param c the card to look for
     * @return true if presence, false if not
     */
    @Override
    public boolean contains(Card c) {
        return pile.contains(c);
    }

    /**
     * check to see if the stash is empty
     *
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return pile.isEmpty();
    }

    /**
     * shuffle the stash randomly
     */
    @Override
    public void shuffle() {
    }

    /**
     * deal out a top card
     *
     * @return the card on top of the stack
     */
    @Override
    public Card deal() {
        return pile.pop();
    }

    /**
     * print the stash as text
     *
     * @return the pile in text form
     */
    @Override
    public String toString() {
        return pile.toString();
    }
}