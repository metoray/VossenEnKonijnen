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
import mirlexpat.voskonijn.logic.Simulator;
/**
 * Renders a pieview on screen, which changes according to the simulator.
 * 
 * @author Lex Hermans, Mirko Rog
 * @version 1.0
 */
public class PieChartView extends GraphView {

	public PieChartView(Simulator sim, Map<Class, Color> colors) {
		super(sim,colors);

	}
	/**
	 * Renders the pieview on screen.
	 * Scales according to the screen the pieview is placed in.
	 */
	@Override
	protected void render(Graphics g, Field field) {
		int w = getWidth();
		int h = getHeight();

		int radius = 50;

		FieldStats stats = field.getStats();
		int totalDegLeft = 360;
		int runningDeg = 0;
		int totalCountLeft = stats.getTotal();
		for(Counter ctr: stats.getCounters()){
			int count = ctr.getCount();
			int deg = (int)Math.round((count/(double)totalCountLeft)*totalDegLeft);

			g.setColor(getColor(ctr.getClazz()));
			g.fillArc(w/2-radius,h/2-radius,radius*2,radius*2,runningDeg,deg);

			totalDegLeft -= deg;
			totalCountLeft -= count;
			runningDeg += deg;
		}

	}

}
