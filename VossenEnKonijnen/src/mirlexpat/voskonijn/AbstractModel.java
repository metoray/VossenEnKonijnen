package mirlexpat.voskonijn;

import java.util.ArrayList;

import mirlexpat.voskonijn.view.AbstractView;

public class AbstractModel {
	
    private ArrayList<AbstractView> views;
    
    public AbstractModel(){
    	views = new ArrayList<>();
    }
	
	protected void notifyViews() {
		for(AbstractView view: views){
			view.update();
		}
		
	}
	
	public void addView(AbstractView view){
		views.add(view);
		view.update();
	}

}
