package mirlexpat.voskonijn.actor;

import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;

/**
 * A simple model of a hunter.
 * Hunters kill, and die.
 * 
 * @author ...
 * @version 2015.01.18
 */
public class Hunter extends Human
{

    // A shared random number generator to control breeding.
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
        super(field, location);
        this.rand = field.getRandomizer().getRandom();
    }
    
    
    
    
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
    	
    		
    


    
    private Location findTarget()
    {
    	Field currentField = getField();
    	List<Location> adjacent = currentField.adjacentLocations(getLocation());
    	for (int i=0; i < 10; i++) {
    		Location targetLocation = getRandomLocation(adjacent);
    		Object object = currentField.getObjectAt(targetLocation);
    		
    		if(object instanceof Animal && !(object instanceof Grass)) {
    			Animal prey = (Animal) object;
    			if(prey.isAlive()) {
    				prey.setDead();
    				return targetLocation;
    			}
    		}
    	}
    	return null;
	}
    
    
    
    
    
    private Location getRandomLocation(List<Location> location){
    	return location.get(rand.nextInt(location.size()));
    }
    

		
	
}

