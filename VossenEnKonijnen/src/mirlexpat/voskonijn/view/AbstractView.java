package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.util.Map;

import javax.swing.JPanel;

import mirlexpat.voskonijn.logic.AbstractModel;

public abstract class AbstractView extends JPanel {
	
	// A map for storing colors for participants in the simulation
    protected Map<Class, Color> colors;
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    
    public AbstractView(Map<Class, Color> colors){
    	this.colors = colors;
    }
    
    /**
     * @return The color to be used for a given class of animal.
     */
    protected Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

	public abstract void update();

}
