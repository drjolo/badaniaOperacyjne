package pl.edu.agh.bo.ui;

import javax.swing.JFrame;

import pl.edu.agh.bo.ui.salesman.SalesmanPanel;

@SuppressWarnings("serial")
public class UiScheduler extends JFrame{
	@SuppressWarnings("deprecation")
	public UiScheduler() {
		super("Cocroach Swarm Algorithm");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new SalesmanPanel());
		this.pack();
		this.resize(600, 300);
		this.setVisible(true);
	}
}