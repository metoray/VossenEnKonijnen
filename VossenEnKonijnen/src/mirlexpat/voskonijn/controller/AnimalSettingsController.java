package mirlexpat.voskonijn.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;

public class AnimalSettingsController extends JPanel implements ChangeListener  {
	private AnimalEntry entry;
	private JSlider age, breedAge, breedprob, litter, food;
	public AnimalSettingsController(AnimalEntry entry){
		this.entry = entry;
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		add(new JLabel(entry.getName()));
		
		add(new JLabel("Max Age"));
		age = new JSlider();
		age.addChangeListener(this);
		add(age);
		
		add(new JLabel("Breeding Age"));
		breedAge = new JSlider();
		breedAge.addChangeListener(this);
		add(breedAge);

		add(new JLabel("Breeding Probability"));
		breedprob = new JSlider();
		breedprob.addChangeListener(this);
		add(breedprob);
		
		add(new JLabel("Max Litter Size"));
		litter = new JSlider();
		litter.addChangeListener(this);
		add(litter);
		
		add(new JLabel("Food Value"));
		food = new JSlider();
		food.addChangeListener(this);
		add(food);
		
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==age){
		entry.setMaxAge(age.getValue());
		}
		if(e.getSource()==breedAge){
		entry.setChance(breedAge.getValue());
		}
		if(e.getSource()==breedprob){
		entry.setBreedChance(breedprob.getValue());
		}
		if(e.getSource()==litter){
		entry.setMaxLitter(litter.getValue());
		}
		if(e.getSource()==food){
		entry.setFoodValue(food.getValue());
		}
	}}
		

	
