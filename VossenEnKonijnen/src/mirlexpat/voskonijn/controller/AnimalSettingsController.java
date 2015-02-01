package mirlexpat.voskonijn.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;

/**
 * Controller to manipulate a specific entry for an animal in the settings
 * @author Mirko Rog
 *
 */
public class AnimalSettingsController extends JPanel implements ChangeListener  {
	private AnimalEntry entry;
	private HashMap<MComboSlider,String> sliders;

	private static HashMap<String,Range> ranges;
	
	static {
		ranges = new HashMap<String,Range>();
		ranges.put("max age", new Range(0,800));
		ranges.put("max litter size", new Range(0,8));
		ranges.put("food value", new Range(0,80));
		ranges.put("breeding chance", new Range(0,100));
		ranges.put("breeding age", new Range(0,800));
	}

	
	/**
	 * Constructor for this controller
	 * @param entry The AnimalEntry this controller manipulates.
	 */
	public AnimalSettingsController(AnimalEntry entry){
		this.entry = entry;
		sliders = new HashMap<MComboSlider,String>();
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		for(Entry<String,Number> mapEntry: entry.getSettings().entrySet()){
			String key = mapEntry.getKey();
			Number value = mapEntry.getValue();
			Range range = AnimalSettingsController.getRange(key);
			
			MComboSlider slider;
			if(value instanceof Integer){
				slider = new MComboSlider(range.getMin(), range.getMax(), value.intValue());
			}
			else if(value instanceof Double){
				slider = new MComboSlider(range.getMin(), range.getMax(), (int)(value.doubleValue()*100),"%");
			}
			else {
				continue;
			}
			
			slider.setTicks(range.getMax()/10, range.getMax());
			slider.addChangeListener(this);
			slider.addTitleBorder(key);
			sliders.put(slider, key);
			add(slider);
		}

		TitledBorder border = BorderFactory.createTitledBorder(entry.getName());
		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.darkGray, Color.black));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), border));

	}

	/**
	 * Listener method for when a slider changes state
	 * @param e The ChangeEvent from the changed slider.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		for(Entry<MComboSlider,String> mapEntry: sliders.entrySet()){
			if(mapEntry.getKey().isSource(source)){
				if(entry.isDouble(mapEntry.getValue())){
					entry.set(mapEntry.getValue(), mapEntry.getKey().getValue()/100d);
				}
				else{
					entry.set(mapEntry.getValue(), mapEntry.getKey().getValue());
				}
			}
		}
	}
	
	/**
	 * The range a slider should have
	 * @param key The option associated with the range.
	 * @return The Range of the option.
	 */
	public static Range getRange(String key){
		Range range = ranges.get(key);
		return range!=null?range:new Range(0,100);
	}
	
	/**
	 * Minor class that stores a max and min value
	 * @author metoray
	 *
	 */
	private static class Range {
		private final int min, max;

		/**
		 * The constructor for a range of numbers
		 * @param min Minimum value.
		 * @param max Maximum value.
		 */
		public Range(int min, int max) {
			super();
			this.min = min;
			this.max = max;
		}

		/**
		 * getter for minimum
		 * @return minimum
		 */
		public int getMin() {
			return min;
		}

		/**
		 * getter for maximum
		 * @return maximum
		 */
		public int getMax() {
			return max;
		}
		
	}
	
}



