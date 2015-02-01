package mirlexpat.voskonijn.logic;

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
