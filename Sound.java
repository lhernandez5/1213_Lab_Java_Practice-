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
   * Method 65 to double the volume (amplitude) of the sound
   */
  
  public void increaseVolume()
  {
      SoundSample[] sampleArray = this.getSamples();
      SoundSample sample = null; 
      int value = 0;
      int index = 0;
      
      //loop through all the samples in the array 
      while (index < sampleArray.length)
      {
        sample = sampleArray[index];
        value = sample.getValue();
        sample.setValue(value * 2);
        index++;
  
       }
  }
  
  /**
   * Method 68 to change the volume (amplitude) of the sound by multipying the 
   * current values in the sound by the passed factor.
   * @param factor the factor to multiply by
   */
  public void changeVolume(double factor)
  {
      SoundSample[] sampleArray = this.getSamples();
      SoundSample sample = null;
      int value = 0;
      
      //loop through all the samples in the array
      for (int i = 0; i < sampleArray.length; i++)
      {
          sample = sampleArray[i];
          value = sample.getValue();
          sample.setValue((int) (value * factor));
      }   
  }
  
    /**
     * Method 69 to Normalize (make as loud as possible) a sound.
     */
    public void normalize()
    {
        int largest = 0;
        int maxIndex = 0;
        SoundSample[] sampleArray = this.getSamples();
        SoundSample sample = null;
        int value = 0;

        //loop comparing the absolute value of the current value to the current
        //largest
        for (int i = 0; i < sampleArray.length; i++)
        {
            sample = sampleArray[i];
            value = Math.abs(sample.getValue());
            if (value > largest)
            {
                largest = value;
                maxIndex = i;
            }              
        }
        //now calculate the multiplier to multiply by 
        double multiplier = 32767.0 / largest;
        //print out the largest value and the multiplier 
        System.out.println("The largest value was" + largest + "at index" + maxIndex);

        System.out.println("The multipier is" + multiplier);
        /**
         * loop through all the samples and multiply by the multiplier
        */
        for (int i = 0; i < sampleArray.length; i++)
        {
            sample = sampleArray[i];
            sample.setValue((int)(sample.getValue() * multiplier));

        }
  }
      
      /**
       * Method 70 to set all the sample values to the maximum positive value if 
       * they were positive (including 0) and the minimum negative value if they
       * were negative.
      */
      public void forceToExtremes()
      {
          SoundSample[] sampleArray = this.getSamples();
          SoundSample sample = null;
          
          //loop through the sample values
          for (int i = 0; i < sampleArray.length; i++)
          {
              //get the current sample
              sample = sampleArray[i];
              //if the value was positive (or zero) set it to the 
              //maximum positive value
              if (sample.getValue() >= 0)
                  sample.setValue(32767);
              
              //else force to max negative value
              else
                  sample.setValue(-32768);
          }
      
      }
      
      /**
       * Method to splice two sounds together with some silence between them 
       * into the current sound
       * 73
       */
        public void splice(){
        Sound sound1 = 
              new Sound(FileChooser.getMediaPath("guzdial.wav"));
        Sound sound2 = 
              new Sound(FileChooser.getMediaPath("is.wav"));
        int targetIndex = 0; //the starting place on the target
        int value = 0;
      
      //copy all of sound 1 into the current sound (target)
        for (int i = 0;
              i < sound1.getLength();
              i++, targetIndex++)
        {
            value = sound1.getSampleValueAt(i);
            this.setSampleValueAt(targetIndex, value);
        }
      //create silence between words by setting values to 0 
      for (int i = 0;
              i < (int) (this.getSamplingRate()* 0.1);
              i++, targetIndex++)
        {
            this.setSampleValueAt(targetIndex, 0);
        }
      
      //copy all of sound 2 into the current sound (target)
        for (int i = 0;
              i < sound2.getLength();
              i++, targetIndex++)
        {
            value = sound2.getSampleValueAt(i);
            this.setSampleValueAt(targetIndex, value);
        }
      }
      
      /**
       * Method to copy part of the passed sound into this sound at the 
       * given start index
       * @param source the source sound to copy from 
       * @param sourceStart the starting index to copy from in the source
       * (the copy will include this)
       * @param sourceStop the ending index (the copy won't include this)
       * @param targetStart the index to start copying into
       * 76
       */
      public void splice(Sound source,
                          int sourceStart,
                          int sourceStop,
                          int targetStart)
      {
          //loop carying from source to target
          for (int sourceIndex = sourceStart,
                  targetIndex = targetStart;
                  sourceIndex < sourceStop &&
                  targetIndex < this.getLength();
                  sourceIndex++, targetIndex++)
              this.setSampleValueAt(targetIndex, source.getSampleValueAt(sourceIndex));
      }
      
        /**
         * Method to reverse the current sound.
         * 78
         */   
      public void reverse(){
      Sound orig = new Sound(this.getFileName());
      int length = this.getLength();
      
      //loop through the samples
      for(int targetIndex = 0, sourceIndex = length - 1;
              targetIndex < length && sourceIndex > 0;
              targetIndex++, sourceIndex--)
          this.setSampleValueAt(targetIndex, orig.getSampleValueAt(sourceIndex));
      
      }
      
      
      
      
      
      
      
      
      // The methods below are methods created by my partner and I for lab
      //lab assignments. The above code is from the Introduction to Computing 
      //and Programming with Java: A Multimedia Approach, 1st edition textbook.
      
      /**
       * This method replaces all the sample values with a zero between the 
       * two points indicated by user, effectively setting that area to no 
       * sound at all 
       * @param startIndex
       * @param endIndex 
       */
     public void blackOutRange(int startIndex, int endIndex){
  
        SoundSample[] sampleArray = this.getSamples();
        SoundSample sample = null;
        
        for (int i = 0; i< sampleArray.length; i++){   
       
            sample=sampleArray[i];
            if (i >=startIndex && i<=endIndex){
            sample.setValue(0);
            }   
 
        } 
    }  
    
     /**
      *  This method clips sound samples that have values below 0.
      * This means that if a sound is below 0 it will be given the value of 0.
      * @return count
      */
     public int clipLows(){
      SoundSample[] sampleArray = this.getSamples();
      SoundSample sample = null;
      int count = 0;
      
      //loop through the sample values
      for (int i = 0; i < sampleArray.length; i++){
      
          //get the current sample
          sample = sampleArray[i];
          
          //if the value was positive (0r zero) set it to the
          //maximum positive value
          if (sample.getValue() < 0)
            sample.setValue(0);
            count += 1;
                    
        }
      return count;
      
     }
     
    /**
     * Remove sounds at every other index
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
    
    /**
     * This replaces the sound at index i with the sound previous. 
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
       
    /**
     * This method is very similar to the previous two, except 
     * that instead of setting every second sample to 0 we will 
     * just skip that, and end up with a new sound (which this 
     * method returns) that is half the length of the original.
     * @return 
     */    
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
       
    /**
     * In this method, we will create a new sound file repeat every 
     * single sample.
     * @return sound
     */ 
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
    
 // this } is the end of class Sound, put all new methods before this