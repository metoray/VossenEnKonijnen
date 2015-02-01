package mirlexpat.voskonijn.actor;
/**
 * Interface containing methods for the 'actors' of the simulation.
 * 
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
 */
import java.util.List;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;



public interface Actor {

	public boolean isAlive();
	
	public void setDead();
	
	public void act(List<Actor> newActors);

}
