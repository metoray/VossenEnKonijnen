package mirlexpat.voskonijn.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.logic.FieldSettings;
import mirlexpat.voskonijn.logic.Simulator;

public class NewController extends JFrame {
	
	MComboSlider width, height;
	
	public NewController(Simulator sim){
		FieldSettings settings = sim.getField().getSettings();
		
		setLayout(new BorderLayout());
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		fieldPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		
		width = new MComboSlider(10, 200, settings.getWidth());
		width.setTicks(5, 20);
		width.addTitleBorder("width");
		
		height = new MComboSlider(10, 200, settings.getDepth());
		height.setTicks(5, 20);
		height.addTitleBorder("height");
		fieldPanel.add(width);
		fieldPanel.add(height);
		
		JPanel animalPanel = new JPanel();
		animalPanel.setLayout(new GridLayout(0,1));
		for(AnimalEntry entry: sim.getField().getSettings().getSpawnList().values()){
			animalPanel.add(new AnimalSettingsController(entry));
		}
		
		JScrollPane scrollPane = new JScrollPane(animalPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		add(scrollPane,BorderLayout.CENTER);
		add(fieldPanel,BorderLayout.NORTH);
		
		setPreferredSize(new Dimension(768,512));
		
		pack();
		setVisible(true);
	}

}
