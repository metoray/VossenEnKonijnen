package mirlexpat.voskonijn.actor;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Location;
import mirlexpat.voskonijn.logic.Randomizer;

public class KomodoDragon extends Animal
{
    private static final int BREEDING_AGE = 400;

    private static final double BREEDING_PROBABILITY = 0.08;

    private static final int MAX_LITTER_SIZE = 2;

    private static final int FOOD_VALUE = 45;
    
    private int age;

    private int foodLevel;
    
    public KomodoDragon(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
        if(randomAge) {
            foodLevel = rand.nextInt(FOOD_VALUE);
        }
        else {
            foodLevel = FOOD_VALUE;
        }
    }
    public void act(List<Actor> newKomodoDragon)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newKomodoDragon);            
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
    
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevel = FOOD_VALUE;
                    return where;
                }
            }
            if(animal instanceof Fox){
            	Fox fox = (Fox) animal;
            	if (fox.isAlive()){
            		fox.setDead();
            		foodLevel = FOOD_VALUE;
            		return where;
            	}
            }
        }
        return null;
    }
    
    private void giveBirth(List<Actor> newKomodovaraan)
    {
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            KomodoDragon young = new KomodoDragon(false, field, loc);
            newKomodovaraan.add(young);
        }
    }
    
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }
    
    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}