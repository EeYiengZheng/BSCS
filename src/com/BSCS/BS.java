package com.BSCS;

import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * The game code
 * BS is a cards game
 * Cards are dealt equally among the players.
 * A 52 cards deck with 4 players mean each player will have 13 cards.
 * Player 1 starts the play, then 2, 3, 4, and back to 1
 * The first play start at the Ace, then 2, 3, ..., Queen, King, Ace, 2, etc.
 * During their turn, the player can put down the actual card of the play, or
 * put down a fake, or combination, up to 4 cards.
 * Cards are discarded to a stack.
 * If the rests of the players think the current player is lying, they can
 * call out "BS", or remain silent.
 * If the player lied, the player will insert the discarded stack onto their hands.
 * Else, if the recently played card is a true card of the current play, the one who called BS will receive the discarded stack instead.
 * The game is won when one player completely discard their hands.
 */
public class BS {
    private int playerCount;
    private RankCounter rc;
    private Ranks currentRank;
    private Hand[] hands;
    private DeckOfCards theDeck;
    private CardPile pile;
    private boolean hasWinner;
    private Scanner in;
    private Random random;
    private Stack<Card> prevCardsPlayed;
    private int prevCardsPlayedCount;

    // constants
    private final double COMPUTER_ACCUSING_COMPUTER_CHANCE = 0.2;
    private final double COMPUTER_ACCUSING_HUMAN_CHANCE = 0.2;
    private final double COMPUTER_BS_TWO_CARDS_CHANCE = 0.2;
    private final double COMPUTER_BS_THREE_CARDS_CHANCE = 0.1;
    private final double COMPUTER_BS_FOUR_CARDS_CHANCE = 0.05;


    /**
     * one of the two public methods
     * this constructor creates the game with 2 to 4 players
     *
     * @param players the number of players
     */
    public BS(int players) {
        int p;
        if (players < 2) p = 2;
        else if (players > 4) p = 4;
        else p = players;
        this.playerCount = p;

        System.out.println("Running with " + p + " players:");

        rc = new RankCounter();
        hands = new Hand[p];
        for (int i = 0; i < p; i++) {
            hands[i] = new Hand();
        }
        theDeck = new DeckOfCards();
        theDeck.shuffle();
        pile = new CardPile();
        hasWinner = false;

        random = new Random();

        prevCardsPlayed = new Stack<>();
        prevCardsPlayedCount = 0;

        in = new Scanner(System.in);
        settingUpDealing();
    }

    /**
     * the second of the two public methods
     * it starts the game
     * while there is no winner, loop through all players until someone wins
     */
    public void play() {
        System.out.println("\nStarting a new game!\n");
        int playersCount = hands.length;
        while (!hasWinner) {
            choosePlayer(1);
            if (hasWinner) {
                if (hands[0].size() != 0) hasWinner = false;
                else break;
            }
            choosePlayer(2);
            if (hasWinner) {
                if (hands[1].size() != 0) hasWinner = false;
                else break;
            }
            if (playersCount > 2) {
                choosePlayer(3);
                if (hasWinner) {
                    if (hands[2].size() != 0) hasWinner = false;
                    else break;
                }
            }
            if (playersCount > 3) {
                choosePlayer(4);
                if (hasWinner) {
                    if (hands[3].size() != 0) hasWinner = false;
                    else break;
                }
            }
        }
        System.out.println("\nGame End.");
    }

    /**
     * set the game up by giving the players equal amounts of cards
     * 26 cards per player in a two-players game
     * 17 cards per player in a three-players game
     * 13 cards per player in a four-players game
     */
    private void settingUpDealing() {
        int cardsForPlayers = (52 - (52 % hands.length)) / hands.length;

        for (int i = 0; i < cardsForPlayers; i++) {
            for (Hand h : hands) {
                h.add(theDeck.deal());
            }
        }
        while (theDeck.size() > 0) {
            pile.add(theDeck.deal());
        }
    }

    /**
     * player chooser
     * identifies the human player vs the computer player
     *
     * @param player the player to identify; human or computer
     */
    private void choosePlayer(int player) {
        if (player == 1) {
            if (!hasWinner) hasWinner = humanPlayer();
        } else {
            hasWinner = cpuPlayer(hands[player - 1], player);
        }
        bsCallChance(player);
        clearPrevPlay();
        if (hands[0].size() != 0) hasWinner = false;
        if (hasWinner) {
            if (player == 1) System.out.println("\nYou win!");
            else System.out.println("Player " + (player) + " won!");
        }
    }

    /**
     * a chance for the selected player to call out BS
     *
     * @param playerNumber this player's chance
     */
    private void bsCallChance(int playerNumber) {
        boolean bsCalled = false;
        if (playerNumber != 1) {
            for (int i = 2; i <= playerCount && i != playerNumber; i++) {
                double d = random.nextDouble();
                if (d < COMPUTER_ACCUSING_COMPUTER_CHANCE) {
                    if (!bsCalled) {
                        boolean check = checkIfBs();
                        Stack<Card> s = pile.getPile();
                        int temp = s.size();
                        System.out.println("Player " + i + " called BS on " +
                                "player " + playerNumber + ".");
                        if (!check) {
                            System.out.println("Player " + i + " " +
                                    "wrongly accused player " + playerNumber + "!\n -- " +
                                    "Player " + i + " receives all cards in " +
                                    "played pile.");

                            for (int k = 0; k < temp; k++) {
                                hands[i - 1].add(s.pop());
                            }
                        } else {
                            System.out.println("Player " + playerNumber +
                                    " BS'd" + "\n -- Player " + playerNumber +
                                    " receives all cards in the played pile.");
                            for (int k = 0; k < temp; k++) {
                                hands[playerNumber - 1].add(s.pop());
                            }
                        }
                        bsCalled = true;
                        clearPrevPlay();
                    }
                    break;
                }
            }
            if (!bsCalled) {
                System.out.println("\nCall BS on " + playerNumber + "?");
                System.out.println("'y' for Yes. 'n' for no.");
                System.out.println("Input: ");
                String playerInputBsDecision = in.next().toLowerCase();
                while (!(playerInputBsDecision.equals("y") ||
                        playerInputBsDecision.equals("n"))) {
                    System.out.println("Wrong input. 'y' for Yes. 'n' for no.");
                    playerInputBsDecision = in.next();
                }
                if (playerInputBsDecision.equals("y")) {
                    boolean check = checkIfBs();
                    System.out.println("\nYou called BS on player " +
                            playerNumber + ".");
                    Stack<Card> s = pile.getPile();
                    int temp = s.size();
                    if (!check) {
                        System.out.println("You wrongly accused player " +
                                playerNumber + "!\n -- " +
                                "receives all cards in played pile.");
                        for (int k = 0; k < temp; k++) {
                            hands[0].add(s.pop());
                        }
                    } else {
                        System.out.println("Player " +
                                playerNumber + " cheated!\n -- " +
                                playerNumber + " receives all cards in played" +
                                " pile.");
                        for (int k = 0; k < temp; k++) {
                            hands[playerNumber - 1].add(s.pop());
                        }
                    }

                }
            }
        } else {
            for (int i = 2; i <= playerCount; i++) {
                double d = random.nextDouble();
                if (d < COMPUTER_ACCUSING_HUMAN_CHANCE) {
                    if (!bsCalled) {
                        bsCalled = true;
                        boolean check = checkIfBs();
                        Stack<Card> s = pile.getPile();
                        int temp = s.size();
                        System.out.println("Yes!\nPlayer " + i + " called BS " +
                                "on you!");
                        if (!check) {
                            System.out.println("But you did not cheat. Player" +
                                    " " + i + " takes all played cards!");
                            for (int k = 0; k < temp; k++) {
                                hands[i - 1].add(s.pop());
                            }
                        } else {
                            System.out.println("You cheated. You take all " +
                                    "played cards");
                            for (int k = 0; k < temp; k++) {
                                hands[0].add(s.pop());
                            }
                        }
                        clearPrevPlay();
                    }
                    break;
                }
            }
            if (!bsCalled) System.out.println("Nope!");
        }
    }

    /**
     * control the human player's possible actions
     *
     * @return boolean true if won, false if not
     */
    private boolean humanPlayer() {
        Ranks r = rc.next();
        currentRank = r;
        System.out.println("Play card Rank " + r.toString() + " for this " +
                "turn.");
        if (hands[0].containsRanks(r)) {
            hands[0].showHand(r);
            System.out.println(r.toString() + " is in your hand.");
            humanPlayerInput();
        } else {
            hands[0].showHand(r);
            System.out.println("You have no current Rank cards. BS a " +
                    "card!");
            System.out.println("Pick the card position to play. 1 to " +
                    hands[0].size());
            String chosen = in.next().trim().toLowerCase();
            while (!tryParseInput(chosen)) {
                System.out.println("Incorrect input. " + "Type in number 1 to " +
                        hands[0].size());
                chosen = in.next().trim().toLowerCase();
            }
            int convertedInput = Integer.parseInt(chosen);
            if (convertedInput < 1) convertedInput = 1;
            if (convertedInput > hands[0].size()) convertedInput = hands[0]
                    .size();
            Card bsCard = hands[0].bsPlay(convertedInput);
            pile.add(bsCard);
            Card fakedCard = DeckOfCards.pickRandomSuitOfRank(r);
            recordPrevPlay(fakedCard);
            System.out.println("You played " + bsCard.getData() + " as " + r.toString());
            System.out.println("Is anyone suspicious of you?");
        }
        return hands[0].size() == 0;
    }

    /**
     * an assisting function for humanPlayer()
     */
    private void humanPlayerInput() {
        System.out.print("\nEnter 'p' to play the current Rank.\nInput: ");
        String input = in.next().toLowerCase();
        if (input.equals("p")) {
            System.out.println("\nPlaying all " + currentRank.toString() +
                    " cards");
            int i = 0;
            while (hands[0].containsRanks(currentRank)) {
                Card c = hands[0].getNextCardOfRank(currentRank);
                recordPrevPlay(c);
                pile.add(hands[0].remove(c));
                i++;
            }
            System.out.println("You played " + i + " " +
                    currentRank.toString() + " " + "cards");
            System.out.println("Will anyone wrongly accuse you?");
        } else {
            System.out.println("Not a correct input.");
            this.humanPlayerInput();
        }
    }

    /**
     * handles the computer players' actions
     *
     * @param hand         the current computer player
     * @param playerNumber the current computer player number
     * @return boolean true if won, false if not
     */
    private boolean cpuPlayer(Hand hand, int playerNumber) {
        Ranks r = rc.next();
        currentRank = r;
        System.out.println("\nPlayer " + playerNumber + "'s turn.");
        if (hand.containsRanks(currentRank)) {
            int i = 0;
            while (hand.containsRanks(currentRank)) {
                Card c = hand.getNextCardOfRank(currentRank);
                recordPrevPlay(c);
                pile.add(hand.remove(c));
                i++;
            }
            System.out.println("Player " + playerNumber + " played " + i + " " +
                    currentRank.toString() + " cards");
        } else {
            double rollCardsToPlay = random.nextDouble();
            if (rollCardsToPlay < COMPUTER_BS_FOUR_CARDS_CHANCE) {
                playBunchOfRandomCards(hand, playerNumber, 4);
            } else if (rollCardsToPlay < COMPUTER_BS_THREE_CARDS_CHANCE) {
                playBunchOfRandomCards(hand, playerNumber, 3);
            } else if (rollCardsToPlay < COMPUTER_BS_TWO_CARDS_CHANCE) {
                playBunchOfRandomCards(hand, playerNumber, 2);
            } else {
                playBunchOfRandomCards(hand, playerNumber, 1);
            }
        }
        return hand.size() == 0;
    }

    /**
     * assists the computer player on playing fake cards
     * if there are no possible cards to play, play a combination of BS cards
     *
     * @param hand         the current computer player
     * @param playerNumber the current computer player number
     * @param amount       the number of cards to play. Max 4
     */
    private void playBunchOfRandomCards(Hand hand, int playerNumber, int
            amount) {
        int plays = amount;
        if (plays > hand.size()) plays = hand.size();
        for (int i = 0; i < plays; i++) {
            int randomRoll = random.nextInt(hand.size());
            pile.add(hand.removeCardAt(randomRoll));
            Card fakedCard = DeckOfCards.pickRandomSuitOfRank(currentRank);
            recordPrevPlay(fakedCard);
        }
        System.out.println("Player " + playerNumber + " played " + plays +
                " " + currentRank.toString() + " cards");
    }

    /**
     * record the previous player's choice of card that was played
     *
     * @param c the card that was played
     */
    private void recordPrevPlay(Card c) {
        PlayingCard pc = (PlayingCard) c;
        Suits s = pc.getSuit();
        Ranks r = pc.getRank();
        Card newCard = new PlayingCard(s, r);
        prevCardsPlayed.push(newCard);
        prevCardsPlayedCount++;
    }

    /**
     * clear the last player's choice of card that was played
     */
    private void clearPrevPlay() {
        prevCardsPlayedCount = 0;
        prevCardsPlayed.clear();
    }

    /**
     * check if the previous player played BS cards
     *
     * @return true if BS'd, false if not
     */
    private boolean checkIfBs() {
        Stack<Card> store = new Stack<>();
        int e = prevCardsPlayed.size(); // TODO
        for (int i = 0; i < e; i++) {
            Card previousInPile = pile.deal();
            Card previousInPlayed = prevCardsPlayed.pop();
            store.push(previousInPile);
            if (!store.peek().getType().equals(previousInPlayed.getType()) ||
                    !store.peek().getData().equals(previousInPlayed.getData())) {
                clearPrevPlay();
                while (!store.empty()) pile.add(store.pop());
                return true;
            }
        }

        clearPrevPlay();
        for (int j = 0; j < e; j++) {
            pile.add(store.pop());
        }
        return false;
    }

    /**
     * an assisting tool to determine if the human player's input is
     * parse-able
     *
     * @param input the input to parse
     * @return true if parse-able, false if not
     */
    private boolean tryParseInput(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}