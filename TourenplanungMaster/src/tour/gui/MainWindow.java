package tour.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import tour.algorithm.AlgorithmHandler;
import tour.algorithm.MemeticAlgorithm;
import tour.algorithm.Tour;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4575209619441571141L;
	private JTextArea output = new JTextArea();
	private JScrollPane jp = new JScrollPane(output);
	
	MemeticAlgorithm algorithm = new MemeticAlgorithm();
	AlgorithmHandler handler = new AlgorithmHandler(algorithm);

	public MainWindow() {
		setTitle("TourPlaner - Master Andre");
		setSize(600, 500);
		setVisible(true);
		setLayout(new BorderLayout());
		
		JButton startButton = new JButton("Run - complete");
		JButton init = new JButton("init");
		JButton step = new JButton("do Step");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithm = new MemeticAlgorithm();
				handler = new AlgorithmHandler(algorithm);
				Tour key = handler.findBestSolution();
				output.append("--------------------------------------\n");
				output.append("Best Solution:\n");
				output.append(Arrays.toString(key.getRoute().toArray()) + " " + Arrays.toString(key.getCuts().toArray()) + "  \n");
			}
		});
		
		step.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		init.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithm = new MemeticAlgorithm();
				handler = new AlgorithmHandler(algorithm);
				output.append("--------------------------------------\n");
				output.append("Initial Population:\n");
				List<Tour> pop = handler.getPopulation();
				for (Tour key : pop)
				{
					output.append(Arrays.toString(key.getRoute().toArray()) + " " + Arrays.toString(key.getCuts().toArray()) + " " + key.getFitness() + "  \n");
					if (key.getSubRoutes() != null)
						for (List<Integer> sR : key.getSubRoutes())
						{
							output.append("    ");
							for (int i = 0; i < sR.size(); i++)
							{
								output.append(sR.get(i) + " ");
							}
						}
					else {
						System.out.println("fehler");
					}
				}
			}
		});
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(startButton);
		buttons.add(init);
		buttons.add(step);
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttons.add(exit);
		add(buttons,BorderLayout.WEST);
		add(jp, BorderLayout.CENTER);
	}
	
}
