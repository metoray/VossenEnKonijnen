package mirlexpat.voskonijn.controller;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mirlexpat.voskonijn.Field;
import mirlexpat.voskonijn.Simulator;

public class SimulatorController extends JPanel {
	
	private Field field;
	private JTextField stepAmount;
	
	public SimulatorController(final Field field){
		this.field = field;
	
		JPanel buttonpanel = new JPanel();
	    
	    GridLayout gl = new GridLayout(0,2);
	    gl.setHgap(8);
	    gl.setVgap(8);
	    buttonpanel.setLayout(gl);
	    
	    stepAmount = new JTextField("100");
	    
	    JButton step = new JButton("Step");
	    step.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				field.simulate(Integer.valueOf(stepAmount.getText()));
				
			}
		});
	    
	    buttonpanel.add(step);
	    buttonpanel.add(stepAmount);
	    
	    setLayout(new FlowLayout());
	    add(buttonpanel);
	    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
	}

}
