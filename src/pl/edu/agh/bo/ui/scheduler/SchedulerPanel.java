package pl.edu.agh.bo.ui.scheduler;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.TestScheduler;
import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.cockroach.PermutationVector;
import pl.edu.agh.bo.utils.scheduler.Item;
import pl.edu.agh.bo.utils.scheduler.Schedule;
import net.miginfocom.swing.MigLayout;

/**
 * @author marpiech
 * @author bblazewicz
 */
@SuppressWarnings("serial")
public class SchedulerPanel extends JPanel {
	private static final Logger logger = Logger.getLogger(SchedulerPanel.class);
	public SchedulerPanel () {
		
		Schedule schedule;
		try {
			schedule = new Schedule("resources/schedulerTest.txt");
			logger.trace("Time: " + schedule.evaluate(new PermutationVector(schedule.size())));
			CockroachSwarm schCockroach = new CockroachSwarm(1, 1, schedule, 1, false);
			//schCockroach.run(1000);
			Map <Item, List<Integer>> vis = schedule.getMapForVisualization(schCockroach.getSolution());
			
			// Printowanie rozwiazania dla kolejnych dyskretnych jednostek czasu; 0 = brak urzadzenia
			// TODO: trzeba to narysowac
			for(Item item : vis.keySet()) {
				System.out.print(item.getName() + "\t");
				for(Integer process : vis.get(item)) {
					System.out.print(process + "\t");
				}
				System.out.println();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		setLayout(new MigLayout("", "[][231px,grow][]", "[26.00px][grow][26.00]"));
		
		JLabel label = new JLabel("Tu powinna byc grafika dla harmonogramowania");
		this.add(label, "cell 1 0,alignx center,aligny top");
		
		JLabel label_pict = new JLabel(new ImageIcon(SchedulerPanel.class.getResource("/scheduler.jpg")));
		this.add(label_pict, "cell 1 1,alignx center,aligny center");
		
		
	}
}
