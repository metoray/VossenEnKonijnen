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

	private int sick;



	// Individual characteristics (instance fields).

	// The rabbit's age.

	private boolean vulnerable;

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
		super(randomAge, field, location);
		vulnerable = rand.nextDouble() < getEntry().getDouble("vulnerable chance");
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
			Location newLocation = getField().freeAdjacentLocation(getLocation());

			if(newLocation != null) {
				setLocation(newLocation);
			}
			else {
				// Overcrowding.
				setDead();
			}
			maybeGetInfected();
			dieOfInfection();
			incrementHunger();
		}
	}

	private void maybeGetInfected() {
		if(vulnerable&&rand.nextDouble()<0.001){
			infect();
		}

	}

	private void eatGrass(){
		if(getFoodLevel()<getFoodValue()*3&&getField().getGrass(getLocation())>0){
			getField().eatGrass(getLocation());
			feed();
		}
	}

	private Location getRandomLocation(List<Location> location){
		return location.get(rand.nextInt(location.size()));
	}

	protected Actor getNew(Field field, Location loc){
		return new Rabbit(false, field, loc);
	}



	/**
	 * @return true if infected
	 * @return false if not infected
	 */
	public void infect()
	{
		if(sick==0){
			sick = 1;
		}
	}

	private boolean giveVirus()
	{
		if(this.isInfected()) {
			Field currentField = getField();
			List<Location> adjacent = currentField.adjacentLocations(getLocation());
			Location targetLocation = getRandomLocation(adjacent);
			Object object = currentField.getObjectAt(targetLocation);
			if(object instanceof Rabbit) {
				Rabbit rabbit = (Rabbit) object;
				rabbit.infect();


			}
		}
		return false;
	}

	public boolean isInfected() {
		return sick > 0;
	}

	private void dieOfInfection()
	{	

		if(sick > 5) {
			setDead();
		}
		if(sick > 0) {
			sick++;

		}

	}
	
	protected void feed(){
		foodLevel+=getFoodValue();
	}

}




