package com.BSCS;

import java.util.HashMap;
import java.util.Map;

/**
 * count and return the current rank to play
 */
public class RankCounter {
    private Map<Integer, Ranks> cardMap = new HashMap<>();
    private int currentRank;
    private int turn;

    /**
     * construct a new Ranks counter
     */
    public RankCounter() {
        this.currentRank = 0;
        this.turn = 1;
        for (Ranks r : Ranks.values()) {
            cardMap.put(turn, r);
            turn++;
        }
        turn = 0;
    }

    /**
     * traverse to the next Ranks
     * resets at 13 (Ranks.KING)
     *
     * @return the Ranks
     */
    public Ranks next() {
        if (currentRank == 13) {
            currentRank = 0;
        }
        currentRank++;
        turn++;
        return cardMap.get(currentRank);
    }
}
