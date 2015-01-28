package mirlexpat.voskonijn.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;

public class AnimalSettingsController extends JPanel {
	AnimalEntry entry;
	public AnimalSettingsController(AnimalEntry entry){
		this.entry = entry;
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(new JLabel(entry.getName()));
		add(new JLabel("Max Age"));
		JSlider age = new JSlider();
		//age.setMaximum(100);
		//age.setPaintLabels(true);
		//age.setMajorTickSpacing(20);
		add(age);
	}

	
}