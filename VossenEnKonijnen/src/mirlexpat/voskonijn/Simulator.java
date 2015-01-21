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

import mirlexpat.voskonijn.controller.MenuController;
import mirlexpat.voskonijn.controller.SimulatorController;
import mirlexpat.voskonijn.view.SimulatorView;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Simulator extends JFrame
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // The current state of the field.
    private Field field;
    // A graphical view of the simulation.
    private SimulatorView view;
    
    public static void main(String[] args){
    	Simulator sim = new Simulator();
    }
    
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

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Hunter.class, Color.red);
        view.setColor(KomodoDragon.class, Color.green);
        field = new Field(depth, width, view);
        
        setupWindow();
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }
    
    public void runShortSimulation()
    {
        simulate(250);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox and rabbit.
     */
    public void simulateOneStep()
    {

        field.step();

        view.showStatus(field);
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
    	field.reset();
    	field.populate();
        
        // Show the starting state in the view.
        view.showStatus(field);
    }
    
    public void setupWindow(){
    	setTitle("Fox and Rabbit Simulation");
    	setLocation(100, 50);
    	Container contents = getContentPane();
        
        setJMenuBar(new MenuController());
        
        contents.add(new JScrollPane(view),BorderLayout.CENTER);
        contents.add(new JLabel("V0.0.0"),BorderLayout.SOUTH);
        contents.add(new SimulatorController(field),BorderLayout.WEST);
        
        pack();
        setVisible(true);
    }
    
}
