package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.actor.*;
import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Simulator;
/**
 * Textual view of the simulator.
 * @author Mirko Rog
 * @version 1.0
 */
public class TextView extends AbstractView {

	private Simulator sim;
	private HashMap<Class,String> chars;
	private HashMap<Class,String> colors;
	
	
	
	public TextView(Map<Class, Color> col, Simulator sim) {
		super(col);
		this.sim = sim;
		this.chars = new HashMap<>();
		this.colors = new HashMap<>();
		chars.put(Fox.class, "F");
		chars.put(Hunter.class, "H");
		chars.put(KomodoDragon.class, "K");
		chars.put(Rabbit.class,"R");
		
		colors.put(Fox.class, "\033[34;1m");
		colors.put(Hunter.class, "\033[31;1m");
		colors.put(KomodoDragon.class, "\033[32;1m");
		colors.put(Rabbit.class, "\033[33;1m");
	}

	@Override
	public void update() {
		System.out.print("\033[1;1f");
		Field field = sim.getField();
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
            	Object obj = field.getObjectAt(row, col);
            	if(obj!=null&&chars.containsKey(obj.getClass())&&colors.containsKey(obj.getClass())){
            		String color = colors.get(obj.getClass());
            		if(obj instanceof Rabbit && ((Rabbit) obj).isInfected()){
                		color = "\033[35;1m";
                	}
            		sb.append(color);
            		String symbol = chars.get(obj.getClass());
            		if(obj instanceof Rabbit && ((Rabbit) obj).isInfected()){
            			symbol = "r";
            		}
            		sb.append(symbol);
            	}
            	else{
            		sb.append(" ");
            	}
            }
            sb.append("\n");
		}
		sb.append("\033[0m");
		System.out.print(sb.toString());
		
	}

}
