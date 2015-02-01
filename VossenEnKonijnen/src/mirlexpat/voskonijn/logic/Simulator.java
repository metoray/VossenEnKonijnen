package mirlexpat.voskonijn.logic;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingUtilities;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
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
    	newField(new FieldSettings());
        
    }
    
	public void newField(FieldSettings settings) {
		stopRunning();
		field = new Field(settings);
		field.reset();
		notifyViews();
		
	}
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
    	stopRunning();
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
    
    /** 
     * Runs the simulation and updates all views accordingly.
     */
	@Override
	public void run() {
		while(isRunning()){
			field.step();
			decrease();
			notifyViews();
		}
		
	}
	
	/**
	 * Lets the simulator take a number of steps based on what the user determined.
	 * @param steps the simulator will take
	 */
	public void simulate(int steps){
		stepsToRun.set(steps);
		runInfinite.set(false);
		startThread();
	}
	
	/**
	 * Decrements the steps to run by 1.
	 */
	private void decrease(){
		if(stepsToRun.get()>0){
			stepsToRun.getAndDecrement();
		}
	}
	
	/**
	 * Starts the thread that contains the simulator.
	 */
	public void startRunning(){
		runInfinite.set(true);
		startThread();
	}
	
	/**
	 * Stops the thread that runs the simulator.
	 */
	public void stopRunning(){
		runInfinite.set(false);
		stepsToRun.set(0);
		waitToEnd();
	}
	
	/**
	 * Allows the thread to 'die' first when stopped.
	 */
	public void waitToEnd(){
		if(thread!=null){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Checks to see if the thread for the simulation is running at the moment.
	 * @return true if running, false if not
	 */
	public boolean isRunning(){
		return runInfinite.get() || stepsToRun.get() > 0;
	}
	
	/**
	 * Starts the simulationthread.
	 */
	public void startThread(){
		if(thread==null||!thread.isAlive()){
			thread = new Thread(this);
	        thread.setDaemon(true);
	        thread.start();
		}
	}
}
