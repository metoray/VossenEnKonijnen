package mirlexpat.voskonijn.controller;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MComboSlider extends JPanel implements ChangeListener {

	private JSlider slider;
	private JLabel text;
	
	private String suffix;
	
	public MComboSlider(int min, int max, int value) {
		this(min,max,value,"");
	}
	
	public MComboSlider(int min, int max, int value, String suffix){
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.suffix = suffix;
		text = new JLabel(value+suffix);
		add(text);
		slider = new JSlider(min,max,value);
		slider.addChangeListener(this);
		add(slider);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider){
			text.setText(slider.getValue()+suffix);
		}
		
	}
	
	public int getValue(){
		return slider.getValue();
	}
	
	public void setTicks(int minor, int major){
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(major);
		slider.setMinorTickSpacing(minor);
	}
	
	public void addTitleBorder(String title){
		setBorder(BorderFactory.createTitledBorder(title));
	}
	
	public void addChangeListener(ChangeListener listener){
		slider.addChangeListener(listener);
	}
	
}
