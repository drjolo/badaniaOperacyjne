package pl.edu.agh.bo.ui.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.ui.widgets.ScatterPlot;
import pl.edu.agh.bo.utils.salesman.Extremes;
import pl.edu.agh.bo.utils.salesman.Point;
import pl.edu.agh.bo.utils.salesman.Road;
import pl.edu.agh.bo.utils.salesman.TravellingSalesman;

import java.util.*;

/**
 * @author pietrekder
 */

@SuppressWarnings("unused")
public class WinMain
{
	private static DrawPanel drpn;
	private static Road road;
	private static int drawopt = 0;
	
	public WinMain()
	{
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
       //new points

        road = new Road("resources/mapaPolski.txt");
		TravellingSalesman tso = new TravellingSalesman(road);
		CockroachSwarm tsoCockroach = new CockroachSwarm(1, 1, tso, 3, false, true, true, 5, 100);
		long time = 2 * 1000;
		tsoCockroach.run(time);
		road.setOrder(tsoCockroach.getSolution().getPermutation());
      
		JFrame frame = new JFrame("Route Path");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AddComponents( frame.getContentPane() );
		
		frame.pack();
		frame.setSize(640, 480);
		frame.setMinimumSize( new Dimension( 320, 240 ) );
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void AddComponents( Container pane )
	{
		if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
		
		//Top Panel with few checkbox
		JPanel topPanel = new JPanel( new GridLayout() );
		
		//Create DrawPanel and initialize sample points
		drpn = new DrawPanel();
		drpn.SetRoad( road );
		
		//Checkboxes
		final JCheckBox cbox_allpaths = new JCheckBox( "Draw All Paths" );
		final JCheckBox cbox_drawpoints = new JCheckBox( "Draw Points" );
		final JCheckBox cbox_drawshadowpath = new JCheckBox( "Draw Shadowed Path" );
		final JCheckBox cbox_drawsimplepath = new JCheckBox( "Draw Cycle Path" );
		final JCheckBox cbox_drawcaptions = new JCheckBox( "Show Captions" );
		
		drawopt |= DRAWOPT.POINTS;
		cbox_drawpoints.setSelected( true );
		
		//ItemListener for checkboxes
		ItemListener itlistener = new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				if( e.getSource() == cbox_allpaths )
				{
					if( e.getStateChange() == ItemEvent.SELECTED )
						drawopt |= DRAWOPT.ALLPATHS;
					else
						drawopt &= ~DRAWOPT.ALLPATHS;
				}
				if( e.getSource() == cbox_drawpoints )
				{
					if( e.getStateChange() == ItemEvent.SELECTED )
						drawopt |= DRAWOPT.POINTS;
					else
						drawopt &= ~DRAWOPT.POINTS;
				}
				if( e.getSource() == cbox_drawshadowpath )
				{
					if( e.getStateChange() == ItemEvent.SELECTED )
						drawopt |= DRAWOPT.SHADOWPATH;
					else
						drawopt &= ~DRAWOPT.SHADOWPATH;
				}
				if( e.getSource() == cbox_drawsimplepath )
				{
					if( e.getStateChange() == ItemEvent.SELECTED )
						drawopt |= DRAWOPT.CYCLEPATH;
					else
						drawopt &= ~DRAWOPT.CYCLEPATH;
				}
				if( e.getSource() == cbox_drawcaptions )
				{
					if( e.getStateChange() == ItemEvent.SELECTED )
						drawopt |= DRAWOPT.CAPTIONS;
					else
						drawopt &= ~DRAWOPT.CAPTIONS;
				}
				
				drpn.SetDrawOptions( drawopt );
				drpn.repaint();
			}
		};
		
		//connect ItemListener to checkboxes
		cbox_allpaths.addItemListener( itlistener );
		cbox_drawpoints.addItemListener( itlistener );
		cbox_drawshadowpath.addItemListener( itlistener );
		cbox_drawsimplepath.addItemListener( itlistener );
		cbox_drawcaptions.addItemListener( itlistener );
		
		//Add items to TopPanel
//		topPanel.add( cbox_drawsimplepath );
//		topPanel.add( cbox_allpaths );
		topPanel.add( cbox_drawpoints );
		topPanel.add( cbox_drawshadowpath );
		topPanel.add( cbox_drawcaptions );
		
		pane.add( topPanel, BorderLayout.PAGE_START );
		pane.add( drpn, BorderLayout.CENTER );
		
		drpn.SetDrawOptions( DRAWOPT.POINTS );
		drpn.repaint();
	}
}

@SuppressWarnings("serial")
class DrawPanel extends JPanel
{
	private int drawoptions = 0;
	private Road road;
	
	public void SetRoad( Road _road )
	{
		road = _road;
	}
	
	public void SetDrawOptions( int dr_opt )
	{
		drawoptions = dr_opt;
	}
	
	//=========================================================================
	//
	//=========================================================================
	@Override
	public void paintComponent( Graphics g )
	{
		super.paintComponent(g);		
		Graphics2D g2d = (Graphics2D)g;
		
		Dimension size = getSize();
		Insets insets = getInsets();
		
		int w = size.width - insets.left - insets.right - GLOBAL.DRAWAREA_OFFSET * 2;
		int h = size.height - insets.top - insets.bottom - GLOBAL.DRAWAREA_OFFSET * 2;

		Extremes extremes = road.getExtremes();
		
		double px, py, cx, cy;
		
		Point previousPoint = road.getPoints().get(road.getOrder().get(road.getSize() - 1));
        for(Integer index : road.getOrder())
        {
        	Point currentPoint = road.getPoints().get(index);
        	
        	px = (previousPoint.getX() - extremes.getMinx()) / (extremes.getMaxx() - extremes.getMinx()) * (w - GLOBAL.DRAWAREA_OFFSET) + GLOBAL.DRAWAREA_OFFSET/2 ;
        	py = h - (previousPoint.getY() - extremes.getMiny()) / (extremes.getMaxy() - extremes.getMiny()) * (h - GLOBAL.DRAWAREA_OFFSET) - GLOBAL.DRAWAREA_OFFSET/2;
        	cx = (currentPoint.getX() - extremes.getMinx()) / (extremes.getMaxx() - extremes.getMinx()) * (w - GLOBAL.DRAWAREA_OFFSET) + GLOBAL.DRAWAREA_OFFSET/2;
        	cy = h - (currentPoint.getY() - extremes.getMiny()) / (extremes.getMaxy() - extremes.getMiny()) * (h - GLOBAL.DRAWAREA_OFFSET) - GLOBAL.DRAWAREA_OFFSET/2;
        	
        	if( (drawoptions & DRAWOPT.SHADOWPATH) != 0 )
        		g2d.setColor( Color.LIGHT_GRAY );
        	Line2D line = new Line2D.Double( px, py, cx, cy );
        	g2d.draw(line);
        	
        	if( (drawoptions & DRAWOPT.SHADOWPATH) != 0 )
        		g2d.setColor( Color.BLACK );
        	
        	if( (drawoptions & DRAWOPT.CAPTIONS) != 0 )
        	{
        		g2d.drawString( currentPoint.getName(), (int)cx, (int)cy - GLOBAL.RADIUS );
        	}
        	        	
        	if( (drawoptions & DRAWOPT.POINTS) != 0 )
        	{
        		g2d.setColor( Color.RED );        		
        		g2d.fillRoundRect( (int)cx - GLOBAL.RADIUS, (int)cy - GLOBAL.RADIUS, GLOBAL.DIAMETER, GLOBAL.DIAMETER, GLOBAL.DIAMETER, GLOBAL.DIAMETER );        		
        		g2d.setColor( Color.BLACK );
        	}
        	
        	previousPoint = currentPoint;
        }
	}
}

class DRAWOPT
{
	public static final int ALLPATHS	= (1<<0);
	public static final int POINTS		= (1<<1);
	public static final int SHADOWPATH	= (1<<2);
	public static final int CYCLEPATH	= (1<<3);
	public static final int CAPTIONS	= (1<<4);
}

class GLOBAL
{
	//point parameters
	public static final int RADIUS		= 5;
	public static final int DIAMETER	= 2*RADIUS;
	
	//path drawing area parameters
	public static final int DRAWAREA_OFFSET = 40;
	public static final int __TEST_POINTS_COUNT = 30;
	public static final int _DEBUG = 0;
}







