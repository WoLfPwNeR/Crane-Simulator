package model;
// Note!  Nothing from the view package is imported here.
import java.util.ArrayList;

import view.IView;

import model.BlockModel;

public class CraneModel extends Object {
	/* A list of the model's views. */
	public ArrayList<IView> views = new ArrayList<IView>();
	
	public ArrayList<BlockModel> crane = new ArrayList<BlockModel>();
	public ArrayList<BlockModel> block = new ArrayList<BlockModel>();
	
	public int gl=550;

	// Override the default construtor, making it private.
	public CraneModel() {
		crane.add(new BlockModel(50, gl-135, 200, 100, false, 75, gl-50, 50, 50));//0
		crane.add(new BlockModel(150, gl-225, 150, gl-125, 10, true));
		crane.add(new BlockModel(150, gl-225, 250, gl-225, 10, true));
		crane.add(new BlockModel(250, gl-225, 350, gl-225, 10, true));
		crane.add(new BlockModel(350, gl-225, 450, gl-225, 10, true));
		crane.add(new BlockModel(450, gl-225, 450, gl-175, 10, true));
		crane.add(new BlockModel(425, gl-175, 475, gl-175, 10, false));//6
		
		block.add(new BlockModel(600,gl-50,50,50,false));//0
		block.add(new BlockModel(660,gl-50,50,50,false));
		block.add(new BlockModel(720,gl-50,50,50,false));
		block.add(new BlockModel(630,gl-100,50,50,false));
		block.add(new BlockModel(690,gl-100,50,50,false));
		block.add(new BlockModel(660,gl-150,50,50,false));//5
	}


	/** Add a new view of this triangle. */
	public void addView(IView view) {
		this.views.add(view);
		view.updateView();
	}

	/** Remove a view from this triangle. */
	public void removeView(IView view) {
		this.views.remove(view);
	}

	/** Update all the views that are viewing this triangle. */
	public void updateAllViews() {
		for (IView view : this.views) {
			view.updateView();
		}
	}
}