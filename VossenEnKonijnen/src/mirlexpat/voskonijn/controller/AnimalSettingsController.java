package mirlexpat.voskonijn.controller;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

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

public class AnimalSettingsController extends JPanel implements ChangeListener  {
	private AnimalEntry entry;
	private MComboSlider age, breedAge, breedprob, litter, food;
	public AnimalSettingsController(AnimalEntry entry){
		this.entry = entry;
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		age = new MComboSlider(0,800,entry.getMaxAge());
		age.setTicks(25, 100);
		age.addChangeListener(this);
		age.addTitleBorder("max age");
		
		add(age);
		
		breedAge = new MComboSlider(0,400,entry.getBreedAge());
		breedAge.setTicks(25, 100);
		breedAge.addChangeListener(this);
		breedAge.addTitleBorder("breeding age");
		add(breedAge);

		breedprob = new MComboSlider(0,100,(int)(entry.getBreedChance()*100));
		breedprob.setTicks(5, 10);
		breedprob.addChangeListener(this);
		breedprob.addTitleBorder("Breeding Probability");
		add(breedprob);

		litter = new MComboSlider(0,8,entry.getMaxLitter());
		litter.addChangeListener(this);
		litter.setTicks(1, 4);
		litter.addTitleBorder("max litter size");
		add(litter);
		
		food = new MComboSlider(0,100,entry.getFoodValue());
		food.setTicks(5, 20);
		food.addChangeListener(this);
		food.addTitleBorder("food value");
		add(food);
		
		TitledBorder border = BorderFactory.createTitledBorder(entry.getName());
		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.darkGray, Color.black));
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8), border));
		
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		if(age.isSource(source)){
		entry.setMaxAge(age.getValue());
		}
		if(breedAge.isSource(source)){
		entry.setChance(breedAge.getValue());
		}
		if(breedprob.isSource(source)){
		entry.setBreedChance(breedprob.getValue());
		}
		if(litter.isSource(source)){
		entry.setMaxLitter(litter.getValue());
		}
		if(food.isSource(source)){
		entry.setFoodValue(food.getValue());
		}
	}}
		

	
