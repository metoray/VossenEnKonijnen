package mirlexpat.voskonijn.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;

public class AnimalSettingsController extends JPanel {
	AnimalEntry entry;
	public AnimalSettingsController(AnimalEntry entry){
		this.entry = entry;
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);
		JLabel name = new JLabel(entry.getName());
		
	}

	
}