package mirlexpat.voskonijn.logic;
/**
 * This class contains some methods to update counters according to
 * the numbers of animals and hunters on the field.
 * 
 * @author Patrick Breukelman, Lex Hermans, Mirko Rog
 * @version 1.0
 */
import java.util.ArrayList;

import mirlexpat.voskonijn.view.AbstractView;

public class AbstractModel {

	private ArrayList<AbstractView> views;

	public AbstractModel(){
		views = new ArrayList<>();
	}
	/**
	 * Updates the views when data changes.
	 */
	protected void notifyViews() {
		for(AbstractView view: views){
			view.update();
		}

	}
	/**
	 * Adds a view to the simulator.
	 * @param view that needs to be added
	 */
	public void addView(AbstractView view){
		views.add(view);
		view.update();
	}

}
