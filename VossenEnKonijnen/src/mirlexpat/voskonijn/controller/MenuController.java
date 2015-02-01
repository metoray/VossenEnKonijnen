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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mirlexpat.voskonijn.logic.FieldSettings.AnimalEntry;
import mirlexpat.voskonijn.logic.FieldSettings;
import mirlexpat.voskonijn.logic.Simulator;

/**
 * This is the top menu
 * @author metoray
 *
 */
public class MenuController extends JMenuBar {
	
	/**
	 * Constructor to create menu
	 * @param sim The simulator
	 */
	public MenuController(final Simulator sim){
	    JMenu fileMenu = new JMenu("File");
	    
	    JMenuItem newMenuItem = new JMenuItem("New Simulation");
	    JMenuItem saveMenuItem = new JMenuItem("Save Simulation Settings");
	    JMenuItem openMenuItem = new JMenuItem("Open Simulation Settings");
	    JMenuItem quitMenuItem = new JMenuItem("Quit");

	    fileMenu.add(newMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.add(openMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(quitMenuItem);
	    
	    add(fileMenu);
	    
	    final JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
	    final JFileChooser fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("FoxRabbitSettings", "frst");
		fc.setFileFilter(filter);
	    
	    newMenuItem.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e)
	    	{
	    		
	    		new NewController(window,sim);
	    		
	    	}
	    });
	    
	    saveMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
	    
	    openMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fc.showOpenDialog(window);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if(file.exists()){
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
							FieldSettings settings = (FieldSettings)ois.readObject();
							ois.close();
							sim.newField(settings);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				
			}
		});
	    
	}}
