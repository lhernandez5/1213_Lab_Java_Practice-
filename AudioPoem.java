/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HernandezLuz_Assignment_ITIS1213;

import BookClasses.FileChooser;
import BookClasses.Sound;
import BookClasses.SoundException;
import BookClasses.SoundSample;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

/**
 * This class contains methods for mixing up the words in an audio file and
 * creating sound poetry out of them. It contains many stub methods which need
 * to be completed as part of Assignment 1.
 *
 * @author Luz Hernandez
 */
public class AudioPoem {

    static final int MAX_NUM_WORDS = 100;
    static private Sound[] myWordArray = new Sound[MAX_NUM_WORDS];

    static private int numWords = 0;

    public AudioPoem(Sound originalSource, int[] spliceArray, int numSplicePoints) {

        // break the sound into sepearate words, copying each into the word array
        for (int i = 0, j = 0; i < numSplicePoints; i = i + 2, j++) {
            myWordArray[j] = new Sound(spliceArray[i + 1] - spliceArray[i]);
            for (int x = spliceArray[i], y = 0; x < spliceArray[i + 1]; x++, y++) {
                myWordArray[j].setSampleValueAt(y, originalSource.getSampleValueAt(x));
            }
            numWords++;
        }

    }

    /**
     * Plays the words, in order with a 200 millisecond pause between each
     *
     * @throws InterruptedException
     */
    public void play() throws InterruptedException {
        // play the words in order with 200 millisecond pause
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();
            Thread.sleep(200);
        }
    }

    /**
     * Plays the words, in order with a parameter-specified pause between each
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void play(int pause) throws InterruptedException {
        //Will play the sound with pauses indicated by the parameter that is taken in
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();  
            Thread.sleep(pause);    
        }
    }

    /**
     * Plays the words, in order with a parameter-specified pause between each
     * and writes the resulting sound out to a file
     *
     * @param pause the number of milliseconds to pause between words
     * @param filename the name of the file to write
     * @param path the path where the file should be written
     * @throws InterruptedException
     */
    public void play(int pause, String filename, String path) throws InterruptedException {
        double sampleRateWord= myWordArray[0].getSamplingRate();    //get the sampling rate 
        int samplesInPause= (int)((pause/1000)*sampleRateWord); //get samples in one pause
        int sampleTotal=((numWords) * (samplesInPause));    //samples in total
        int numberSamples = 0;
        int intoSound=0;
        Sound sound_1 = new Sound(sampleTotal + numberSamples); //created sound instance sound_1
        
        for (int i = 0; i< numWords; i++){
            numberSamples += myWordArray[i].getNumSamples();    //number of samples at index i
        }

        for (int i = 0; i< numWords; i++){
            for (int j = 0; j < myWordArray[i].getLength(); j++){
                sound_1.setSampleValueAt(intoSound, myWordArray[i].getSampleValueAt(j));    //setting values in instance sound_1
                intoSound++;
            }
            intoSound+=samplesInPause;
        }
        
        for (int i = 0; i < numWords; i++) {
            myWordArray[i].blockingPlay();   
            Thread.sleep(pause);    //play words with pause parameter passed by user
        }
        
        sound_1.write(path + filename); //Write sound_1 instance to given file in given path
    }

    /**
     * Plays the words in random order, each word can be played multiple times
     *
     * @param totalWords the total number of words that will be played
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playRandomOrder(int totalWords, int pause) throws InterruptedException {
        //created a new instance of the random class
        Random randomGenerator = new Random();
        
        //generated the randomInt number that will play the random word multiple times
        for (int i = 0; i < totalWords; ++i) {
            int randomInt = (int)randomGenerator.nextInt(4);    //generates random int 0 to 3
            for (int j = 0; j < numWords; j++) {
                myWordArray[randomInt].blockingPlay(); //plays words randomly 
                Thread.sleep(pause);    //takes in pause parameter set by user
            }
        }
    }
    
    /**
     * Plays the words in random order, each word can be played multiple times
     *
     * @param totalWords the total number of words that will be played
     * @param pause the number of milliseconds to pause between words
     * @param filenamethe name of the file to write
     * @param path the path where the file should be written 
     * @throws InterruptedException
     */
    public void playRandomOrder(int totalWords, int pause, String filename, String path){
        Sound[] sound_2 = new Sound[totalWords];    //new instance sound_2
        Random randomGenerator = new Random();
        double sampleRateWord= myWordArray[0].getSamplingRate();    //obtain sample rate for each word
        double samplesInPause= (int)((pause/1000)*sampleRateWord);  //obtain samples in a pause 
        int length = 0;
        int intoSound = 0;
        
        for (int i = 0; i < totalWords; ++i) {
            int randomInt = (int)randomGenerator.nextInt(4);    //generates random int 0-3
            for (int j = 0; j < numWords; j++) {    
                sound_2[i] = myWordArray[randomInt];    //will play a random word at index i
            }
        }
        
        for (int i = 0; i< sound_2.length; i++){
        length+=sound_2[i].getLength();     //add the length of sample at index i
        }
        
        length+=((totalWords-1)* samplesInPause);   //add the the length of the word and the pause
        Sound newSound = new Sound(length);
        
        for(int i = 0; i< sound_2.length; i++){
            Sound soundForNow = sound_2[i];
            for(int j = 0; j< soundForNow.getLength();j++){
                newSound.setSampleValueAt(intoSound, soundForNow.getSampleValueAt(j));  //set the values at index i for new sound instance
                intoSound++;
            }
            intoSound+=samplesInPause; 
        }
            newSound.write(path+filename);  //write the new instance to the file in the path given by user
            newSound.blockingPlay();    
    }

    /**
     * Plays the words in random order, playing each word only once
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playRandomUnique(int pause) throws InterruptedException {
        Sound[] newSound = new Sound[numWords]; //created new instance of the array Sound, 
        Random randomGenerator = new Random();  
        int randomInt = (int) randomGenerator.nextInt(numWords);    //generated the random number based on the length of number of words
        
        
        for (int i = 0; i < numWords; ++i) {    
            newSound[i] = myWordArray[i];   //created a new Sound instance to temporiarly hold sound and then place back into array
            myWordArray[i] = myWordArray[randomInt];    
            myWordArray[randomInt] = newSound[i];   
        }
        
        
        for (int j = 0; j < numWords; ++j) {
            myWordArray[j].blockingPlay();  //plays myWord Array with random value at each index and will play once
            Thread.sleep(pause);   
        }
    }
    
    /**
     * Plays the words in random order, playing each word only once
     *
     * @param pause the number of milliseconds to pause between words
     * @param filenamethe name of the file to write
     * @param path the path where the file should be written 
     * @throws InterruptedException
     */
    public void playRandomUnique(int pause, String filename, String path) throws InterruptedException {
        Sound[] sound_2 = new Sound[numWords];  //created new instance called sound_2
        Random randomGenerator = new Random();
        double sampleRateWord= myWordArray[0].getSamplingRate();    // obtained the sample rate per word 
        double samplesInPause= (int)((pause/1000)*sampleRateWord);  //number of pauses calculated by multipying by the sample rate 
        int length = 0;
        int intoSound = 0;
        
        int randomInt = (int) randomGenerator.nextInt(4);   //generated a random number form 0-3
        for (int i = 0; i < numWords ; ++i) {    
            sound_2[i] = myWordArray[i];  //value at myWordArray index i equals to the value in the sme index in sound_2
            myWordArray[i] = myWordArray[randomInt];    // fing the value of random value in myWordArray   
            myWordArray[randomInt] = sound_2[i];  // Make the random value take same place in sound_2  
        }
        
        for (int j = 0; j < numWords; ++j) {
            myWordArray[j].blockingPlay();
            Thread.sleep(pause);    // take in the pause parameter set by user
           } 
        
        for (int i = 0; i< sound_2.length; i++){
        length+=sound_2[i].getLength(); //add the length of sound_2 at i to the length
        }
        
        length+=((numWords-1)* samplesInPause); //to the length multiply by the samples in a paue
        Sound newSound = new Sound(length); // new sound instance
        
        for(int i = 0; i< sound_2.length; i++){
            Sound soundForNow = sound_2[i];
            for(int j = 0; j< soundForNow.getLength();j++){
                newSound.setSampleValueAt(intoSound, soundForNow.getSampleValueAt(j));  // set new values for newSound
                intoSound++; //iterate for everylocation
            }
            intoSound+=samplesInPause; 
        }
        newSound.write(path+filename);   //write values of newSound into new file and new path        
    }

    /**
     * Plays the sound words in reverse order (e.g. 'this is a test' will be
     * played 'test a is this')
     *
     * @param pause the number of milliseconds to pause between words
     * @throws InterruptedException
     */
    public void playReverseOrder(int pause) throws InterruptedException {
        for (int i = numWords - 1; i >= 0; i--) {
            myWordArray[i].blockingPlay();  //plays the values in myWordArray in reverse order 
            Thread.sleep(pause);   //takes in pause parameter 
        } 
    }
    
    /**
     * Plays the sound words in reverse order (e.g. 'this is a test' will be
     * played 'test a is this')
     *
     * @param pause the number of milliseconds to pause between words
     * @param filenamethe name of the file to write
     * @param path the path where the file should be written 
     * @throws InterruptedException
     */
    public void playReverseOrder(int pause, String filename, String path) throws InterruptedException {
        Sound[] sound_2 = new Sound[numWords];  //new array called sound 
        Random randomGenerator = new Random();  //random generator 
        double sampleRateWord= myWordArray[0].getSamplingRate();    //determines the sampling rate by index
        double samplesInPause= (int)((pause/1000)*sampleRateWord);  //samples in pause 
        int length = 0;
        int intoSound = 0;
        
        for (int i = numWords - 1; i >= 0; i--) {   //this loop plays the words in reverse order
            myWordArray[i].blockingPlay(); 
            Thread.sleep(pause); 
           } 
        
        for (int i = 0; i< sound_2.length; i++){
        length+=sound_2[i].getLength(); // the length is incremented by the length in sound_2
        }
        
        length+=((numWords-1)* samplesInPause);
        Sound newSound = new Sound(length); //new sound created to write file
        
        for(int i = 0; i< sound_2.length; i++){ //will iterate based on length of sound
            Sound soundForNow = sound_2[i]; //get values in sound_2 array into soundForNow
            for(int j = 0; j< soundForNow.getLength();j++){
                newSound.setSampleValueAt(intoSound, soundForNow.getSampleValueAt(j)); //set values in my newSound
                intoSound++;
            }
            intoSound+=samplesInPause; //adds sample in pause
        }
        newSound.write(path+filename);  // newSound written to new file and path
    }

    /**
     * Plays random consecutive pairs of words with only a 100 millisecond pause
     * between them, with a four hundred millisecond pause between pairs Ex: for
     * 'this is a test' a pair would be 'this is' or 'is a' or 'a test'
     *
     * @param numDoublets the number of doublets to play
     * @throws InterruptedException
     */
    public void playDoublets(int numDoublets) throws InterruptedException {
        Random randomGenerator = new Random();  //Random class randon number generator
        for (int i = 0; i < numDoublets; i++) {
            int randomInt = randomGenerator.nextInt(3); //generate random number between 0-3
            int two = randomInt + 2;    //we want double samples to be played
            
            while (randomInt < two) {
                myWordArray[randomInt].blockingPlay();  //plays the double words
                Thread.sleep(100); 
                randomInt++;    // iterates over the randomInt number
            }
            Thread.sleep(400); 
        }
    }
    
    /**
     * Plays random consecutive pairs of words with only a 100 millisecond pause
     * between them, with a four hundred millisecond pause between pairs Ex: for
     * 'this is a test' a pair would be 'this is' or 'is a' or 'a test'
     *
     * @param numDoublets the number of doublets to play
     * @param filenamethe name of the file to write
     * @param path the path where the file should be written 
     * @throws InterruptedException
     */
    public void playDoublets(int numDoublets, String filename, String path) throws InterruptedException {
        Sound[] sound_2 = new Sound[numDoublets*2]; //new sound array that takes in the number of doublets and multiplies by 2
        Random randomGenerator = new Random();
        
        double sampleRateWord= myWordArray[0].getSamplingRate();    //calculation to figure out the sampling rate
        int pause_1 = (int)((100/1000)* sampleRateWord);    // determines the shorter pause between words 
        int pause_2 = (int)((400/1000)* sampleRateWord);    //determines the larger pause between the pairs
        int length = 0;
        
        for (int i = 0; i < numDoublets; i++) {
            int randomInt = randomGenerator.nextInt(3); //generates random number between 0-3 
            int two = randomInt + 2; // to get the doublets I added 2
            
            while (randomInt < two) {   //loop will iterate until we have two words playing
                sound_2[i]=myWordArray[randomInt];  //find the word within the array in a specific index
                sound_2[i].blockingPlay();  //plays the doublets
                Thread.sleep(100);  
                randomInt++;
            }
            Thread.sleep(400); 
        }
        
        length += ((pause_1 + pause_2) * numDoublets);
        Sound newSound = new Sound(length);
        
        newSound.write(path+filename);  //newSound written out to file within the assigned path 
    }


    /**
     * Plays random consecutive triplets of words with only a 100 millisecond
     * pause between the three words, with a four hundred millisecond pause
     * between triplets Ex: for 'this is a test' a triplet would be 'this is a'
     * or 'is a test'
     *
     * @param numTriplets the number of triplets to play
     * @throws InterruptedException
     */
    public void playTriplets(int numTriplets) throws InterruptedException {
        Random randomGenerator = new Random();  // instance of random generator
        
        for (int j = 0; j < numTriplets; j++) {
            int randomInt = randomGenerator.nextInt(2); // we want to generate numbers 0 and 1 because they are the options for start
            int three = randomInt + 3;  //since we want triplets we add 3
            
            while (randomInt < three) {     //will run at least randomInt is less than the value of variable 3
                myWordArray[randomInt].blockingPlay(); //Will play words in groups of 3 
                Thread.sleep(100);  
                randomInt++;    //will iterate over randomInt number
            }
            Thread.sleep(400);  
        }
    }
    
    /**
     * Plays random consecutive triplets of words with only a 100 millisecond
     * pause between the three words, with a four hundred millisecond pause
     * between triplets Ex: for 'this is a test' a triplet would be 'this is a'
     * or 'is a test'
     *
     * @param numTriplets the number of triplets to play
     * @param filenamethe name of the file to write
     * @param path the path where the file should be written 
     * @throws InterruptedException
     */
    public void playTriplets(int numTriplets, String filename, String path) throws InterruptedException {
        Sound[] sound_2 = new Sound[numTriplets*3];
        Random randomGenerator = new Random();
        
        double sampleRateWord= myWordArray[0].getSamplingRate();    //Will calculate the sample rate per word starting from the first index in myWordArray
        int pause_1 = (int)((100/1000)* sampleRateWord);    //will calculate the shorter of the pauses between words
        int pause_2 = (int)((400/1000)* sampleRateWord);    //will calculate the longer of the pauses between triplets
        int length = 0;
        
        for (int i = 0; i < numTriplets; i++) { //will iterate while i is less than the number of Triplets
            int randomInt = randomGenerator.nextInt(2); //will generate a number between 0-2
            int three = randomInt + 3;  //will give us the triplets we are looking for
            
            while (randomInt < three) { //will iterate until the randomInt is less than the variable three 
                sound_2[i]=myWordArray[randomInt];
                sound_2[i].blockingPlay();
                Thread.sleep(100);  
                randomInt++;    //will iterate randomInt times
            }
            Thread.sleep(400); 
        }
       
        length += ((pause_1 + pause_2)* numTriplets); //length is determined by taking both pauses and multipying then to the numbe of triplets
        Sound newSound = new Sound(length);
        
        
        newSound.write(path+filename);  //will write values form newSound instance to path int he path chosen
        
    
}

}
