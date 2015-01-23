package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;

public class LineGraphView extends GraphView {
	
	private HashMap<Class,History> histories;

	public LineGraphView(Field field, Map<Class, Color> colors) {
		super(field,colors);
		histories = new HashMap<Class,History>();
	}

	@Override
	protected void render(Graphics g, Field field) {
		scaleLine();
		int h = getHeight();
		
		for(Counter ctr: field.getStats().getCounters()){
			Class cls = ctr.getClazz();
			int count = ctr.getCount();
			History hist;
			if(histories.containsKey(cls)){
				hist = histories.get(cls);
			}
			else{
				hist = new History(getWidth());
				histories.put(cls, hist);
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
	}
	
	private int getScaled(int n){
		if(n>0) {
			return (int)((Math.log(n)/Math.log(2))*8);
		}
		return 0;
	}
	
	private class History{
		int[] history;
		
		public History(int size){
			history = new int[size];
			for(int i=0; i<size; i++){
				history[i] = -1;
			}

		}
		
		public void add(int amount){
			for(int i=0; i<history.length-1;i++){
				history[i] = history[i+1];
			}
			history[history.length-1] = amount;
		}
		
		public int[] getHistory(){
			return history;
		}
	}

}
