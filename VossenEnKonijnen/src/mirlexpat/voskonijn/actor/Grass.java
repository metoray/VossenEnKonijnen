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

		super(randomAge,field, location);

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


	protected Actor getNew(Field field, Location loc){
		return new Grass(false, field, loc);
	}

}
