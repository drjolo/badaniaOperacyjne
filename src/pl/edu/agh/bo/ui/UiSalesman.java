package pl.edu.agh.bo.ui;

import java.awt.Dimension;

import javax.swing.*;

import pl.edu.agh.bo.ui.salesman.SalesmanPanel;

@SuppressWarnings("serial")
public class UiSalesman extends JFrame{
	public UiSalesman() {
		super("Cocroach Swarm Algorithm");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new SalesmanPanel());
		this.pack();
		this.setPreferredSize(new Dimension(600, 600));
		this.setVisible(true);
	}
}
