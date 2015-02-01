package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Simulator;
/**
 * Renders a linegraph on screen, which changes according to the simulator.
 * 
 * @author Mirko Rog
 * @version 1.0
 */
public class LineGraphView extends GraphView {

	private HashMap<Class,History> histories;
	private int lastStep;

	public LineGraphView(Simulator sim, Map<Class, Color> colors) {
		super(sim,colors);
		histories = new HashMap<Class,History>();
		lastStep = -1;
	}
	/**
	 * Renders the linegraph on screen.
	 * Scales according to the screen the linegraph is placed in.
	 */
	@Override
	protected void render(Graphics g, Field field) {
		scaleLine();
		int h = getHeight();
		if(field.getStep()<lastStep){
			histories.clear();
			lastStep = -1;
		}

		for(Counter ctr: field.getStats().getCounters()){
			Class cls = ctr.getClazz();
			int count = ctr.getCount();
			History hist;
			if(histories.containsKey(cls)){
				hist = histories.get(cls);
			}
			else{
				hist = new History(getWidth());
				if(field.getStep() > lastStep){
					histories.put(cls, hist);
				}
			}
			hist.add(count);
			g.setColor(getColor(cls));
			int[] values = hist.getHistory();
			for(int i=0;i<values.length-1;i++){
				int current = values[i];
				if(current<0) continue;
				int next = values[i+1];
				g.drawLine(i, h-getScaled(current), i+1, h-getScaled(next));
			}
			g.drawString(""+values[values.length-1], 160, h-getScaled(values[values.length-1]));
		}

		lastStep = field.getStep();
	}
	/**
	 * Scales the view to the size of the application window.
	 * @param n
	 * @return 0
	 */
	private int getScaled(int n){
		if(n>0) {
			return (int)((Math.log(n)/Math.log(2))*8);
		}
		return 0;
	}
	/**
	 * Stores the previous step's values in a variable to draw an accurate line.
	 */
	private class History{
		int[] history;

		public History(int size){
			history = new int[size];
			for(int i=0; i<size; i++){
				history[i] = -1;
			}

		}
		/**
		 * Adds a new value to the variable explained in History's Javadocs.
		 * @param amount that needs to be added
		 */
		public void add(int amount){
			for(int i=0; i<history.length-1;i++){
				history[i] = history[i+1];
			}
			history[history.length-1] = amount;
		}
		/**
		 * Returns the amount currently stored in history.
		 * @return amount currently stored in history
		 */
		public int[] getHistory(){
			return history;
		}
	}

}
