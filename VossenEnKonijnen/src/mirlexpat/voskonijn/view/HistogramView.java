package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;

public class HistogramView extends GraphView {
	








	public HistogramView(Field field, Map<Class, Color> colors) {
		super(field,colors);

	}

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
	
	
	private int getScaled(int n){
		if(n>0) {
			return (int)((Math.log(n)/Math.log(2))*8);
		}
		return 0;
	}
	

}
