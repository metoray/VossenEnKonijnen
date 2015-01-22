package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import mirlexpat.voskonijn.Field;

public class LineGraphView extends GraphView {

	public LineGraphView(Field field) {
		super(field);
	}

	@Override
	protected void render(Graphics g) {
		g.setColor(Color.red);
		int w = this.getWidth(), h = this.getHeight();
		g.drawLine(0, 0, w, h);
		g.drawLine(0, h, w, 0);
	}

}
