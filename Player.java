/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class that defines a player for a game with monetary winnings and a limited
 * number of choices
 *
 * @author Luz Hernandez
 */
public class Player {

    private int winnings = 0; // amount of money won
    private String name; // player's name
    private int numGuesses = 0; // number of times they've tried to solve puzzle
    private ArrayList<String> prizeTracker = new ArrayList<String>();   //arraylist to track prizes
    private String prize;   //prize as string

    /**
     * Populates prizes for prizeTracker
     *
     * @param prizes the prizes the player can earn
     * @throws InterruptedException
     */
    public void addPrize(ArrayList prizes) throws InterruptedException {
        Random randomGenerator = new Random();  //initialize a new randomGenerator
        int randomInt = randomGenerator.nextInt(4); //a randomInt number is generated between 0-3

        if (randomInt == 0) {
            prize = (String) prizes.get(0); //get first prize in prizes
            prizeTracker.add(prize);   //populate the prizeTracker by adding prize
        } else if (randomInt == 1) {
            prize = (String) prizes.get(1); //get first prize in prizes
            prizeTracker.add(prize);    //populate the prizeTracker by adding prize
        } else if (randomInt == 2) {
            prize = (String) prizes.get(2); //get first prize in prizes
            prizeTracker.add(prize);    //populate the prizeTracker by adding prize
        } else if (randomInt == 3) {
            prize = (String) prizes.get(3); //get first prize in prizes
            prizeTracker.add(prize);    //populate the prizeTracker by adding prize
        }
        System.out.println(prizeTracker);   //prints the prizeTracker arraylist
    }

    /**
     * Constructor
     *
     * @param name String that is the player's name
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return int amount of money won so far
     */
    public int getWinnings() {
        return winnings;
    }

    /**
     *Getter
     * 
     * @return the prizeTracker array
     */
    public ArrayList<String> getPrizes() {
        return prizeTracker;
    }

    /**
     * Adds amount to the player's winnings
     *
     * @param amount int money to add
     */
    public void incrementScore(int amount) {
        if (amount < 0) {
            return;
        }
        this.winnings += amount;
    }

    /**
     * Getter
     *
     * @return String player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     *
     * @return int the number of guesses used up
     */
    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * Add 1 to the number of guesses used up
     */
    public void incrementNumGuesses() {
        this.numGuesses++;
    }

    /**
     * Resets for a new game (only number of guesses) This does not reset the
     * winnings.
     */
    public void reset() {
        this.numGuesses = 0;
    }

    /**
     * Initializes the winnings and prizes
     */
    public void bankrupt() {
        this.winnings = 0;
        this.prize = "";
    }

}
