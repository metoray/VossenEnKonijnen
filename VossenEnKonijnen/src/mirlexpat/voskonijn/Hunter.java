package mirlexpat.voskonijn;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.Field;
import mirlexpat.voskonijn.Location;

/**
 * A simple model of a hunter.
 * Hunters kill, and die.
 * 
 * @author ...
 * @version 2015.01.18
 */
public class Hunter extends Human
{
    // Characteristics shared by all foxes (class variables).
    
    // The amount of kills a hunter has.
	private int kills = 0;
	// The maximum amount of kills a hunter is allowed to get.
	private final int MAX_KILLS = 10;
	// The maximum amount of steps a hunter is allowed to live.
	private static final int MAX_AGE = 80;
	
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    

    


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
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);            
        }
        else {
            age = 0;
        }
        
        kills = rand.nextInt(MAX_KILLS);
    }
    
    
    
	public void act(List<Actor> newHunters)
    {
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
    	for (int i=0; i < MAX_KILLS; i++) {
    		Location targetLocation = getRandomLocation(adjacent);
    		Object object = currentField.getObjectAt(targetLocation);
    		
    		if(object instanceof Animal) {
    			Animal prey = (Animal) object;
    			if(prey.isAlive()) {
    				prey.setDead();
    				kills++;
    				return targetLocation;
    			}
    		}
    	}
    	return null;
	}
    
    private static Location getRandomLocation(List<Location> location){
    	int random = (int) (Math.random()*(location.size() -0));
    	return location.get(random);
    	}
    

		
	
}

