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
	protected Random rand;
	protected int age;

	protected boolean infected;
	/**
	 * Create a new animal at location in field.
	 * 
	 * @param field The field currently occupied.
	 * @param location The location within the field.
	 */
	public Animal(boolean randomAge, Field field, Location location)
	{
		this.field = field;
		this.rand = field.getRandomizer().getRandom();
		if(randomAge){
			int maxAge = getMaxAge();
			this.age = maxAge>0?rand.nextInt(maxAge):0;
		}
		else{
			age = 0;
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

	protected void isActive()
	{
		isAlive();
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
	
	protected AnimalEntry getEntry(){
		Map<Class<Actor>,AnimalEntry> map = getField().getSettings().getSpawnList();
		if(map.containsKey(this.getClass())){
			return map.get(this.getClass());
		}
		else{
			return new AnimalEntry(0, null, 0, 0, 0, 0, 0){

				@Override
				public Actor getActor(Field field, Location location) {
					return null;
				}
				
			};
		}
	}
	
	protected int getMaxAge(){
		return getEntry().getMaxAge();
	}
	
	/**
	 * Increase the age.
	 * This could result in the rabbit's death.
	 */
	protected void incrementAge()
	{
		if(!isAlive()) return;
		age++;
		if(age > getMaxAge()) {
			setDead();
		//	System.out.println("hi");
		}
	}

	

}
