package mirlexpat.voskonijn.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import mirlexpat.voskonijn.Field;
import mirlexpat.voskonijn.Randomizer;
import mirlexpat.voskonijn.Simulator;

public class MenuController extends JMenuBar {
	
	public MenuController(final Simulator sim){
	    JMenu fileMenu = new JMenu("File");
	    JMenu toolMenu = new JMenu("Tools");
	    
	    JMenuItem newMenuItem = new JMenuItem("New Simulation");
	    JMenuItem saveMenuItem = new JMenuItem("Save Simulation Settings");
	    JMenuItem quitMenuItem = new JMenuItem("Quit");
	    
	    fileMenu.add(newMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.addSeparator();
	    fileMenu.add(quitMenuItem);
	    
	    add(fileMenu);
	    add(toolMenu);
	}

}
