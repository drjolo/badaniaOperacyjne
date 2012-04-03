package pl.edu.agh.bo.utils.salesman;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.Test;

/**
 * @author marpiech
 * @author bblazewicz
 * 
 */
public class Road {
	List<Point> points;
	List<Integer> order;
	private static final Logger logger = Logger.getLogger(Road.class);
	
	public Road(String file) {
		this.points = new ArrayList<Point>();
		this.order = new ArrayList<Integer>();
		try {
			this.readPointsFromFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Double distance() {
		double distance = 0;
		for (int i=0; i < (this.getSize() - 1); i++) {
			//logger.debug("Distance i: " + i + " Order(i): " + order.get(i) + " Order(i + 1): " + order.get(i + 1) );
			distance += points.get(order.get(i)).distance(points.get(order.get(i + 1))); 
			//Math.pow(Math.pow(this.points.get(i).getX() - this.points.get(i+1).getX(), 2) + Math.pow(this.points.get(i).getY() - this.points.get(i+1).getY(), 2), 0.5);
		}
		// Do poczatku
		distance += points.get(order.get(this.getSize() - 1)).distance(points.get(order.get(0)));
		return distance;
	}
	
	public void readPointsFromFile (String file) throws IOException {
		  BufferedReader br = new BufferedReader(
				  new InputStreamReader(
						  new DataInputStream(
								  new FileInputStream(file))));
		  String strLine;
		  Integer orderCounter = 0;
		  // TODO: (pigmaster) uodpornic tą funckje na dziwne przypadki, typu pusta linia, nowe znaki
		  while ((strLine = br.readLine()) != null)   {
			  String[] split = strLine.split("\t");
			  points.add(new Point(new Double(split[0]), new Double(split[1]), split[2]));
			  order.add(orderCounter++);
		  }
		  br.close();
		  		
	}
	
	public void setOrder (List <Integer> order) {
		this.order = order;
	}
	
	@Override
	public String toString() {
		String out = "";
		for(Integer which : order)
			out += points.get(which).getName().concat(" - ");
		return out;
	}
	
	public int getSize() {
		return this.points.size();
	}
}