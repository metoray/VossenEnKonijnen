package mirlexpat.voskonijn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.actor.Actor;
import mirlexpat.voskonijn.actor.Fox;
import mirlexpat.voskonijn.actor.Hunter;
import mirlexpat.voskonijn.actor.KomodoDragon;
import mirlexpat.voskonijn.actor.Rabbit;
import mirlexpat.voskonijn.view.IView;
import mirlexpat.voskonijn.view.SimulatorView;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Field
{
	
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.15;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.2;  
    private static final double KOMODOVARAAN_CREATION_PROBABILITY = 0.001;  
    private static final double HUNTER_CREATION_PROBABILITY = 0.010;
	
    // A random number generator for providing random locations.
    private static final Random rand = Randomizer.getRandom();
    // List of animals in the field.
    private List<Actor> animals;
    
    // The current step of the simulation.
    private int step;
    // The depth and width of the field.
    private int depth, width;
    // Storage for the animals.
    private Object[][] field;
    private ArrayList<IView> views;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
        animals = new ArrayList<Actor>();
        this.views = new ArrayList<>();
    }
    
    /**
     * Empty the field.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        field[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    public void place(Object animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the field.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col)
    {
        return field[row][col];
    }
    
    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location)
    {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }
    
    /**
     * Get a shuffled list of the free adjacent locations.
     * @param location Get locations adjacent to this.
     * @return A list of free adjacent locations.
     */
    public List<Location> getFreeAdjacentLocations(Location location)
    {
        List<Location> free = new LinkedList<Location>();
        List<Location> adjacent = adjacentLocations(location);
        for(Location next : adjacent) {
            if(getObjectAt(next) == null) {
                free.add(next);
            }
        }
        return free;
    }
    
    /**
     * Try to find a free location that is adjacent to the
     * given location. If there is none, return null.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location freeAdjacentLocation(Location location)
    {
        // The available free ones.
        List<Location> free = getFreeAdjacentLocations(location);
        if(free.size() > 0) {
            return free.get(0);
        }
        else {
            return null;
        }
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<Location>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Simulate the field for one step
     */
    public void step(){
    	step++;
        // Provide space for newborn animals.
        List<Actor> newAnimals = new ArrayList<Actor>(); 
        // Let all rabbits act.
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            actor.act(newAnimals);
            if(! actor.isAlive()) {
                it.remove();
            }
        }
        
        
 
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        
        notifyViews();
    }

	private void notifyViews() {
		for(IView view: views){
			view.update();
		}
		
	}
	
	public void addView(IView view){
		views.add(view);
	}

	public synchronized void reset() {
    	step = 0;
        animals.clear();
        field = new Object[depth][width];
        this.populate();
	}
	
	
	/**
     * Randomly populate the field with foxes, rabbits and hunters.
     */
    void populate()
    {
        Random rand = Randomizer.getRandom();
        this.clear();
        for(int row = 0; row < this.getDepth(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Fox fox = new Fox(true, this, location);
                    animals.add(fox);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Rabbit rabbit = new Rabbit(true, this, location);
                    animals.add(rabbit);
                }else if(rand.nextDouble() <= HUNTER_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Hunter hunter = new Hunter(true, this, location);
                    animals.add(hunter);
                }else if(rand.nextDouble() <= KOMODOVARAAN_CREATION_PROBABILITY){
                	Location location = new Location(row, col);
                	KomodoDragon komodovaraan = new KomodoDragon(true, this, location);
                	animals.add(komodovaraan);
                }
                // else leave the location empty.
            }
        }
    }
    
    public int getStep(){
    	return step;
    }
    
    public void add(Actor actor){
    	animals.add(actor);
    }
    
}
