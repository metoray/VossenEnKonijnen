package mirlexpat.voskonijn.logic;

import java.util.Random;

/**
 * Provide control over the randomization of the simulation.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Randomizer
{
    // The seed for control of randomization.
    private int seed;
    // A shared Random object, if required.
    private Random rand;
    // Determine whether a shared random generator is to be provided.
    private static final boolean useShared = true;

    /**
     * Constructor for objects of class Randomizer
     */
    public Randomizer()
    {
    	this(1111);
    }
    
    public Randomizer(int seed){
    	this.seed = seed;
    	this.rand = new Random(seed);
    }

    /**
     * Provide a random generator.
     * @return A random object.
     */
    public Random getRandom()
    {
        if(useShared) {
            return rand;
        }
        else {
            return new Random();
        }
    }
    
    /**
     * Reset the randomization.
     * This will have no effect if randomization is not through
     * a shared Random generator.
     */
    public void reset()
    {
        if(useShared) {
            rand.setSeed(seed);
        }
    }
}
