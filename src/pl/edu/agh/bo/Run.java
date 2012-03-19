package pl.edu.agh.bo;

import javax.swing.SwingUtilities;

import pl.edu.agh.bo.ui.UiBinder;

public class Run {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				UiBinder ui = new UiBinder();
			}
		});
	}
}
