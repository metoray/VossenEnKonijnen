package mirlexpat.voskonijn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mirlexpat.voskonijn.actor.Fox;
import mirlexpat.voskonijn.actor.Hunter;
import mirlexpat.voskonijn.actor.KomodoDragon;
import mirlexpat.voskonijn.actor.Rabbit;
import mirlexpat.voskonijn.controller.MenuController;
import mirlexpat.voskonijn.controller.SimulationController;
import mirlexpat.voskonijn.view.LineGraphView;
import mirlexpat.voskonijn.view.SimulatorView;
import mirlexpat.voskonijn.view.GraphView;

public class FoxRabbit extends JFrame {
	
	// A graphical view of the simulation.
    private SimulatorView view;
    private Simulator sim;
    private GraphView graph;
    private GraphView graph2;
	
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
        
        graph = new LineGraphView(sim.getField());
        graph2 = new LineGraphView(sim.getField()); //please don't name your variables this way
        
        setTitle("Fox and Rabbit Simulation");
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setLocation(100, 50);
    	Container contents = getContentPane();
        
        setJMenuBar(new MenuController(sim));
        
        JPanel graphs = new JPanel();
        GridLayout gl = new GridLayout(0,1);
        gl.setVgap(8);
        graphs.setLayout(gl); //PUT GRAPH VIEWS IN HERE
        graphs.add(graph);
        graphs.add(graph2);
        
        JPanel rightSideBar = new JPanel();
        rightSideBar.setLayout(new FlowLayout());
        rightSideBar.add(graphs);
        
        contents.add(new JScrollPane(view),BorderLayout.CENTER);
        contents.add(new JLabel("V0.0.0"),BorderLayout.SOUTH);
        contents.add(new SimulationController(sim),BorderLayout.WEST);
        contents.add(rightSideBar,BorderLayout.EAST);
        
        pack();
        setVisible(true);
        
        sim.addView(view);
        sim.addView(graph);
        sim.addView(graph2);
	}
}
