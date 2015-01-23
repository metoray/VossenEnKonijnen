package mirlexpat.voskonijn.actor;

import java.util.List;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;

public abstract class Human implements Actor 
{
	// The age of a Human
	protected int age;
	// Whether the animal is alive or not.
	protected boolean alive;
	// The animal's field.
	private Field field;
	// The animal's position in the field.
	private Location location;
	

	public Human(Field fieldInput, Location locationInput)
	{
		alive = true;
		field = fieldInput;
		setLocation(locationInput);
		age = 0;
	}
	
	public abstract void act(List<Actor> newHumans);
	/**
	* Check whether the human is alive or not.
	* @return true if the human is still alive.
	*/
	@Override
	public boolean isAlive()
		{
		return alive;
		}
	
	public void setDead()
		{
		alive = false;
		if(location != null) {
			field.clear(location);
			location = null;
			field = null;
			}
		}
	public Location getLocation()
		{
		return location;
		}
	
	public Field getField()
		{
		return field;
		}
	
	protected void setLocation(Location newLocation)
		{
		if(this.location != null) {
			field.clear(location);
		}
		location = newLocation;
		field.place(this, newLocation);
		}
	}
