package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import mirlexpat.voskonijn.Counter;
import mirlexpat.voskonijn.Field;

public class LineGraphView extends GraphView {

	public LineGraphView(Field field) {
		super(field);
	}

	@Override
	protected void render(Graphics g, Field field) {
		g.setColor(Color.red);
		int w = this.getWidth(), h = this.getHeight();
		int y = 0;
		for(Counter ctr: field.getStats().getCounters()){
			g.drawString(ctr.getName()+": "+ctr.getCount(), 8, y);
			y+=16;
		}
	}

}
