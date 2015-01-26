package mirlexpat.voskonijn.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

import mirlexpat.voskonijn.logic.Simulator;

public class MenuController extends JMenuBar {
	
	JFrame f2 = new JFrame("Settings Frame");
	
	public MenuController(final Simulator sim){
	    JMenu fileMenu = new JMenu("File");
	    JMenu toolMenu = new JMenu("Tools");
	    JMenu settingsMenu = new JMenu("Settings");
	    
	    JMenuItem settingsMenuItem = new JMenuItem("Settings");
	    
	    JMenuItem newMenuItem = new JMenuItem("New Simulation");
	    JMenuItem saveMenuItem = new JMenuItem("Save Simulation Settings");
	    JMenuItem quitMenuItem = new JMenuItem("Quit");
	    
	    fileMenu.add(newMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(quitMenuItem);
	    
	     settingsMenu.add(settingsMenuItem);
	    
	    newMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sim.newField(200,200);
				
			}
		});
	    
	    
	    
	    add(fileMenu);
	    add(toolMenu);
	    add(settingsMenu);
	    
	    f2.setSize(500,500);
	    
	    settingsMenuItem.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50,  25);
	    		//JSlider(0,50);
	    		f2.add(slider, BorderLayout.CENTER);
	    		f2.setVisible(true);
	    		//fieldView.dispose(true);
	    	}
	    });
	}

}
