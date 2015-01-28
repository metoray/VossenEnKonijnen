package mirlexpat.voskonijn.controller;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MComboSlider extends JPanel implements ChangeListener {

	JSlider slider;
	JLabel text;
	
	public MComboSlider(int min, int max, int value){
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		text = new JLabel(""+value);
		add(text);
		slider = new JSlider(min,max,value);
		slider.addChangeListener(this);
		add(slider);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider){
			text.setText(""+slider.getValue());
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
	
}
