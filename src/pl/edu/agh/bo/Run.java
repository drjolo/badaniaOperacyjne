package pl.edu.agh.bo;


import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.PropertyConfigurator;


import pl.edu.agh.bo.ui.UiSalesman;
import pl.edu.agh.bo.ui.UiScheduler;
import pl.edu.agh.bo.ui.salesman.WinMain;
import pl.edu.agh.bo.ui.widgets.ScatterPlot;
import pl.edu.agh.bo.ui.widgets.GraphPoint;

@SuppressWarnings("unused")
public class Run {
	public static void main(String[] args) {
		PropertyConfigurator.configure("lib/log4j.properties");
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
			//	UiScheduler ui2 = new UiScheduler();
			//	WinMain win = new WinMain();
			//	UiSalesman ui = new UiSalesman();
				ScatterPlot sp = new ScatterPlot();
				
				//Przyk³adowo wygenerowana lista punktów
				List <GraphPoint> pts = new ArrayList<GraphPoint>();				
				for(int i=0; i<20;i++)
					pts.add( new GraphPoint( i, (int)(Math.random()*20) ) );
				sp.setPoints( pts );

			}
		});
	}
}
