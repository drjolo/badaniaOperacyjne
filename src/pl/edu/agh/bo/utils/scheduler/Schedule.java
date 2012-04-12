package pl.edu.agh.bo.utils.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.TestScheduler;
import pl.edu.agh.bo.cockroach.Evaluatable;
import pl.edu.agh.bo.cockroach.PermutationVector;

public class Schedule implements Evaluatable {
	List <Item> items;
	List <Integer> order;
	List <Integer> machineCount;
	private static final Logger logger = Logger.getLogger(Schedule.class);
	
	public Schedule(List<Item> items, List <Integer> machineCount) {
		this.items = items;
		this.order = new PermutationVector(items.size()).getPermutation();
		this.machineCount = machineCount;
	}
	
	private Double process() throws ItemProblemException {
		Double processingTime = 0.0;
		Integer currentItem = 0;
		items.get(order.get(currentItem)).run();
		while(!isEverythingFinished()) {
			while(isFirstMachineFree() && currentItem < this.size() - 1) {
				currentItem++;
				items.get(order.get(currentItem)).run();
			}
			Double incrementTime = getIncrementTime();
			logger.debug("INC: " + incrementTime);
			processingTime += incrementTime;
			increment(incrementTime);
		}
		return processingTime;
	}
	
	void increment(double time) {
		for(Item item : items)
			if(!item.isFinished() && item.isProcessed())
				item.process(time);
	}
	
	double getIncrementTime() throws ItemProblemException {
		double incrementTime = 100000.0;
		for(Item item : items) {
			if(!item.isFinished() && item.isProcessed())
				if(item.getTimeToFinishCurrentProcess() < incrementTime)
					incrementTime = item.getTimeToFinishCurrentProcess();
		}
		return incrementTime;
	}
	
	boolean isFirstMachineFree() {
		Integer numberOfItemsProcessedByFirstMachine = 0;
		for(Item item : items) {
			if(!item.isFinished && item.isProcessed && item.isProcessingByFirstMachine())
				numberOfItemsProcessedByFirstMachine++;
		}
		if(numberOfItemsProcessedByFirstMachine < machineCount.get(0))
			return true;
		else
			return false;
	}
	
	boolean isEverythingFinished() {
		for(Item item : items) {
			if(!item.isFinished)
				return false;
		}
		return true;
	}
	
	@Override
	public Double evaluate(PermutationVector permutation) {
		order = permutation.getPermutation();
		try {
			return process();
		} catch (ItemProblemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1.0;
	}

	@Override
	public int size() {
		return items.size();
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Integer> getOrder() {
		return order;
	}

	public void setOrder(List<Integer> order) {
		this.order = order;
	}

	public List<Integer> getMachineCount() {
		return machineCount;
	}

	public void setMachineCount(List<Integer> machineCount) {
		this.machineCount = machineCount;
	}
}
