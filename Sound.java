package BookClasses;


/**
 * Class that represents a sound.  This class is used by the students
 * to extend the capabilities of SimpleSound. 
 * 
 * Copyright Georgia Institute of Technology 2004
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Sound extends SimpleSound
{
  
  /////////////// consructors ////////////////////////////////////
  
  /**
   * Constructor that takes a file name
   * @param fileName the name of the file to read the sound from
   */
  public Sound(String fileName)
  {
    // let the parent class handle setting the file name
    super(fileName);
  }
  
  /**
   * Constructor that takes the number of samples in
   * the sound
   * @param numSamples the number of samples desired
   */
  public Sound (int numSamples)
  {
    // let the parent class handle this
    super(numSamples);
  }
  
  /**
   * Constructor that takes the number of samples that this
   * sound will have and the sample rate
   * @param numSamples the number of samples desired
   * @param sampleRate the number of samples per second
   */
  public Sound (int numSamples, int sampleRate)
  {
    // let the parent class handle this
    super(numSamples,sampleRate);
  }
  
  /**
   * Constructor that takes a sound to copy
   */
  public Sound (Sound copySound)
  {
    // let the parent class handle this
    super(copySound);
  }
  
  ////////////////// methods ////////////////////////////////////
  
  /**
   * Method to return the string representation of this sound
   * @return a string with information about this sound
   */
  public String toString()
  {
    String output = "Sound";
    String fileName = getFileName();
    
    // if there is a file name then add that to the output
    if (fileName != null)
      output = output + " file: " + fileName;
    
    // add the length in frames
    output = output + " number of samples: " + getLengthInFrames();
    
    return output;
  }
 
  public static void main(String[] args)
  {
    Sound sound1 = new Sound(FileChooser.pickAFile());
    sound1.explore();
  }
  /**
   * This method cuts off values before 0 in the sound file
   * and returns the number of samples that were cut.
   * @return integer representing how many values were cut off in the 
   * sound file is returned
   */
  public int clipLows(){
      int numChanged = 0;
      SoundSample[] sampleArray = this.getSamples();
      SoundSample sample = null;
      
      //loop through sample values
      for (int i = 0; i < sampleArray.length; i++){
          //get the current sample
          sample = sampleArray[i];
          
          if(sample.getValue() < 0){
              sample.setValue(0);
              numChanged++;
          }
      }
      
      return numChanged;
  }
  public void blackOutRange(int startIndex, int endIndex){
      
  }
  
  // MC Textbook Programs 73, 76, 78
  
  //Program 73: Splice Words into a Single Sentence
  
  /**
   * Method to splice two sounds together with some slence between them into the current sound
   */
  public void splice (){
      Sound sound1 =
              new Sound(FileChooser.getMediaPath("guzdial.wav"));
      Sound sound2 =
              new Sound(FileChooser.getMediaPath("is.wav"));
      int targetIndex = 0; // the starting place on the target
      int value = 0;
      
      // copy all of sound 1 into the current sound (target)
      
      for (int i = 0; i < sound1.getLength(); i++, targetIndex++){
          value = sound1.getSampleValueAt(i);
          this.setSampleValueAt(targetIndex,value);
          }
      // create silence between words by setting values to 0
      for (int i = 0; i < (int) (this.getSamplingRate() * 0.1); i++, targetIndex++){
          this.setSampleValueAt(targetIndex, 0);
      }
      
      // copy all of sound 2 into the current sound (target)
      for (int i = 0; i < sound2.getLength(); i++, targetIndex++){
          value = sound2.getSampleValueAt(i);
          this.setSampleValueAt(targetIndex, value);
      }
  }
    
  // Program 76: General Splice Method
    
    /**
     * Method to copy part of the passed sound into this sound at 
     * the given start index
     * @param source the source sound to copy from
     * @param sourceStart the starting index to copy from in the 
     * source(the copy will include this)
     * @param sourceStop the ending index (the copy wont include
     * this)
     * @param targetStart the index to start copying into
     */
    public void splice(Sound source, int sourceStart, int sourceStop, int targetStart){
        // loop copying from source to target
        
        for (int sourceIndex = sourceStart, targetIndex = targetStart;
                 sourceIndex < sourceStop && targetIndex < this.getLength();
                 sourceIndex++, targetIndex++)
            this.setSampleValueAt(targetIndex, source.getSampleValueAt(sourceIndex));
    }
 // Program 78: Reverse a Sound
    /**
     * Method to reverse the current sound
     */
    public void reverse(){
        Sound orig = new Sound(this.getFileName());
        int length = this.getLength();
        
        // loop through the samples
        
        for (int targetIndex = 0, sourceIndex = length -1;
                targetIndex < length && sourceIndex > 0;
                targetIndex++, sourceIndex--)
            this.setSampleValueAt(targetIndex,
                                  orig.getSampleValueAt(sourceIndex));
    }
 // Program dave: Remove sounds at every other index
    /**
     * This removes the sound from every other index. Why would you need this? Idk
     * @return Sound
     */
    public Sound zeroAlternatingSamples(){
        int j = 0;
        Sound sound = new Sound(this.getLength());
        for (int i = 0;this.getLength() > i;i+=2){
            j = this.getSampleValueAt(i);
            sound.setSampleValueAt(i,j);
        }
        return sound;
    }
    // Program Remove: Adjust the sound
    /**
     * This replaces the sound at index i with the sound previous. Why would you need this? Idk
     * @return Sound
     */
    public Sound repeatAlternatingSamples(){
        int sample = 0;
        Sound sound = new Sound(this.getLength());
        sound = this;
        for (int i = 1;this.getLength() > i;i+=2){
            int j = i-1;
            sample = sound.getSampleValueAt(j);
            sound.setSampleValueAt(i,sample);
        }
        return sound;
    }
        public Sound skipSamples(){
        int j = 0;
        int k = 0;
        Sound sound = new Sound(this.getLength()/2);
        for (int i = 0;this.getLength() > i;i+=2){
            j = this.getSampleValueAt(i);
            sound.setSampleValueAt(k,j);
            k++;
        }
        return sound;
    }
        public Sound repeatAllSamples(){
        int sample = 0;
        int j = 0;
        Sound sound = new Sound(this.getLength()*2);
        for (int i = 0;this.getLength() > i;i++){
            sample = this.getSampleValueAt(i);
            sound.setSampleValueAt(j, sample);
            j++;
            sound.setSampleValueAt(j, sample);
            j++;
            
    }
      return sound; 
  }
}
 // this is the end of time
