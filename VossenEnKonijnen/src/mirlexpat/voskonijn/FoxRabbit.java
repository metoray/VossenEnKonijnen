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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import mirlexpat.voskonijn.actor.*;
import mirlexpat.voskonijn.view.*;
import mirlexpat.voskonijn.controller.MenuController;
import mirlexpat.voskonijn.controller.SimulationController;
import mirlexpat.voskonijn.logic.Simulator;

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
		UIManager.put("Slider.paintValue", true);
		new FoxRabbit();
	}

	public FoxRabbit(){
		sim = new Simulator();

		colors = new LinkedHashMap<Class, Color>();
		setColor(Rabbit.class, Color.orange);
		setColor(Fox.class, Color.blue);
		setColor(Hunter.class, Color.red);
		setColor(KomodoDragon.class, new Color(0,127,0));

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
		graphs.add(new IconView(colors));
		
		
		
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
