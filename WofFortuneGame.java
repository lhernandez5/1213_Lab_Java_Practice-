/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WofFortuneGame class Contains all logistics to run the game
 *
 * @author Luz Hernandez
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;
    private Wheel wheel;
    private String phrase = "Once upon a time"; //default phrase
    private ArrayList<Letter> arrayListOfLetters = new ArrayList<Letter>(); //Arraylist of the letters in the phrases
    private ArrayList<String> arrayListOfPhrases = new ArrayList<String>(); //Arraylist of the phrases
    private ArrayList<Player> arrayListOfPlayers = new ArrayList<Player>(); //Arraylist of the players 
    private ArrayList<Integer> arrayListOfScores = new ArrayList<Integer>();    //Arraylist of scores
    private ArrayList<String> prizes = new ArrayList<String>(); //Arraylist of prizes

    /**
     * Constructor
     *
     * @param wheel Wheel
     * @throws InterruptedException
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        // get the wheel
        this.wheel = wheel;
        // do all the initialization for the game
        setUpGame();
    }

    /**
     * Plays the game giving each player a turn
     *
     * @throws InterruptedException
     */
    public void playGame() throws InterruptedException {
        int k = 0;  //initalizes k 
        int n = arrayListOfPlayers.size();  //declares the size of the arraylist
        do {
            int i = k++ % n;    //remainder equates to the index
            playTurn(arrayListOfPlayers.get(i));    //gives the player at i index a turn
        }// use i
        while (!puzzleSolved);  //while the puzzle is not solved iterate through each player

    }

    /**
     * Adds 10 phrases to the arrayListOfPhrases
     *
     * @throws InterruptedException
     */
    private void setPhrases() throws InterruptedException {
        arrayListOfPhrases.add(0, "Carolina");  //adds pharse to arraylist
        arrayListOfPhrases.add(1, "Roses are red"); //adds pharse to arraylist
        arrayListOfPhrases.add(2, "Yesterday"); //adds pharse to arraylist
        arrayListOfPhrases.add(3, "This is an assignment"); //adds pharse to arraylist
        arrayListOfPhrases.add(4, "Random");    //adds pharse to arraylist
        arrayListOfPhrases.add(5, "Breif and concise"); //adds pharse to arraylist
        arrayListOfPhrases.add(6, "UNC Charlotte"); //adds pharse to arraylist
        arrayListOfPhrases.add(7, "Web development");   //adds pharse to arraylist
        arrayListOfPhrases.add(8, "College");   //adds pharse to arraylist
        arrayListOfPhrases.add(9, "Extordinary");   //adds pharse to arraylist
    }

    /**
     * Adds 4 prizes to the prizes arraylist
     *
     * @throws InterruptedException
     */
    private void setPrizes() throws InterruptedException {
        prizes.add(0, "Cruise");    //adds pharse to arraylist
        prizes.add(1, "New car");   //adds pharse to arraylist
        prizes.add(2, "House"); //adds pharse to arraylist
        prizes.add(3, "Island");    //adds pharse to arraylist
    }

    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() throws InterruptedException {
        this.setPhrases();  //is used as a reference to the object of the setPhrases class
        this.setPrizes();   //is used as a reference to the object of the setPrizes class

        Scanner sc = new Scanner(System.in);

        // will allow the user to enter the players names
        System.out.println("How many people are going to play? ");
        try {
            int numPlayers = sc.nextInt();  //declaration of number of player input
            for (int j = 1; j <= numPlayers; j++) {
                System.out.println("Name of player number " + j + " player: ");
                String name = sc.next();    //declaration of name of player as a string
                arrayListOfPlayers.add(new Player(name));   //add player's name to arraylist
            }

        } catch (InputMismatchException ex) {   //throws exception 
            System.out.println("You need to enter one number.");

        }

        // print out the rules
        System.out.println();
        System.out.println("-------------------------------RULES!-----------------------------------------");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println(" the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has 1 guess per round regardless if the letter is guessed or not. ");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println();

        System.out.println("Would you like to enter your own phrase? ");

        try {

            char ans = sc.next().charAt(0); //declaration of answer as char 
            if ((ans == 'y') || (ans == 'Y')) {
                System.out.println("Enter your phrase:");

                String otherString = sc.next(); //the input declares input as otherString 
                otherString += sc.nextLine();

                phrase = phrase.replace(phrase, otherString);   //replacing the default with the user phrase
                for (int i = 0; i < phrase.length(); i++) {
                    arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                }
            } else if ((ans == 'n') || (ans == 'N')) {
                System.out.println("You do not want to create your own phrase so, a random phrase will be chosen for you.");

                Random randomGenerator = new Random();  //declaration for randomGenerator
                int randomInt = randomGenerator.nextInt(10);    //generates random numbers 0-9
                if (randomInt == 0) {
                    phrase = arrayListOfPhrases.get(0); //gets the first phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i))); //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 1) {
                    phrase = arrayListOfPhrases.get(1); //gets the second phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 2) {
                    phrase = arrayListOfPhrases.get(2); //gets the third phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 3) {
                    phrase = arrayListOfPhrases.get(3); //gets the fourth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 4) {
                    phrase = arrayListOfPhrases.get(4); //gets the fifth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 5) {
                    phrase = arrayListOfPhrases.get(5); //gets the sixth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 6) {
                    phrase = arrayListOfPhrases.get(6); //gets the seventh phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 7) {
                    phrase = arrayListOfPhrases.get(7); //gets the eigth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 8) {
                    phrase = arrayListOfPhrases.get(8); //gets the ninth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                } else if (randomInt == 9) {
                    phrase = arrayListOfPhrases.get(9); //gets the tenth phrase
                    for (int i = 0; i < phrase.length(); i++) {
                        arrayListOfLetters.add(new Letter(phrase.charAt(i)));   //add letters in phrase to letter arraylist
                    }
                }
            }
        } catch (Exception ex) {    //throws exception
            System.out.println("You need to enter letter. Error :" + ex);   
        }

        // setup done   
    }

    /**
     * One player's turn in the game Spin wheel, pick a letter, choose to solve
     * puzzle if letter found
     *
     * @param player
     * @throws InterruptedException
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;  //initialize money to 0
        Scanner sc = new Scanner(System.in);

        System.out.println(player.getName() + ", you have $" + player.getWinnings());   //prints the winnings for the current guess
        System.out.println("Spin the wheel! <press enter>");
        try {
            sc.nextLine();
        } catch (Exception ex) {    //general exception is thrown
            System.out.println("Error: " + ex);
        }
        System.out.println("<SPINNING>");

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) { //interrupted expection is thrown
            System.out.println("Thread was interrupted.");
        }

        Wheel.WedgeType type = wheel.spin();
        System.out.print("The wheel landed on: ");
        switch (type) {
            case MONEY:
                money = wheel.getAmount();
                System.out.println("$" + money);
                break;

            case LOSE_TURN:
                System.out.println("LOSE A TURN");
                System.out.println("So sorry, you lose a turn.");
                return; // doesn't get to guess letter

            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                return; // doesn't get to guess letter
            case PRIZE:
                System.out.println("PRIZE");
                player.addPrize(prizes);
                break;
            default:

        }

        System.out.println("");
        System.out.println("Here is the puzzle:");
        showPuzzle();
        System.out.println();
        System.out.println(player.getName() + ", please guess a letter.");

        try {
            char letter = sc.next().charAt(0);
            if (!Character.isAlphabetic(letter)) {
                System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
            } else {
                // search for letter to see if it is in
                int numFound = 0;
                for (Letter l : arrayListOfLetters) {
                    if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter))) {
                        l.setFound();
                        numFound += 1;
                    }
                }

                if (numFound == 0) {
                    System.out.println("Sorry, but there are no " + letter + "'s.");
                } else {
                    if (numFound == 1) {
                        System.out.println("Congrats! There is 1 letter " + letter + ":");
                    } else {
                        System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                    }
                    System.out.println();
                    showPuzzle();
                    System.out.println();
                    player.incrementScore(numFound * money);
                    System.out.println("You earned $" + (numFound * money) + ", and you now have: $" + player.getWinnings());

                    System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                    letter = sc.next().charAt(0);
                    System.out.println();
                    if ((letter == 'Y') || (letter == 'y')) {
                        solvePuzzleAttempt(player);

                    }

                }
            }

        } catch (Exception ex) {    //general exception is thrown
            System.out.println("Error: " + ex);
        }
    }

    /**
     * Logic for when user tries to solve the puzzle
     *
     * @param player
     */
    private void solvePuzzleAttempt(Player player) {

        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        try {
            Scanner sc = new Scanner(System.in);
            sc.useDelimiter("\n");

            String guess = sc.next();   //declaration of guess string variable

            if (guess.compareToIgnoreCase(phrase) == 0) {
                System.out.println("Congratulations! You guessed it!");
                puzzleSolved = true;

                // Round is over. Write message with final stats
                int i = 0;  //initializes i to 0
                int scoreOfWinner = 0;  //initializes score of winner to 0

                //for every player in the array list list the money earned and the prizes if any earned
                for (Player p : arrayListOfPlayers) {
                    System.out.println(p.getName().toUpperCase() + " your final winnings are: $" + p.getWinnings() + ".");
                    System.out.println(p.getName().toUpperCase() + " your prizes are: " + p.getPrizes());
                    arrayListOfScores.add(i, p.getWinnings());  //populates the arraylist of scores
                }

                System.out.println();   //print an empty line
                Collections.sort(arrayListOfScores);    //sorts the scores form least to greatest
                scoreOfWinner = arrayListOfScores.get(arrayListOfScores.size() - 1); //grab the last score in the arraylist
                for (Player p : arrayListOfPlayers) {
                    if (p.getWinnings() == scoreOfWinner) {
                        System.out.println(p.getName().toUpperCase() + " YOU WON!!!");  //prints the name of the player who won
                    }
                }

            } else {
                //will print if guess is incorrect
                System.out.println("Sorry, but that is not correct.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex); //general exception is thrown
        }

    }

    /**
     * Display of the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : arrayListOfLetters) {
            if (l.isSpace()) {
                System.out.print("   ");    //prints spaces
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");   //prints the letter that was guessed correctly
                } else {
                    System.out.print(" _ ");    //empty line for letter not guessed
                }
            }
        }
        System.out.println();
    }

    /**
     * For a new game reset player's number of guesses to 0
     */
    public void reset() {
        if (!puzzleSolved) {
            //will reset values for game for each player
            for (Player p : arrayListOfPlayers) {
                p.bankrupt();
                p.reset();
            }

        }
    }
}
