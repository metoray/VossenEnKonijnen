package mirlexpat.voskonijn.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mirlexpat.voskonijn.logic.AbstractModel;
/**
 * Creates Icons for the simulator.
 * @param colors Looks up the respective colors of each class and puts them in a map. 
 */
public class IconView extends JPanel {
	
	Map<Class, Color> colors;
    
    public IconView(Map<Class, Color> colors){
    	this.colors = colors;
    	setLayout(new FlowLayout());
    	for(Class clazz: colors.keySet()){
    		ImageIcon icon = loadImage(clazz);
    		if(icon != null) {
    			this.add(new JLabel(icon));
    		}
    	}
    }
    /*
     * This method loads the icons from the /image folder, changes its color and returns it.
     * @param Class
     */
    public ImageIcon loadImage(Class clazz) {
    	File file = new File("image"+File.separator+clazz.getSimpleName()+".png");
    	if(file.exists()){
    		try {
				BufferedImage img = ImageIO.read(file);
				return new ImageIcon(changeColor(img, colors.get(clazz)));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return null;
    }
    /*
     * This method changes the color of the icons. The method only changes the color of non-transparent pixels.
     * @param img The BufferedImage  created by loadImage.
     * @param newcolor The color desired for the icon.
     */
    public BufferedImage changeColor(BufferedImage img, Color newcolor) {
        final int newRGB = newcolor.getRGB();
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
            	int oldARGB = img.getRGB(x, y);
            	img.setRGB(x, y,newRGB&oldARGB);
            }
        }
        return img;
    }

}
