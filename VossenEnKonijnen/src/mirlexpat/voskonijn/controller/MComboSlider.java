package mirlexpat.voskonijn.controller;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A JPanel that combines a slider with a JLabel
 * @author Mirko Rog
 *
 */
public class MComboSlider extends JPanel implements ChangeListener {

	private JSlider slider;
	private JLabel text;
	
	private String suffix;
	
	/**
	 * Constructor specifying range and value
	 * @param min Min value.
	 * @param max Max value.
	 * @param value Value value.
	 */
	public MComboSlider(int min, int max, int value) {
		this(min,max,value,"");
	}
	
	/**
	 * Constructor specifying range, value and suffix for JLabel
	 * @param min Min value.
	 * @param max Max value.
	 * @param value Current value.
	 * @param suffix Suffix to display behind number, "%" for example.
	 */
	public MComboSlider(int min, int max, int value, String suffix){
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.suffix = suffix;
		text = new JLabel(value+suffix);
		add(text);
		slider = new JSlider(min,max,value);
		slider.addChangeListener(this);
		add(slider);
	}

	/**
	 * Listener for the slider so that the JLabel may change text
	 * @param e ChangeEvent for JSlider
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider){
			text.setText(slider.getValue()+suffix);
		}
		
	}
	
	/**
	 * Getter for JSlider's value
	 * @return value
	 */
	public int getValue(){
		return slider.getValue();
	}
	
	/**
	 * Set the ticks on the JSlider
	 * @param minor Minor ticks.
	 * @param major Major ticks.
	 */
	public void setTicks(int minor, int major){
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(major);
		slider.setMinorTickSpacing(minor);
	}
	
	/**
	 * Adds a titled border around this panel
	 * @param title Title of this panel.
	 */
	public void addTitleBorder(String title){
		setBorder(BorderFactory.createTitledBorder(title));
	}
	
	/**
	 * Add a changelistener for the slider
	 * @param listener The ChangeListener.
	 */
	public void addChangeListener(ChangeListener listener){
		slider.addChangeListener(listener);
	}
	
	/**
	 * Checks if the slider is the given object
	 * @param object Possible source of ChangeEvent
	 * @return true or false
	 */
	public boolean isSource(Object object){
		return object==slider;
	}
	
}
