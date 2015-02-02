package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import mirlexpat.voskonijn.actor.*;
import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.Simulator;
/**
 * Textual view of the simulator.
 * Requires console capable of ANSI escape codes.
 * @author Mirko Rog
 * @version 1.0
 */
public class TextView extends AbstractView {

	private Simulator sim;
	private HashMap<Class,String> chars;
	
	/**
	 * Constructor for view
	 * @param col Colormap for classes
	 * @param sim Simulator to show
	 */
	public TextView(Map<Class, Color> col, Simulator sim) {
		super(col);
		this.sim = sim;
		this.chars = new HashMap<>();
		this.colors = new HashMap<>();
		chars.put(Fox.class, "\033[34;1mF");
		chars.put(Hunter.class, "\033[31;1mH");
		chars.put(KomodoDragon.class, "\033[32;1mK");
		chars.put(Rabbit.class,"\033[33;1mR");
	}

	/**
	 * Update method for view.
	 * When updated creates a stringbuilder and fills a string with letters and ANSI escape codes.
	 */
	@Override
	public void update() {
		System.out.print("\033[1;1f");
		Field field = sim.getField();
		StringBuilder sb = new StringBuilder();
		for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
            	Object obj = field.getObjectAt(row, col);
            	if(obj!=null&&chars.containsKey(obj.getClass())){
            		String symbol = chars.get(obj.getClass());
            		if(obj instanceof Rabbit && ((Rabbit) obj).isInfected()){
            			symbol = "\033[35;1mr";
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
