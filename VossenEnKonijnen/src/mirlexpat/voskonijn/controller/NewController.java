package mirlexpat.voskonijn.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.logic.FieldSettings;
import mirlexpat.voskonijn.logic.Simulator;

public class NewController extends JDialog implements ActionListener {
	
	MComboSlider width, height, grassGrowth;
	JFormattedTextField randomSeed;
	JButton ok, cancel;
	Simulator sim;
	FieldSettings settings;
	
	public NewController(JFrame owner, Simulator sim){
		super(owner, "New Simulation", true);
		
		this.sim = sim;
		
		settings = new FieldSettings(sim.getField().getSettings());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
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
		
		JPanel grassAndRandomPanel = new JPanel();
		grassAndRandomPanel.setLayout(new GridLayout(1,2));
		fieldPanel.add(grassAndRandomPanel);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
		randomSeed = new JFormattedTextField(format);
		randomSeed.setValue(settings.getRandomSeed());
		randomSeed.setHorizontalAlignment(JFormattedTextField.RIGHT);
		
		grassGrowth = new MComboSlider(0, 100, (int)(settings.getGrassGrowthChance()*100), "%");
		grassGrowth.setTicks(5, 25);
		grassGrowth.addTitleBorder("grass growth");
		
		JPanel seedPanel = new JPanel();
		seedPanel.setLayout(new BoxLayout(seedPanel, BoxLayout.X_AXIS));
		seedPanel.add(randomSeed);

		seedPanel.setBorder(BorderFactory.createTitledBorder("random seed"));
		
		grassAndRandomPanel.add(seedPanel);
		grassAndRandomPanel.add(grassGrowth);
		
		JPanel animalPanel = new JPanel();
		animalPanel.setLayout(new GridLayout(0,1));
		for(AnimalEntry entry: settings.getSpawnList().values()){
			animalPanel.add(new AnimalSettingsController(entry));
		}
		
		JScrollPane scrollPane = new JScrollPane(animalPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8),BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		panel.add(scrollPane,BorderLayout.CENTER);
		panel.add(fieldPanel,BorderLayout.NORTH);
		panel.add(buttonPanel,BorderLayout.SOUTH);
		
		this.add(panel);
		this.setSize(640,480);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ok){
			settings.setWidth(width.getValue());
			settings.setDepth(height.getValue());
			settings.setGrassGrowthChance(grassGrowth.getValue()/100d);
			settings.setRandomSeed(((Number)randomSeed.getValue()).intValue());
			
			sim.newField(settings);
			this.dispose();
		}
		else if(e.getSource()==cancel){
			this.dispose();
		}
		
	}

}
