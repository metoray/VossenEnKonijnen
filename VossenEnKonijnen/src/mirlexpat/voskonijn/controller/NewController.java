package mirlexpat.voskonijn.controller;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.logic.Simulator;

public class NewController extends JFrame {
	
	public NewController(Simulator sim){
		setLayout(new GridLayout(0,1));
		for(AnimalEntry entry: sim.getField().getSettings().getSpawnList()){
			add(new AnimalSettingsController(entry));
		}
		pack();
		setVisible(true);
	}

}
