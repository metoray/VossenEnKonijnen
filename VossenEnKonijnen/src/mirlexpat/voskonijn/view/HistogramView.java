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

public class HistogramView extends GraphView {
	








	public HistogramView(Simulator sim, Map<Class, Color> colors) {
		super(sim,colors);

	}

	@Override
	protected void render(Graphics g, Field field) {
		int w = getWidth();
		int h = getHeight();
		
		Collection<Counter> counters = field.getStats().getCounters();
		int i = 0;
		int barWidth = w/counters.size();
		for(Counter ctr: counters){
			Class cls = ctr.getClazz();
			int count = ctr.getCount();
			g.setColor(getColor(cls));
			
			int scaled = getScaled(count);
			g.drawRect(barWidth*i+8, h-scaled, barWidth-16, scaled);
			i++;
		}
		
		//	g.drawRect(x, y, width, height);
			
		}
	
	
	private int getScaled(int n){
		return (int)(Math.log(n)/Math.log(1.1));
	}
	

}
