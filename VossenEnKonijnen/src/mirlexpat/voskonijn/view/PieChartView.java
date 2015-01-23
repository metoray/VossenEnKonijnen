package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.FieldStats;

public class PieChartView extends GraphView {
	








	public PieChartView(Field field, Map<Class, Color> colors) {
		super(field,colors);

	}

	@Override
	protected void render(Graphics g, Field field) {
		int w = getWidth();
		int h = getHeight();
		
		
		FieldStats stats = field.getStats();
		Collection<Counter> counters = stats.getCounters();
		int runningTotal = 0;
		double total = stats.getTotal();
		for(Counter ctr: counters){
			Class cls = ctr.getClazz();
			int count = ctr.getCount();
			g.setColor(getColor(cls));
			
			int scaled = (int) (count/total*360);
			
			g.fillArc(50,5,100,100,runningTotal,scaled);
		//	g.fillArc(x, y, width, height, startAngle, arcAngle);
			runningTotal+=scaled;
		}
			
	}
	
	
	private int getScaled(int n){
		if(n>0) {
			return (int)((Math.log(n)/Math.log(2))*8);
		}
		return 0;
	}
	

}
