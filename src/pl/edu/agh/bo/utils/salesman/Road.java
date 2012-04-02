package pl.edu.agh.bo.utils.salesman;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marpiech
 * @author bblazewicz
 * 
 */
public class Road {
	List<Point> points;
	List<Integer> order;
	
	public Road() {
		points = new ArrayList<Point>();
		order = new ArrayList<Integer>();
		
		// Mock
		Point point1 = new Point(0.0, 0.0);
		points.add(point1);
		Point point2 = new Point(1.0, 1.0);
		points.add(point2);
		order.add(1);
		order.add(2);
	}

	public Double distance() {
		double distance = 0;
		for (int i=0; i < this.points.size(); i++) {
			distance += Math.pow(Math.pow(this.points.get(i).getX() - this.points.get(i+1).getX(), 2) + Math.pow(this.points.get(i).getY() - this.points.get(i+1).getY(), 2), 0.5);
		}
		return distance;
	}
	
}
