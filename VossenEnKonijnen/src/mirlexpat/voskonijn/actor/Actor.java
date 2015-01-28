package mirlexpat.voskonijn.actor;

import java.util.List;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;



public interface Actor {
	
	// Whether the animal is alive or not.
	public boolean alive = true;
	// The animal's field.
	public Field field = null;
	// The animal's position in the field.
	public Location location = null;

	
	abstract public boolean isAlive();
	
	public abstract void setDead();
	
	public abstract void act(List<Actor> newActors);

}
