package mirlexpat.voskonijn.controller;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mirlexpat.voskonijn.Field;
import mirlexpat.voskonijn.Simulator;

public class SimulatorController extends JPanel {
	
	public SimulatorController(final Field field){
	
		JPanel buttonpanel = new JPanel();
	    
	    GridBagLayout gl = new GridBagLayout();
	    buttonpanel.setLayout(gl);
	    
	    final JTextField stepAmount = new JTextField("100");
	    JButton step = new JButton("Step");
	    JButton start = new JButton("Start");
	    JButton stop = new JButton("Stop");
	    
	    step.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				field.simulate(Integer.valueOf(stepAmount.getText()));
				
			}
		});
	    
	    start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				field.startRunning();
				
			}
		});
	    
	    stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				field.stopRunning();
				
			}
		});
	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.BOTH;
	    c.weightx = 1;
	    c.insets = new Insets(2, 2, 2, 2);
	    c.gridy = 0;
	    c.gridx = 0;
	    buttonpanel.add(step,c);
	    c.gridx = 1;
	    buttonpanel.add(stepAmount,c);
	    c.gridy = 1;
	    c.gridx = 0;
	    buttonpanel.add(start,c);
	    c.gridx = 1;
	    buttonpanel.add(stop,c);
	    
	    setLayout(new FlowLayout());
	    add(buttonpanel);
	    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	}

}
