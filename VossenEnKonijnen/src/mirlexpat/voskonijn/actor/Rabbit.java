package mirlexpat.voskonijn.actor;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;
import mirlexpat.voskonijn.FoxRabbit;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Rabbit extends Animal
{
	// Characteristics shared by all rabbits (class variables).

	// The age at which a rabbit can start to breed.
	private static final int BREEDING_AGE = 15;
	//The age to which a rabbit can live.
	private int MAX_AGE = 100;
	// The likelihood of a rabbit breeding.
	private static final double BREEDING_PROBABILITY = 0.14;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 12;



	private static final double SICK_PROBABILITY = 0.90;
	// A shared random number generator to control breeding.
	private static final Random rand = Randomizer.getRandom();
	// A rabbit's food level.
	private int foodLevel;



	// Individual characteristics (instance fields).

	// The rabbit's age.
	private int age;

	/**
	 * Create a new rabbit. A rabbit may be created with age
	 * zero (a new born) or with a random age.
	 * 
	 * @param randomAge If true, the rabbit will have a random age.
	 * @param field The field currently occupied.
	 * @param location The location within the field.
	 */
	public Rabbit(boolean randomAge, Field field, Location location)
	{
		super(field, location);
		age = 0;
		if(randomAge) {
			foodLevel = rand.nextInt(10);
			age = rand.nextInt(MAX_AGE);
		}
		else{
			foodLevel = 10;
		}
	}

	/**
	 * This is what the rabbit does most of the time - it runs 
	 * around. Sometimes it will breed or die of old age.
	 * @param newRabbits A list to return newly born rabbits.
	 */
	@Override
	public void act(List<Actor> newRabbits)
	{
		incrementAge();
		if(isAlive()) {
			giveBirth(newRabbits);
			// Try to move into a free location.
			eatGrass();
			giveVirus();
			dieOfInfection();
			Location newLocation = getField().freeAdjacentLocation(getLocation());

			if(newLocation != null) {
				setLocation(newLocation);
			}
			else {
				// Overcrowding.
				setDead();
			}
			dieWhenHungry();
		}
	}

	private Location findFood()
	{
		Field currentField = getField();
		List<Location> adjacent = currentField.adjacentLocations(getLocation());
		for (int i=0; i < 10; i++) {
			Location targetLocation = getRandomLocation(adjacent);
			Object object = currentField.getObjectAt(targetLocation);

			if(object instanceof Grass) {
				Grass grass = (Grass) object;
				if(grass.isAlive()) {
					grass.setDead();
					foodLevel+=10;
					return targetLocation;
				}
			}
		}
		return null;
	}

	private void eatGrass(){
		if(foodLevel<30&&getField().getGrass(getLocation())>0){
			getField().eatGrass(getLocation());
			foodLevel+=10;
		}
	}

	private static Location getRandomLocation(List<Location> location){
		return location.get(rand.nextInt(location.size()));
	}

	/**
	 * Increase the age.
	 * This could result in the rabbit's death.
	 */
	private void incrementAge()
	{
		age++;
		if(age > MAX_AGE) {
			setDead();
		//	System.out.println("hi");
		}
	}

	/**
	 * Check whether or not this rabbit is to give birth at this step.
	 * New births will be made into free adjacent locations.
	 * @param newRabbits A list to return newly born rabbits.
	 */
	private void giveBirth(List<Actor> newRabbits)
	{
		// New rabbits are born into adjacent locations.
		// Get a list of adjacent free locations.
		Field field = getField();
		List<Location> free = field.getFreeAdjacentLocations(getLocation());
		int births = breed();
		for(int b = 0; b < births && free.size() > 0; b++) {
			Location loc = free.remove(0);
			Rabbit young = new Rabbit(false, field, loc);
			newRabbits.add(young);
		}
	}

	private void dieWhenHungry() {
		if(foodLevel <= 0) {
			setDead();
		}
		foodLevel--;
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

	/**
	 * A rabbit can breed if it has reached the breeding age.
	 * @return true if the rabbit can breed, false otherwise.
	 */
	private boolean canBreed()
	{
		return age >= BREEDING_AGE;
	}

	/**
	 * @return true if infected
	 * @return false if not infected
	 */
	public boolean isInfected()
	{
		int random = (rand.nextInt(100) + 1)/100;
		if (random >= SICK_PROBABILITY)
		{
			setInfected(true);
			return true;
		}
		return false;
	}

	private boolean giveVirus()
	{
		Field currentField = getField();
		List<Location> adjacent = currentField.adjacentLocations(getLocation());
		for (int i=0; i < 10; i++) {
			Location targetLocation = getRandomLocation(adjacent);
			Object object = currentField.getObjectAt(targetLocation);
			if(object instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) object;
				if(rabbit.getInfected()) {
				isInfected();
					
				}
			}
		}
		return false;
	}
	
	public void setInfected(boolean infected)
	{
		this.infected = infected;
	}

	private boolean getInfected()
	{
		return infected;
	}

	private void dieOfInfection()
	{
		if(isInfected()) {
			MAX_AGE = 15;
			System.out.println("I'm dying of infection.");
		}
	}
	


}
