import javax.swing.*;

import view.CraneView;
import model.CraneModel;


public class Main {

	public static void main(String[] args) {
		CraneModel model = new CraneModel();
		CraneView vGraphical = new CraneView(model);

		JFrame frame = new JFrame("Crane Game");
		frame.getContentPane().add(vGraphical);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
