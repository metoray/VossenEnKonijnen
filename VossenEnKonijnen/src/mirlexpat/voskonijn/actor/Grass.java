package mirlexpat.voskonijn.actor;

import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Grass extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.5;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The maximum amount of steps grass can live.
    private static final int MAX_AGE = 20;
    private int age;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Grass(boolean randomAge, Field field, Location location) {
    
        super(field, location);
       
	}
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    @Override
	public void act(List<Actor> newGrass)
    {
    	incrementAge();
        if(isAlive()) {
            giveBirth(newGrass);
          
          
            }
        }
    

    
    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newGrass)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Grass young = new Grass(false, field, loc);
            newGrass.add(young);
        }
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}
