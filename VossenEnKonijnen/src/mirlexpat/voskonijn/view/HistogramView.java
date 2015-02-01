package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Simulator;
/**
 * Renders a histogram on screen, which changes according to the simulator.
 * 
 * @author Lex Hermans, Mirko Rog
 * @version 1.0
 */
public class HistogramView extends GraphView {
	
	public HistogramView(Simulator sim, Map<Class, Color> colors) {
		super(sim,colors);

	}
	/**
	 * Renders the histogram on screen.
	 * Scales according to the screen the histogram is placed in.
	 */
	@Override
	protected void render(Graphics g, Field field) {
		int w = getWidth();
		int h = getHeight();
		int inset = 8;
		
		
		
		Collection<Counter> counters = field.getStats().getCounters();
		int i = 0;
		int barWidth = w/counters.size();
		for(Counter ctr: counters){
			Class cls = ctr.getClazz();
			int count = ctr.getCount();
			g.setColor(getColor(cls));
			
			int scaled = getScaled(count);
			g.fill3DRect(barWidth*i+inset, h-scaled, barWidth-2*inset, scaled, true);
			i++;
		}
		scaleLine();
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
	

}
