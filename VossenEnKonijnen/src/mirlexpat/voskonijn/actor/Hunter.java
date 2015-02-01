package mirlexpat.voskonijn.actor;

import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;

/**
 * A simple model of a hunter.
 * Hunters kill, they don't breed and they don't die.
 * 
 * @author Lex Hermans
 * @version 1.0
 */
public class Hunter implements Actor
{

	// The animal's field.
	private Field field;
	// The animal's position in the field.
	private Location location;
	// Whether the animal is alive or not.
	protected boolean alive;
	// A shared random number generator.
	private Random rand;

	/**
	 * Create a hunter.
	 * 
	 * @param randomAge 
	 * @param field The field currently occupied.
	 * @param location The location within the field.
	 */
	public Hunter(boolean randomAge, Field field, Location location)
	{
		alive = true;
		this.field = field;
		setLocation(location);
		this.rand = field.getRandomizer().getRandom();
	}

	/**
	 * Makes the hunter do what it's got to do.
	 * @param newHunters a list of new actors, unused for hunters
	 */
	public void act(List<Actor> newHunters)
	{
		// incrementAge();
		if(isAlive()) { 

			// Move towards a source of prey if found
			Location newLocation = findTarget();
			if(newLocation == null) {
				// No prey found - try to move to a free location.
				newLocation = getField().freeAdjacentLocation(getLocation());
			}
			// See if it was possible to move.
			if(newLocation != null) {
				setLocation(newLocation);
			}
			else {
				// Overcrowding.
				setDead();
			}

		}

	}

	/**
	 * Tries to find prey
	 * @return location of first prey found
	 */
	private Location findTarget()
	{
		Field currentField = getField();
		List<Location> adjacent = currentField.adjacentLocations(getLocation());
		for (int i=0; i < 10; i++) {
			Location targetLocation = getRandomLocation(adjacent);
			Object object = currentField.getObjectAt(targetLocation);

			if(object instanceof Animal) {
				Animal prey = (Animal) object;
				if(prey.isAlive()) {
					prey.setDead();
					return targetLocation;
				}
			}
		}
		return null;
	}

	/**
	 * returns random entry from location list
	 * @param locations a list of locations
	 * @return a random location from the list
	 */
	private Location getRandomLocation(List<Location> locations){
		return locations.get(rand.nextInt(locations.size()));
	}
	/**
	 * Returns the field a hunter is in.
	 * @return field
	 */
	public Field getField()
	{
		return field;
	}
	/**
	 * Returns whether or not a hunter is alive or not.
	 * @return true when alive
	 * @return false when dead
	 */
	public boolean isAlive()
	{
		return alive;
	}
	/**
	 * Kills a hunter.
	 */
	public void setDead()
	{
		alive = false;
		if(location != null) {
			field.clear(location);
			location = null;
			field = null;
		}
	}
	/**
	 * Returns the location of a hunter.
	 * @return location
	 */
	public Location getLocation()
	{
		return location;
	}
	/**
	 * Sets the location of a hunter.
	 * @param newLocation
	 */
	protected void setLocation(Location newLocation)
	{
		if(this.location != null) {
			field.clear(location);
		}
		location = newLocation;
		field.place(this, newLocation);
	}


}

