package mirlexpat.voskonijn.logic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator extends AbstractModel implements Runnable
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // The current state of the field.
    private Field field;
    
	private AtomicInteger stepsToRun;
	private AtomicBoolean runInfinite;
	
	private Thread thread;
    
    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    
    
    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
    	stepsToRun = new AtomicInteger(0);
    	runInfinite = new AtomicBoolean(false);
    	newField(depth, width);
        
    }
    
    public void newField(int depth, int width){
    	stopRunning();
    	if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

    	FieldSettings settings = new FieldSettings();
    	settings.getWidthSetting().setValue(width);
    	settings.getDepthSetting().setValue(depth);
        field = settings.generateField();
        Randomizer.reset();
    	field.reset();
    	notifyViews();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
    	stopRunning();
    	Randomizer.reset();
    	field.reset();
    	notifyViews();
    }
    
    /**
     * returns the current field that is being simulated
     * @return field
     */
    public Field getField(){
    	return field;
    }
    
	@Override
	public void run() {
		while(isRunning()){
			field.step();
			decrease();
			notifyViews();
		}
		
	}
	
	public void simulate(int steps){
		stepsToRun.set(steps);
		runInfinite.set(false);
		startThread();
	}
	
	private void decrease(){
		if(stepsToRun.get()>0){
			stepsToRun.getAndDecrement();
		}
	}
	
	public void startRunning(){
		runInfinite.set(true);
		startThread();
	}
	
	public void stopRunning(){
		runInfinite.set(false);
		stepsToRun.set(0);
		if(thread!=null){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isRunning(){
		return runInfinite.get() || stepsToRun.get() > 0;
	}
	
	public void startThread(){
		if(thread==null||!thread.isAlive()){
			thread = new Thread(this);
	        thread.setDaemon(true);
	        thread.start();
		}
	}
}
