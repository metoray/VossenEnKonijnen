package mirlexpat.voskonijn.actor;

import java.util.List;
import java.util.Map;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public abstract class Animal implements Actor
{
	// Whether the animal is alive or not.
	private boolean alive;
	// The animal's field.
	private Field field;
	// The animal's position in the field.
	private Location location;
	// The random number generator for this animal
	protected Random rand;
	// The age of this animal
	protected int age;
    // The animal's food level, which is increased by eating rabbits.
    protected int foodLevel;

	/**
	 * Create a new animal at location in field.
	 * 
	 * @param randomAge gives the animal a random age if true.
	 * @param field The field currently occupied.
	 * @param location The location within the field.
	 */
	public Animal(boolean randomAge, Field field, Location location)
	{
		this.field = field;
		this.rand = field.getRandomizer().getRandom();
		if(randomAge){
			int maxAge = getMaxAge();
			int foodValue = getFoodValue();
			this.age = maxAge>0?rand.nextInt(maxAge):0;
			this.foodLevel = foodValue>0?rand.nextInt(foodValue):0;
		}
		else{
			age = 0;
			foodLevel = getFoodValue();
		}
		alive = true;
		setLocation(location);
	}

	/**
	 * Make this animal act - that is: make it do
	 * whatever it wants/needs to do.
	 * @param newAnimals A list to receive newly born animals.
	 */
	abstract public void act(List<Actor> newActors);

	/**
	 * Check whether the animal is alive or not.
	 * @return true if the animal is still alive.
	 */
	public boolean isAlive()
	{
		return alive;
	}

	/**
	 * Indicate that the animal is no longer alive.
	 * It is removed from the field.
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
	 * Return the animal's location.
	 * @return The animal's location.
	 */
	protected Location getLocation()
	{
		return location;
	}

	/**
	 * Place the animal at the new location in the given field.
	 * @param newLocation The animal's new location.
	 */
	protected void setLocation(Location newLocation)
	{
		if(location != null) {
			field.clear(location);
		}
		location = newLocation;
		field.place(this, newLocation);
	}

	/**
	 * Return the animal's field.
	 * @return The animal's field.
	 */
	protected Field getField()
	{
		return field;
	}
	
	/**
	 * return this animal's entry in the settings system.
	 * @return The animal's entry.
	 */
	protected AnimalEntry getEntry(){
		Map<Class<Actor>,AnimalEntry> map = getField().getSettings().getSpawnList();
		if(map.containsKey(this.getClass())){
			return map.get(this.getClass());
		}
		else{
			return new AnimalEntry(0, null);
		}
	}
	
	/**
	 * return this animal's maximum age according to its entry
	 * @return maxAge
	 */
	protected int getMaxAge(){
		return getEntry().getInteger("max age");
	}
	
	/**
	 * return this animal's maximum litter size according to its entry
	 * @return maxLitterSize
	 */
	protected int getMaxLitterSize(){
		return getEntry().getInteger("max litter size");
	}
	
	/**
	 * return this animal's breeding chance according to its entry
	 * @return breedChance
	 */
	protected double getBreedChance(){
		return getEntry().getDouble("breeding chance");
	}
	
	/**
	 * return this animal's breeding age according to its entry
	 * @return breedAge
	 */
	protected int getBreedAge(){
		return getEntry().getInteger("breeding age");
	}
	
	/**
	 * abstract method so animals can instantiate their own kind in giveBirth()
	 * @return new actor
	 */
	protected abstract Actor getNew(Field field, Location loc);
	
	/**
	 * Increase the age.
	 * This could result in the animal's death.
	 */
	protected void incrementAge()
	{
		if(!isAlive()) return;
		age++;
		if(age > getMaxAge()) {
			setDead();
		}
	}
	
    /**
     * Check whether or not this animal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newAnimals A list to return newly born animals.
     */
    protected void giveBirth(List<Actor> newAnimals)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Actor young = getNew(getField(), loc);
            newAnimals.add(young);
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedChance()) {
        	int maxLitterSize = getMaxLitterSize();
            births = maxLitterSize>0?(rand.nextInt(maxLitterSize) + 1):0;
        }
        return births;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return canBreed
     */
    protected boolean canBreed()
    {
        return age >= getBreedAge();
    }
    
    /**
     * return the level of food this animal gains from eating.
     * Value comes from animal's settings entry.
     * @return foodValue
     */
    protected int getFoodValue(){
    	return getEntry().getInteger("food value");
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    protected void feed(){
    	foodLevel = getFoodValue();
    }
    
    public int getFoodLevel(){
    	return foodLevel;
    }
	

}
