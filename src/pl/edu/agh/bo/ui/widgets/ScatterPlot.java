package pl.edu.agh.bo.ui.widgets;


import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;


/**
 * 
 * @author pietrekder
 *
 *	wymagane biblioteki:
 *
 *		jfreechart-1.0.14.jar
 *		junit.jar
 *		jcommon-1.0.17.jar
 *		iText-2.1.5.jar
 *		jfreechart-1.0.14-experimental.jar
 *
 *	Biblioteki dodam p�niej, jak w og�le ten ScatterPlot b�dzie wykorzystany, bo issue by� na closed?
 */

/**
 *	TODO: Implementowanie punkt�w z klasy GraphPoint to nie problem, ale nie wiem czy ma by� tylko jedna seria danych czy kilka
 */


@SuppressWarnings("serial")
public class ScatterPlot extends ApplicationFrame
{
	/*===============================================================
	 * 		MEMBERS
	 *===============================================================*/
	private XYSeries s1;
	private JFreeChart chart;
	
	public void setPoints(List <GraphPoint> points)
	{
		if( points.isEmpty() )
			return;
		
		s1.clear();
		
		for( int i=0; i<points.size(); i++ )
		{
			s1.add( (double)points.get(i).GetX(), (double)points.get(i).GetY() );
		}
	}
	
	public ScatterPlot()
	{
		super( "Wykres wartosci funkcji celu" );
		
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }catch (InstantiationException ex) {
            ex.printStackTrace();
        }catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        s1 = new XYSeries("Wykres wartosci funkcji celu", true, false);
        
        XYSeriesCollection dataset = new XYSeriesCollection( s1 );
      	
        //	Wykres punktowy:
        chart = ChartFactory.createScatterPlot("Function", "X", "Y", dataset, PlotOrientation.VERTICAL, false, false, false);
        
        //	Wykres liniowy:
        //chart = ChartFactory.createXYLineChart( "Function", "X", "Y", dataset, PlotOrientation.VERTICAL, false, false, false );
        
        
		ChartPanel chartPanel = new ChartPanel(chart);
		JPanel content = new JPanel(new BorderLayout());
        content.add(chartPanel, BorderLayout.CENTER );
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        
        setContentPane(content);
        
        pack();
        setSize( 1280, 720 );
        setMinimumSize( new Dimension( 640, 360 ) );
        setLocationRelativeTo( null );
        setVisible( true );
        
	}
}





































