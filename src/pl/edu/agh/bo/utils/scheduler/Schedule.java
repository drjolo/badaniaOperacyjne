package pl.edu.agh.bo.utils.scheduler;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.bo.cockroach.Evaluatable;
import pl.edu.agh.bo.cockroach.PermutationVector;

public class Schedule implements Evaluatable {
	List <Item> items;
	List <Integer> order;
	List <Integer> machineCount;
	
	public Schedule() {
		this.items = new ArrayList<Item>();
		this.order = new ArrayList<Integer>();
		this.machineCount = new ArrayList<Integer>();
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
			increment(getIncrementTime());
		}
		return null;
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
