package mirlexpat.voskonijn;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import mirlexpat.voskonijn.actor.Fox;
import mirlexpat.voskonijn.actor.Hunter;
import mirlexpat.voskonijn.actor.KomodoDragon;
import mirlexpat.voskonijn.actor.Rabbit;
import mirlexpat.voskonijn.controller.MenuController;
import mirlexpat.voskonijn.controller.FieldController;
import mirlexpat.voskonijn.view.IView;
import mirlexpat.voskonijn.view.SimulatorView;

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
    
	private int stepsToRun = 0;
	private boolean runInfinite = false;
	
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
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        field = new Field(depth, width);
        field.reset();
        notifyViews();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
    	field.reset();
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
	
	public synchronized void simulate(int steps){
		stepsToRun = steps;
		runInfinite = false;
		startThread();
	}
	
	private synchronized void decrease(){
		if(stepsToRun>0){
			stepsToRun--;
		}
	}
	
	public synchronized void startRunning(){
		runInfinite = true;
		startThread();
	}
	
	public synchronized void stopRunning(){
		runInfinite = false;
		stepsToRun = 0;
	}
	
	public synchronized boolean isRunning(){
		return runInfinite || stepsToRun > 0;
	}
	
	public void startThread(){
		if(thread==null||!thread.isAlive()){
			thread = new Thread(this);
	        thread.setDaemon(true);
	        thread.start();
		}
	}
}
