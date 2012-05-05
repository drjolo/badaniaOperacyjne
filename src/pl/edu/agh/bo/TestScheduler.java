package pl.edu.agh.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.cockroach.PermutationVector;
import pl.edu.agh.bo.utils.salesman.Road;
import pl.edu.agh.bo.utils.salesman.TravellingSalesman;
import pl.edu.agh.bo.utils.scheduler.Item;
import pl.edu.agh.bo.utils.scheduler.Schedule;

import junit.framework.TestCase;

public class TestScheduler extends TestCase {
	
	private static final Logger logger = Logger.getLogger(TestScheduler.class);
	
	public void test () throws IOException {
		PropertyConfigurator.configure("lib/log4j.properties");
		logger.trace("Running test");
		//Schedule schedule = new Schedule("resources/tools.txt");
		//Schedule schedule = new Schedule("resources/schedulerTest.txt");
		Schedule schedule = new Schedule("resources/schedulerTest2.txt");
		logger.trace("Loaded");
		logger.trace("Time: " + schedule.evaluate(new PermutationVector(schedule.size())));
		logger.trace("Evaluated");
		CockroachSwarm schCockroach = new CockroachSwarm(1, 1, schedule, 1, false);
		schCockroach.run(1000);
		logger.trace("Time: " + schedule.evaluate(schCockroach.getSolution()));

	}
}
