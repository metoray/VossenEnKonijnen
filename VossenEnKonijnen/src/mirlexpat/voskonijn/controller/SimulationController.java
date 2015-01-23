package mirlexpat.voskonijn.controller;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mirlexpat.voskonijn.logic.Simulator;

public class SimulationController extends JPanel {
	
	public SimulationController(final Simulator sim){
	
		JPanel buttonpanel = new JPanel();
	    
	    GridBagLayout gl = new GridBagLayout();
	    buttonpanel.setLayout(gl);
	    
	    final JTextField stepAmount = new JTextField("100");
	    JButton step = new JButton("Step");
	    JButton start = new JButton("Start");
	    JButton stop = new JButton("Stop");
	    JButton reset = new JButton("Reset");
	    
	    step.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sim.simulate(Integer.valueOf(stepAmount.getText()));
				
			}
		});
	    
	    start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sim.startRunning();
				
			}
		});
	    
	    stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sim.stopRunning();
				
			}
		});
	    
	    reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sim.reset();
				
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
	    c.gridy = 2;
	    c.gridx = 0;
	    c.gridwidth = 2;
	    buttonpanel.add(reset,c);
	    
	    setLayout(new FlowLayout());
	    add(buttonpanel);
	    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	}

}
