package pl.edu.agh.bo.ui.widgets;


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import java.lang.Math;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.experimental.chart.plot.CombinedXYPlot;


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
 *	TODO: Na razie generowane s� losowe punkty.
 *	TODO: Implementowanie punkt�w z klasy GraphPoint to nie problem, ale nie wiem czy ma by� tylko jedna seria danych czy kilka
 */


public class ScatterPlot
{
	public static void main( String[] argv )
	{
		SwingUtilities.invokeLater( new Runnable() {
			public void run()
			{
				new ScatterPlot();
			}
		});
	}
	
	public void setPoints(List <GraphPoint> points) {
		
	}
	
	public ScatterPlot()
	{
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
        
        JFrame frame = new JFrame( "Scatter Plot" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        AddComponents( frame.getContentPane() );
        
        frame.pack();
        frame.setSize( 1280, 720 );
        frame.setMinimumSize( new Dimension( 640, 360 ) );
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );
	}
	
	public void AddComponents( Container pane )
	{
		XYSeries s1 = new XYSeries("SERIA JEDEN", true, false);
		
		//RANDOM POINTS
		for (int i = 0; i < 50; i++)
			s1.add(Math.random() * 50, Math.random() * 100 );

		
		DefaultTableXYDataset xy_data = new DefaultTableXYDataset();
		xy_data.addSeries( s1 );
		XYItemRenderer xy_renderer = new DefaultXYItemRenderer();
		
		xy_renderer.setBaseToolTipGenerator( new StandardXYToolTipGenerator() );
		xy_renderer.setSeriesStroke( 0, new BasicStroke( 1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL ) );
		xy_renderer.setSeriesPaint( 0, Color.RED );
		
		ValueAxis x_axis = new NumberAxis( "iksy" );
		ValueAxis y_axis = new NumberAxis( "igreki" );
		
		XYPlot xy_plot = new XYPlot( xy_data, x_axis, y_axis, xy_renderer );

		CombinedXYPlot cplot = new CombinedXYPlot(x_axis, y_axis);
		cplot.add( xy_plot, 1 );
		cplot.setGap( 8.0f );
		
		JFreeChart chart = new JFreeChart( "Sample XY plot", JFreeChart.DEFAULT_TITLE_FONT, cplot, false );
		chart.setBackgroundPaint( Color.GRAY );
		LegendTitle legend = new LegendTitle(cplot);
        chart.addSubtitle(legend);
        
        JPanel panel = new ChartPanel( chart );
        pane.add( panel, BorderLayout.CENTER );
	}
}





































