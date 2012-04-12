package pl.edu.agh.bo.ui;


import javax.swing.JFrame;

import pl.edu.agh.bo.ui.scheduler.SchedulerPanel;
import pl.edu.agh.bo.utils.scheduler.Schedule;

@SuppressWarnings({ "serial", "unused" })
public class UiScheduler extends JFrame{
	@SuppressWarnings("deprecation")
	public UiScheduler() {
		super("Scheduling Algorithm");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new SchedulerPanel());
		this.pack();
		this.resize(600, 600);
		this.setVisible(true);
	}
}