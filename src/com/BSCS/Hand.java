package com.BSCS;

import java.util.ArrayList;
import java.util.Collections;

/**
 * a pile of cards in the player's hands
 */
public class Hand {
    private ArrayList<Card> hand;
    private ArrayList<String> sortedHand;


    /**
     * construct a new hand
     */
    public Hand() {
        hand = new ArrayList<>();
        sortedHand = new ArrayList<>();
    }

    /**
     * construct a hand with a given List of Cards
     *
     * @param hand the cards to use
     */
    public Hand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * pass the cards on hand
     *
     * @return the hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }


    /**
     * size of the hand
     */
    public int size() {
        return hand.size();
    }

    /**
     * discard/clear the hand
     *
     * @return the hand
     */
    public ArrayList<Card> discard() {
        ArrayList<Card> temp = (ArrayList<Card>) hand.clone();
        hand.clear();
        return temp;
    }

    /**
     * add a new card to the hand
     *
     * @param card the card to add
     */
    public void add(Card card) {
        hand.add(card);
    }

    /**
     * find and return the closest card near the hand, of a given Ranks
     *
     * @param r the Ranks to find
     * @return the card
     */
    public Card getNextCardOfRank(Ranks r) {
        for (Card c : hand) {
            Card d = c;
            if (c.getData().equals(r.toString())) return c;
        }
        return null;
    }

    /**
     * remove a card from the hand and return it
     *
     * @param card the card to remove
     * @return the removed card
     */
    public Card remove(Card card) {
        PlayingCard c = (PlayingCard) card;
        Suits s = c.getSuit();
        Ranks r = c.getRank();
        Card newCard = new PlayingCard(s, r);
        hand.remove(card);
        return newCard;
    }

    /**
     * remove a card at an index
     *
     * @param index the index of the card
     * @return the card at index
     */
    public Card removeCardAt(int index) {
        return hand.remove(index);
    }

    /**
     * play a card, but 'BS' it
     */
    public Card bsPlay(int chosenCard) {
        String chosen = sortedHand.get(chosenCard - 1);
        Card referenceCard;
        if (chosen.length() == 3)
            referenceCard = NumberToCard.getCard(chosen.substring(0, 2), chosen.substring(2));
        else referenceCard = NumberToCard.getCard(chosen.substring(0, 1), chosen
                .substring(1));
        return removeCardAt(cardIndexAt((PlayingCard) referenceCard));
    }

    /**
     * assisting tool
     * find a PlayingCard at an index
     *
     * @param pc the PlayingCard to find
     * @return the location
     */
    private int cardIndexAt(PlayingCard pc) {
        Suits s = pc.getSuit();
        Ranks r = pc.getRank();
        int i = 0;
        for (Card c : hand) {
            if (c.getType().equals(s.toString()) && c.getData().equals(r
                    .toString())) return i;
            i++;
        }
        return 0;
    }

    /**
     * look for a card with a given Ranks
     *
     * @return true if has Ranks, false otherwise
     */
    public boolean containsRanks(Ranks r) {
        for (Card c : hand) {
            PlayingCard pc = (PlayingCard) c;
            if (pc.getRank() == r) return true;
        }
        return false;
    }

    /**
     * look for a card with the Suits
     *
     * @return true if has Suites, false otherwise
     */
    public boolean containsSuits(Suits s) {
        for (Card c : hand) {
            PlayingCard pc = (PlayingCard) c;
            if (pc.getSuit() == s) return true;
        }
        return false;
    }

    /**
     * show how many cards you own
     *
     * @param r
     */
    public void showHand(Ranks r) {
        String str = "You have " + size() + " cards. ";
        printHand();
        str.concat("" + numOfCards(r) + "are " + r.toString() + "cards.");
        System.out.println(str);
    }

    /**
     * print the cards in your hands in sorted order
     */
    public void printHand() {
        ArrayList<String> als1 = new ArrayList<>();
        ArrayList<String> als2 = new ArrayList<>();
        sortedHand.clear();
        for (Card c : hand) {
            String str = "";
            PlayingCard pc = (PlayingCard) c;
            int i = 0;
            for (Ranks r : Ranks.values()) {
                i++;
                if (r.toString().equals(c.getData())) str = str + i;
            }
            String type = c.getType();
            if (type.equals("SPADES")) str = str.concat("♠");
            if (type.equals("HEARTS")) str = str.concat("♥");
            if (type.equals("DIAMONDS")) str = str.concat("♦");
            if (type.equals("CLUBS")) str = str.concat("♣");
            if (str.length() == 2) als1.add(str);
            else als2.add(str);
        }
        Collections.sort(als1);
        Collections.sort(als2);
        als1.addAll(als2);
        sortedHand = (ArrayList<String>) als1.clone();

        System.out.println("Your cards:");
        for (int i = 1; i <= size(); i++) {
            System.out.print(sortedHand.get(i - 1));
            if (sortedHand.get(i - 1).length() == 2) System.out.print("  ");
            else System.out.print(" ");
            if (i % 4 == 0) System.out.println();
        }
    }

    /**
     * count the number of cards in the hand with a given Ranks
     *
     * @param r the Ranks to find
     * @return the number of cards with the Ranks
     */
    public int numOfCards(Ranks r) {
        int count = 0;
        for (Card c : hand) {
            PlayingCard pc = (PlayingCard) c;
            if (pc.getRank() == r) count++;
        }
        return count;
    }

    /**
     * show the hand in raw data
     *
     * @return the hand as String
     */
    @Override
    public String toString() {
        return hand.toString();
    }
}