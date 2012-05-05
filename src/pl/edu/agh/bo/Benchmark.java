package pl.edu.agh.bo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.utils.salesman.Road;
import pl.edu.agh.bo.utils.salesman.TravellingSalesman;

public class Benchmark {
	private static final Logger logger = Logger.getLogger(Benchmark.class);
	public static void main (String [] args) {
		PropertyConfigurator.configure("lib/log4j.properties");
		
		// resource
		Road road = new Road("resources/mapaPolski.txt");
		logger.trace("Road distance:" + road.distance());
		TravellingSalesman tso = new TravellingSalesman(road);
		
		// parameters
		int numberOfCocroaches = 5;
		int numberOfFoods = 5;
		int splitDistance = 5;
		boolean randomNest = true;
		boolean randomSplit = false;
		boolean localSplit = false;
		int invertStep = 0;
		int localSize = 10;
		
		int seconds = 10;
		
		//CockroachSwarm tsoCockroach = new CockroachSwarm(5, 5, tso, 5, true);
		CockroachSwarm tsoCockroach = new CockroachSwarm(numberOfCocroaches, 
				numberOfFoods, 
				tso, 
				splitDistance, 
				randomNest,
				randomSplit,
				localSplit,
				invertStep,
				localSize);
		long time = seconds * 1000;
		tsoCockroach.run(time);
		
		//int iterations = 1000;
		//tsoCockroach.run(iterations);
		road.setOrder(tsoCockroach.getSolution().getPermutation());
		logger.trace("Road distance:" + road.distance());
	}
}
