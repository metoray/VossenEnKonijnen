package mirlexpat.voskonijn.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.logic.Simulator;

public class MenuController extends JMenuBar {
	
	JFrame f2 = new JFrame("Settings Frame");
	
	public MenuController(final Simulator sim){
	    JMenu fileMenu = new JMenu("File");
	    JMenu toolMenu = new JMenu("Tools");
	    JMenu settingsMenu = new JMenu("Settings");
	    
	    JMenuItem newMenuItem = new JMenuItem("New Simulation");
	    JMenuItem saveMenuItem = new JMenuItem("Save Simulation Settings");
	    JMenuItem quitMenuItem = new JMenuItem("Quit");
	    JMenuItem komodoDragonSettings = new JMenuItem("KomodoDragon Settings");

	    fileMenu.add(newMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(quitMenuItem);
	    settingsMenu.add(komodoDragonSettings);
	    
	    add(fileMenu);
	    add(toolMenu);
	    add(settingsMenu);
	    
	    f2.setSize(500,500);
	    
	    final JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
	    
	    komodoDragonSettings.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50,  25);
	    		//JSlider(0,50);
	    		f2.add(slider, BorderLayout.CENTER);
	    		f2.setVisible(true);
	    		//fieldView.dispose(true);
	    	}});
	    
	    newMenuItem.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		
	    		new NewController(window,sim);
	    		
	    	}
	    });
	    
	    saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("FoxRabbitSettings", "frst");
				fc.setFileFilter(filter);
				int returnVal = fc.showSaveDialog(window);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if(!file.toString().endsWith(".frst")){
						file = new File(file.toString()+".frst");
					}
					ObjectOutputStream oos;
					try {
						oos = new ObjectOutputStream(new FileOutputStream(file));
						oos.writeObject(sim.getField().getSettings());
						oos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
	    
	}}
