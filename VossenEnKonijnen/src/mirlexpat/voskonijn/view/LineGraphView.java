package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import mirlexpat.voskonijn.logic.Counter;
import mirlexpat.voskonijn.logic.Field;

public class LineGraphView extends GraphView {
	
	private HashMap<String,History> histories;

	public LineGraphView(Field field) {
		super(field);
		histories = new HashMap<String,History>();
	}

	@Override
	protected void render(Graphics g, Field field) {
		int h = getHeight();
		for(Counter ctr: field.getStats().getCounters()){
			String name = ctr.getName();
			int count = ctr.getCount();
			History hist;
			if(histories.containsKey(name)){
				hist = histories.get(name);
			}
			else{
				hist = new History(getWidth());
				histories.put(name, hist);
			}
			hist.add(count);
			g.setColor(Color.red);
			int[] values = hist.getHistory();
			for(int i=0;i<values.length-1;i++){
				g.drawLine(i, h-values[i], i+1, h-values[i+1]);
			}
		}
	}
	
	private class History{
		int[] history;
		
		public History(int size){
			history = new int[size];

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
