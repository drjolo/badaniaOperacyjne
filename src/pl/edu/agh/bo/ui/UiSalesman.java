package pl.edu.agh.bo.ui;

import javax.swing.*;

import pl.edu.agh.bo.ui.salesman.SalesmanPanel;

@SuppressWarnings("serial")
public class UiSalesman extends JFrame{
	@SuppressWarnings("deprecation")
	public UiSalesman() {
		super("Cocroach Swarm Algorithm");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new SalesmanPanel());
		this.pack();
		this.resize(600, 300);
		this.setVisible(true);
	}
}
