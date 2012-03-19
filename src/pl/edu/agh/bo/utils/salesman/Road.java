package pl.edu.agh.bo.utils.salesman;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marpiech
 * TODO: (bblazewicz) dopisac sie do listy autorow
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
		// TODO: (bblazewicz) zaimplementowac obliczanie dlugosci drogi
		return 1.0;
	}
	
}
