package pl.edu.agh.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.bo.cockroach.PermutationVector;
import pl.edu.agh.bo.utils.scheduler.Item;
import pl.edu.agh.bo.utils.scheduler.Schedule;

import junit.framework.TestCase;

public class TestScheduler extends TestCase {
	
	private static final Logger logger = Logger.getLogger(TestScheduler.class);
	
	public void test () {
		PropertyConfigurator.configure("lib/log4j.properties");
		logger.trace("Running test");
		List <Double> processingTimes1 = new ArrayList <Double>();
		processingTimes1.add(1.0);
		processingTimes1.add(3.0);
		Item item1 = new Item(processingTimes1);
		List <Double> processingTimes2 = new ArrayList <Double>();
		processingTimes2.add(1.0);
		processingTimes2.add(3.0);
		Item item2 = new Item(processingTimes2);		
		List <Item> items = new ArrayList <Item> ();
		items.add(item1);
		items.add(item2);
		List <Integer> machineCount = new ArrayList <Integer> ();
		machineCount.add(1);
		machineCount.add(1);
		Schedule schedule = new Schedule(items, machineCount);
		logger.trace("Time: " + schedule.evaluate(new PermutationVector(2)));
	}
}
