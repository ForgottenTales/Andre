package tour.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4575209619441571141L;

	public MainWindow() {
		setTitle("TourPlaner - Master Andre");
		setSize(500, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		JButton startButton = new JButton("start");
		
		add(startButton,BorderLayout.WEST);
	}
	
}
