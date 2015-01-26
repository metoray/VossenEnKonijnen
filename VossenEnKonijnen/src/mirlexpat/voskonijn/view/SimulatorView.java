package mirlexpat.voskonijn.view;

import java.awt.*;

import javax.swing.*;

import mirlexpat.voskonijn.logic.Field;
import mirlexpat.voskonijn.logic.FieldStats;
import mirlexpat.voskonijn.logic.Simulator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class SimulatorView extends AbstractView
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private FieldView fieldView;
    private Simulator sim;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(Simulator sim, Map<Class, Color> colors)
    {
    	super(colors);

        stepLabel = new JLabel(STEP_PREFIX, SwingConstants.CENTER);
        population = new JLabel(POPULATION_PREFIX, SwingConstants.CENTER);
        
        
        fieldView = new FieldView(sim);

        
        
        
        JPanel gridpanel = new JPanel();
        gridpanel.setLayout(new BorderLayout());
        
        gridpanel.add(stepLabel, BorderLayout.NORTH);
        gridpanel.add(fieldView, BorderLayout.CENTER);
        gridpanel.add(population, BorderLayout.SOUTH);
        
        setLayout(new FlowLayout());
        add(gridpanel);
        
        
        this.sim = sim;
        
        
    }

    /**
     * Show the current status of the field.
     * @param field The field whose status is to be displayed.
     */
    public void showStatus(Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + field.getStep());
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }

        population.setText(POPULATION_PREFIX + field.getStats().getPopulationDetails(field));
        fieldView.repaint();
    }
    
    /**
     * Provide a graphical view of a rectangular field. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the field.
     * This is rather advanced GUI stuff - you can ignore this 
     * for your project if you like.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Create a new FieldView component.
         */
        public FieldView(Simulator sim)
        {
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        @Override
		public Dimension getPreferredSize()
        {
            return new Dimension(sim.getField().getWidth() * GRID_VIEW_SCALING_FACTOR,
                                 sim.getField().getDepth() * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(!size.equals(getPreferredSize())) {  // if the size has changed...
                size = getPreferredSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / sim.getField().getWidth();
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / sim.getField().getDepth();
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this field in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
        	if(g==null) return;
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The field view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        @Override
		public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                 g.drawImage(fieldImage, 0, 0, null);

            }
        }
    }

	@Override
	public void update() {
		showStatus(sim.getField());
		
	}
}
