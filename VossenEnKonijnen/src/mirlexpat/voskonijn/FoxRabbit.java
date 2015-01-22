package mirlexpat.voskonijn;

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
import mirlexpat.voskonijn.view.SimulatorView;

public class FoxRabbit extends JFrame {
	
	// A graphical view of the simulation.
    private SimulatorView view;
    private Simulator sim;
	
	public static void main(String[] args){
    	new FoxRabbit();
    }
	
	public FoxRabbit(){
		sim = new Simulator();
		
        view = new SimulatorView(sim);
        view.setColor(Rabbit.class, Color.orange);
        view.setColor(Fox.class, Color.blue);
        view.setColor(Hunter.class, Color.red);
        view.setColor(KomodoDragon.class, new Color(0,191,0));
        
		setupWindow();
        
        sim.addView(view);
	}
	
	public void setupWindow(){
    	setTitle("Fox and Rabbit Simulation");
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setLocation(100, 50);
    	Container contents = getContentPane();
        
        setJMenuBar(new MenuController(sim));
        
        contents.add(new JScrollPane(view),BorderLayout.CENTER);
        contents.add(new JLabel("V0.0.0"),BorderLayout.SOUTH);
        contents.add(new FieldController(sim),BorderLayout.WEST);
        
        pack();
        setVisible(true);
    }
	
	

}
