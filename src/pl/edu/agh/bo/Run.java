package pl.edu.agh.bo;


import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.bo.ui.UiSalesman;
import pl.edu.agh.bo.ui.UiScheduler;

public class Run {
	public static void main(String[] args) {
		PropertyConfigurator.configure("lib/log4j.properties");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//@SuppressWarnings("unused")
				//UiSalesman ui = new UiSalesman();
				@SuppressWarnings("unused")
				UiScheduler ui2 = new UiScheduler();
				
			}
		});
	}
}
