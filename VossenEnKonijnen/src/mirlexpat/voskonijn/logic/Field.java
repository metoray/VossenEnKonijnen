package mirlexpat.voskonijn.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mirlexpat.voskonijn.actor.*;
import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.view.AbstractView;

/**
 * Represent a rectangular grid of field positions.
 * Each position is able to store a single animal.
 * 
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
 */
public class Field
{
	
	private double grassGrowChance;
	
    // A random number generator for providing random locations.
    private Random rand;
    // List of animals in the field.
    private List<Actor> animals;
    
    // The current step of the simulation.
    private int step;
    // The depth and width of the field.
    private final int depth, width;
    // The field settings
    private FieldSettings settings;
    // Storage for the animals.
    private Object[][] field;
    private int[][] grass;
    private ArrayList<AbstractView> views;
    // A statistics object computing and storing simulation information
    private FieldStats stats;
    private Randomizer random;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(FieldSettings settings)
    {
    	this.random = new Randomizer(settings.getRandomSeed());
    	this.settings = settings;
        this.depth = settings.getDepth();
        this.width = settings.getWidth();
        this.rand = random.getRandom();
        this.grassGrowChance = settings.getGrassGrowthChance();
        field = new Object[depth][width];
        grass = new int[depth][width];
        animals = new ArrayList<Actor>();
        this.views = new ArrayList<>();
    	stats = new FieldStats();
    }
    
    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Return the FieldStats object for this field
     * @return stats
     */
    public FieldStats getStats(){
    	return stats;
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
    	stats.reset();
        updateGrass();
        // Provide space for newborn animals.
        List<Actor> newAnimals = new ArrayList<Actor>(); 
        // Let all rabbits act.
        for(Iterator<Actor> it = animals.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            stats.incrementCount(actor.getClass());
            actor.act(newAnimals);
            if(! actor.isAlive()) {
                it.remove();
            }
        }
        
        
 
        // Add the newly born foxes and rabbits to the main lists.
        animals.addAll(newAnimals);
        
        stats.countFinished();
        
        
        notifyViews();
    }

	private void notifyViews() {
		for(AbstractView view: views){
			view.update();
		}
		
	}
	
	public void addView(AbstractView view){
		views.add(view);
	}

	public synchronized void reset() {
		random.reset();
    	step = 0;
        animals.clear();
        field = new Object[depth][width];
        grass = new int[depth][width];
        this.populate();
        this.populateGrass();
	}
	
	
	/**
     * Randomly populate the field with foxes, rabbits and hunters.
     */
    void populate()
    {
        this.clear();
        stats.reset();
        for(int row = 0; row < this.getDepth(); row++) {
            for(int col = 0; col < this.getWidth(); col++) {
            	Actor actor = null;
            	Location location = new Location(row, col);
                for(AnimalEntry entry: settings.getSpawnList().values()){
                	actor = entry.trySpawn(this, location, rand);
                	if(actor!=null){
                		break;
                	}
                }
                // else leave the location empty.
                
                if(actor!=null){
                	animals.add(actor);
                	stats.incrementCount(actor.getClass());
                }
            }
        }
        stats.countFinished();
    }
    /**
     * Returns the current simulator step.
     * @return step
     */
    public int getStep(){
    	return step;
    }
    /**
     * Adds an actor to the list of 'animals' to be spawned.
     * @param actor
     */
    public void add(Actor actor){
    	animals.add(actor);
    }
    /**
     * Returns the fieldsettings.
     * @return settings
     */
    public FieldSettings getSettings(){
    	return settings;
    }
    /**
     * Populaties the field with grass.
     */
    public void populateGrass(){
    	for(int col=0; col<grass.length; col++){
    		for(int row=0; row<grass[col].length; row++){
    			grass[col][row] = rand.nextInt(4);
    		}
    	}
    }
    /**
     * Returns the location where grass is located.
     * @param loc
     * @return Location where grass is located
     */
    public int getGrass(Location loc){
    	return grass[loc.getRow()][loc.getCol()];
    }
    /**
     * Returns grass.
     * @param col
     * @param row
     * @return Location of grass
     */
	public int getGrass(int col, int row) {
		return grass[col][row];
	}
	/**
	 * Method that allows grass to be eaten and thus to die.
	 * @param col
	 * @param row
	 */
	public void eatGrass(int col, int row){
		if(grass[col][row]>0)grass[col][row]--;
	}
	/**
	 * Method that allows grass to be eaten and thus to die.
	 * @param loc
	 */
	public void eatGrass(Location loc){
		this.eatGrass(loc.getRow(), loc.getCol());
	}
	/**
	 * Updates the location of grass in the simulator.
	 */
	public void updateGrass(){
		for(int col=0; col<grass.length; col++){
    		for(int row=0; row<grass[col].length; row++){
    			int level = grass[col][row];
    			if((level<3)&&(level>0||grassAdjacent(col, row))&&rand.nextDouble()<grassGrowChance){
    				grass[col][row]++;
    			}
    		}
    	}
	}
	/**
	 * Returns whether or not there is grass in an adjacent location.
	 * @param col
	 * @param row
	 * @return true if grass is found in an adjacent location
	 * @return false if no grass is found in an adjacent location
	 */
	public boolean grassAdjacent(int col, int row){
		for(int coloffset=-1; coloffset<2; coloffset++){
			for(int rowoffset=-1; rowoffset<2; rowoffset++){
				int newCol = col+coloffset;
				int newRow = row+rowoffset;
				if(newCol>0&&newRow>0&&newCol<grass.length&&newRow<grass[newCol].length){
					if(getGrass(newCol,newRow) > 0) return true; 
				}
			}
		}
		return false;
	}
	/**
	 * Randomizer used in this class.
	 * @return random value
	 */
	public Randomizer getRandomizer(){
		return random;
	}
    
}
