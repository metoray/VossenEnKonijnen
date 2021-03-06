package mirlexpat.voskonijn.actor;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;
/**
 * Contains all relevant information for the Komododragons.
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
 */
public class KomodoDragon extends Animal
{
    
    public KomodoDragon(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }
    
    /**
     * Do stuff
     * @param newKomodoDragons a list of new komodo dragons
     */
    public void act(List<Actor> newKomodoDragons)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newKomodoDragons);            
            Location newLocation = findFood();
            if(newLocation == null) { 
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                setDead();
            }
        }
    }
    
    /**
     * Finds food
     * @return first location of food
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object obj = field.getObjectAt(where);
            if(obj instanceof Rabbit || obj instanceof Fox) {
            	Animal animal = (Animal) obj;
                if(animal.isAlive()) { 
                    animal.setDead();
                    feed();
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns a new KomodoDragon for giveBirth()
     * @param field The field the new actor should spawn in.
     * @param loc The location of the new komododragon.
     * @return new Actor
     */
    protected Actor getNew(Field field, Location loc){
    	return new KomodoDragon(false, field, loc);
    }
}