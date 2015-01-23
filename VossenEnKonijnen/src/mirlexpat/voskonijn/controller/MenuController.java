package mirlexpat.voskonijn.controller;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mirlexpat.voskonijn.logic.Simulator;

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
