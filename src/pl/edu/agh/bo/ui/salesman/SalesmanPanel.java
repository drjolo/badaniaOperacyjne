package pl.edu.agh.bo.ui.salesman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.utils.salesman.Extremes;
import pl.edu.agh.bo.utils.salesman.Point;
import pl.edu.agh.bo.utils.salesman.Road;
import pl.edu.agh.bo.utils.salesman.TravellingSalesman;

/**
 * @author marpiech
 * TODO: (pietrekder) dopisac sie do listy autorow
 */
@SuppressWarnings("serial")
public class SalesmanPanel extends JPanel {
	
	Road road;
    Graphics2D device;
	Integer ysize;
	Integer xsize;
	Extremes extremes;
	int width = 400;
	int height = 400;
	
	public SalesmanPanel () {
		this.road = new Road("resources/mapaPolski.txt");
		TravellingSalesman tso = new TravellingSalesman(road);
		CockroachSwarm tsoCockroach = new CockroachSwarm(100, 4, tso, 1);
		tsoCockroach.run(100);
		road.setOrder(tsoCockroach.getSolution().getPermutation());
		extremes = road.getExtremes();
		setPreferredSize(new Dimension(width, height));
		
	}
	
	@Override  
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        Graphics2D g2d = (Graphics2D) g;  
        
        Point previousPoint = road.getPoints().get(road.getOrder().get(road.getSize() - 1));
        for(Integer index : road.getOrder()) {
        	Point currentPoint = road.getPoints().get(index);
        	Line2D line = new Line2D.Double(getX(previousPoint), 
        			getY(previousPoint), getX(currentPoint), getY(currentPoint));
        	g2d.draw(line);
        	if (road.getSize() < 20)
        		g2d.drawString(currentPoint.getName(), (int) getX(currentPoint), (int) getY(currentPoint));
        	previousPoint = currentPoint;
        }
    }
	
	double getX(Point point) {
		return (point.getX() - extremes.getMinx()) / (extremes.getMaxx() - extremes.getMinx()) * (width - 100) + 50;
	}
	double getY(Point point) {
		return height - (point.getY() - extremes.getMiny()) / (extremes.getMaxy() - extremes.getMiny()) * (height - 100) - 50;
	}
}

