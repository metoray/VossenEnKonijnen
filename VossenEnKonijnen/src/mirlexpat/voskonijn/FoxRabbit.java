package mirlexpat.voskonijn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mirlexpat.voskonijn.actor.*;
import mirlexpat.voskonijn.view.*;
import mirlexpat.voskonijn.controller.MenuController;
import mirlexpat.voskonijn.controller.SimulationController;
import mirlexpat.voskonijn.logic.FieldSettings;
import mirlexpat.voskonijn.logic.Simulator;

public class FoxRabbit {

	// A map for storing colors for participants in the simulation
	private static Map<Class, Color> colors;
	
	static {
		colors = new LinkedHashMap<Class, Color>();
		setColor(Rabbit.class, Color.orange);
		setColor(Fox.class, Color.blue);
		setColor(Hunter.class, Color.red);
		setColor(KomodoDragon.class, new Color(0,127,0));
	}

	public static void main(String[] args){
		if(args.length==0){
			new FoxRabbit();
		}
		else {
			new FoxRabbit(args);
		}
	}

	public FoxRabbit(){
		Simulator sim = new Simulator();

		SimulatorView view = new SimulatorView(sim,colors);

		GraphView lineGraph = new LineGraphView(sim,colors);
		GraphView histoGram = new HistogramView(sim,colors);
		GraphView pieChart = new PieChartView(sim,colors);
		
		JFrame frame = new JFrame();
		

		frame.setTitle("Fox and Rabbit Simulation");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(100, 50);
		Container contents = frame.getContentPane();

		frame.setJMenuBar(new MenuController(sim));

		
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

		frame.pack();
		frame.setVisible(true);

		sim.addView(view);
		sim.addView(lineGraph);
		sim.addView(histoGram);
		sim.addView(pieChart);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				System.exit(0);
			}
		});
	}
	
	public FoxRabbit(String[] args){
		Simulator sim = new Simulator();
		TextView view = new TextView(colors, sim);
		FieldSettings settings = new FieldSettings();
		settings.setWidth(180);
		settings.setDepth(60);
		sim.newField(settings);
		sim.addView(view);
		sim.simulate(4000);
		sim.waitToEnd();
	}

	/**
	 * Define a color to be used for a given class of animal.
	 * @param animalClass The animal's Class object.
	 * @param color The color to be used for the given class.
	 */
	public static void setColor(Class animalClass, Color color)
	{
		colors.put(animalClass, color);
	}


}
