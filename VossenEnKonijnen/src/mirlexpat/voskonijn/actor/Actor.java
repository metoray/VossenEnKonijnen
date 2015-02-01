package mirlexpat.voskonijn.actor;

import java.util.List;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;



public interface Actor {

	public boolean isAlive();
	
	public void setDead();
	
	public void act(List<Actor> newActors);

}
