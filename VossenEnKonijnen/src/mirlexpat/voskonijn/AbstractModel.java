package mirlexpat.voskonijn;

import java.util.ArrayList;

import mirlexpat.voskonijn.view.IView;

public class AbstractModel {
	
    private ArrayList<IView> views;
    
    public AbstractModel(){
    	views = new ArrayList<>();
    }
	
	protected void notifyViews() {
		for(IView view: views){
			view.update();
		}
		
	}
	
	public void addView(IView view){
		views.add(view);
		view.update();
	}

}
