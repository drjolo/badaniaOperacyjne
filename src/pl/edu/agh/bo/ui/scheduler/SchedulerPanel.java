package pl.edu.agh.bo.ui.scheduler;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import net.miginfocom.swing.MigLayout;

/**
 * @author marpiech
 * @author bblazewicz
 */
@SuppressWarnings("serial")
public class SchedulerPanel extends JPanel {

	public SchedulerPanel () {
		//setLayout(new MigLayout("", "[][231px,grow][]", "[26.00px][grow][26.00]"));
		
		JLabel label = new JLabel("Tu powinna byc grafika dla harmonogramowania");
		this.add(label, "cell 1 0,alignx center,aligny top");
		
		//JLabel label_pict = new JLabel(new ImageIcon(SchedulerPanel.class.getResource("/scheduler.jpg")));
		//this.add(label_pict, "cell 1 1,alignx center,aligny center");
		
		
	}
}
