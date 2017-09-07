package com.BSCS;

/**
 * public tool to help with the game
 */
public class NumberToCard {
    /**
     * get a card of the given type and data
     *
     * @param data the data
     * @param type the type
     * @return a card of the type and data
     */
    public static Card getCard(String data, String type) {
        Suits s = null;
        Ranks r = null;
        if (type.equals("♠")) s = Suits.SPADES;
        if (type.equals("♥")) s = Suits.HEARTS;
        if (type.equals("♦")) s = Suits.DIAMONDS;
        if (type.equals("♣")) s = Suits.CLUBS;

        int count1 = 0;
        if (data.length() > 2) {
            for (Ranks rs : Ranks.values()) {
                count1++;
                if (data.equals(rs.toString())) r = rs;
            }
        } else {
            int num = Integer.parseInt(data);
            int count2 = 0;
            for (Ranks rs : Ranks.values()) {
                count2++;
                if (count2 == num) r = rs;
            }
        }
        Card c = new PlayingCard(s, r);
        return c;
    }
}