package com.BSCS;

/**
 * a playing card identifiable with a Suits and a Ranks
 */
public class PlayingCard extends Card {
    private Suits suit;
    private Ranks rank;

    /**
     * create a card with the Suits and Ranks given
     * a card is immutable after construction
     *
     * @param suit the Suits to use
     * @param rank the Ranks to use
     */
    public PlayingCard(Suits suit, Ranks rank) {
        super(suit.toString(), rank.toString());
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * get the Suits of this card
     *
     * @return Suits
     */
    public Suits getSuit() {
        return suit;
    }

    /**
     * get the Ranks of this card
     *
     * @return Ranks
     */
    public Ranks getRank() {
        return rank;
    }

    /**
     * show the card in text form
     *
     * @return the card as 'Ranks of Suits'
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
