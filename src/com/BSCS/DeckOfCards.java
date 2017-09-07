package com.BSCS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * a deck of playing cards
 * has functions similar to a standard 52 cards deck
 */
public class DeckOfCards implements Deck {
    private List<Card> cards;
    public final int NUM_OF_CARDS_STD_DECK = 52;

    /**
     * make a deck with 52 slots
     */
    public DeckOfCards() {
        cards = new ArrayList<Card>(NUM_OF_CARDS_STD_DECK);
        this.buildDeck();
    }

    /**
     * replace the deck with another deck of cards
     *
     * @param cards the cards to replace
     */
    public DeckOfCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * empty the current deck and build a new of deck of 52 playing cards
     */
    public void buildDeck() {
        cards.clear();
        for (Suits s : Suits.values())
            for (Ranks r : Ranks.values()) cards.add(new PlayingCard(s, r));
    }

    /**
     * give the deck
     *
     * @return the deck
     */
    public List<Card> passTheDeck() {
        return cards;
    }

    /**
     * return the current deck size
     *
     * @return the size
     */
    @Override
    public int size() {
        return cards.size();
    }

    /**
     * look for a Card
     *
     * @param c the card to look for
     * @return true if found, false if not
     */
    @Override
    public boolean contains(Card c) {
        for (Card card : cards) {
            if (c.getType().equals(card.getType()) && c.getData().equals(card.getData()))
                return true;
        }
        return false;
    }

    /**
     * check if the deck is empty
     *
     * @return true if empty, false if not
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * shuffle the deck randomly
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * add a new card to the deck
     * if card is already in the deck, ignore
     *
     * @param c
     */
    @Override
    public void add(Card c) {
        if (!contains(c)) cards.add((Card) c);
    }

    /**
     * if the deck has cards, deal the top card
     *
     * @return top card
     */
    @Override
    public Card deal() {
        return cards.remove(0);
    }

    /**
     * publicly visible tool
     * given a Ranks, return a card of random Suits
     *
     * @param r Ranks
     * @return new card of given Ranks and random Suits
     */
    public static Card pickRandomSuitOfRank(Ranks r) {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("♠");
        arr.add("♥");
        arr.add("♦");
        arr.add("♣");
        Collections.shuffle(arr);
        Card c = NumberToCard.getCard(r.toString(), arr.get(0));
        return c;
    }

    // for testing
    private static void main(String[] args) {
        // make a new empty deck
        Deck d = new DeckOfCards();
        // isEmpty? Expects true
        System.out.println("\n" + d.isEmpty());
        // build a standard 52 cards deck
        ((DeckOfCards) d).buildDeck();
        // isEmpty? Expects false
        System.out.println("\n" + d.isEmpty());
        // create a new playing card
        Card card = new PlayingCard(Suits.HEARTS, Ranks.FIVE);
        // testing contains(c ) Expects true
        System.out.println("\n" + d.contains(card));
        // testing size(); Expects: 52
        System.out.println("\nCards in deck: " + d.size());
        // rebuild deck
        ((DeckOfCards) d).buildDeck();
        // testing size(); Expects: 52
        System.out.println("\nCards in deck: " + d.size());
        // Expects Ace of Spades
        System.out.println("\n" + d.deal());
        // Expects Two of Spades
        System.out.println("\n" + d.deal());
        // test shuffling
        d.shuffle();
        // Expects 50
        System.out.println("\nCards in deck: " + d.size());
        // reassigned card to a random card from shuffled deck
        card = (Card) d.deal();
        // Expects a random card
        System.out.println("\n" + card);
        // testing contains(card) Expects false
        System.out.println("\n" + d.contains(card));
        System.out.println("\nlists all shuffled cards:");
        for (Card c : ((DeckOfCards) d).cards) {
            System.out.println(c.toString());
        }
        // shuffles again
        d.shuffle();
        System.out.println("\nlists all shuffled cards:");
        for (Card c : ((DeckOfCards) d).cards) {
            System.out.println(c.toString());
        }
        // deal left over cards
        for (int i = 0; i < 50; i++) {
            d.deal();
        }
        // should be empty
        System.out.println("\n" + d.isEmpty());
        // print nothing
        System.out.println("\nNothing?");
        for (Card c : ((DeckOfCards) d).cards) {
            System.out.println(c.toString());
        }
    }
}