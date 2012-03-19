package pl.edu.agh.bo.ui.salesman;

import javax.swing.JLabel;
import javax.swing.JPanel;

import pl.edu.agh.bo.utils.salesman.Road;

/**
 * @author marpiech
 * TODO: (pietrekder) dopisac sie do listy autorow
 */
@SuppressWarnings("serial")
public class SalesmanPanel extends JPanel {
	Road road;
	public SalesmanPanel () {
		road = new Road();
		//TODO: (pietrekder) umiescic w tym panelu obrazek wizualizujacy droge
		this.add(new JLabel("Tu powinna byc grafika dla komiwojazera"));
	}
}
