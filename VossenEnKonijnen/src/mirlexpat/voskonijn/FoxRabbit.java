package mirlexpat.voskonijn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

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
import mirlexpat.voskonijn.logic.Simulator;
import mirlexpat.voskonijn.view.HistogramView;
import mirlexpat.voskonijn.view.LineGraphView;
import mirlexpat.voskonijn.view.PieChartView;
import mirlexpat.voskonijn.view.SimulatorView;
import mirlexpat.voskonijn.view.GraphView;

public class FoxRabbit extends JFrame {
	
	// A graphical view of the simulation.
    private SimulatorView view;
    private Simulator sim;
    private GraphView lineGraph;
    private GraphView histoGram;
    private GraphView pieChart;
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
	
	public static void main(String[] args){
    	new FoxRabbit();
    }
	
	public FoxRabbit(){
		sim = new Simulator();
		
        colors = new LinkedHashMap<Class, Color>();
        setColor(Rabbit.class, Color.orange);
        setColor(Fox.class, Color.blue);
        setColor(Hunter.class, Color.red);
        setColor(KomodoDragon.class, new Color(0,191,0));
        
        view = new SimulatorView(sim,colors);
        
        lineGraph = new LineGraphView(sim,colors);
        histoGram = new HistogramView(sim,colors);
        pieChart = new PieChartView(sim,colors);
        
        setTitle("Fox and Rabbit Simulation");
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	setLocation(100, 50);
    	Container contents = getContentPane();
        
        setJMenuBar(new MenuController(sim));
        
        JPanel graphs = new JPanel();
        GridLayout gl = new GridLayout(0,1);
        gl.setVgap(8);
        graphs.setLayout(gl); //PUT GRAPH VIEWS IN HERE
        graphs.add(lineGraph);
        graphs.add(histoGram);
        graphs.add(pieChart);
        
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
        sim.addView(lineGraph);
        sim.addView(histoGram);
        sim.addView(pieChart);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                System.exit(0);
            }
        });
	}
	
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }
	
	
}
