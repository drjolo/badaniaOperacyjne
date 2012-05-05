package pl.edu.agh.bo.utils.scheduler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import pl.edu.agh.bo.TestScheduler;
import pl.edu.agh.bo.cockroach.Evaluatable;
import pl.edu.agh.bo.cockroach.PermutationVector;
import pl.edu.agh.bo.utils.salesman.Point;

public class Schedule implements Evaluatable {
	List<Item> items;
	List<Integer> order;
	List<Machine> machines;
	List<String> machineTypes;
	Map<Item, List<Integer>> vis;
	private static final Logger logger = Logger.getLogger(Schedule.class);

	public Schedule(String file) throws IOException {
		this.machines = new ArrayList<Machine>();
		this.machineTypes = new ArrayList<String>();
		this.items = new ArrayList<Item>();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new DataInputStream(new FileInputStream(file))));
		String strLine = br.readLine();
		String[] machineDescriptions = strLine.split("\t");
		if (machineDescriptions.length > 1) {
			for (String machineDescription : machineDescriptions) {
				String[] descriptionElements = machineDescription.split("\\[");
				String type = descriptionElements[0];
				if (!type.equals("Nazwa") && type.length() > 2) {
					machineTypes.add(type);
					Integer number = new Integer(
							descriptionElements[1].split("\\]")[0]);
					for (int iterate = 0; iterate < number; iterate++)
						machines.add(new Machine(type));
				}
			}
			while ((strLine = br.readLine()) != null) {
				if (strLine.trim().length() == 0) {
					continue;
				} else {
					String[] itemDescription = strLine.split("\t");
					String name = itemDescription[0];
					Iterator<String> type = this.machineTypes.iterator();
					Map<String, Integer> process = new TreeMap<String, Integer>();
					for (String element : itemDescription) {
						try {
							Integer number = new Integer(element);
							process.put(type.next(), number);
						} catch (Exception e) {
							// System.out.print("Blad");
						}
					}
					items.add(new Item(process, machineTypes, name));
				}
			}
		}
		br.close();
		this.order = new ArrayList<Integer>();
		for (int i = 0; i < items.size(); i++)
			order.add(i);
	}

	@Override
	public Double evaluate(PermutationVector permutation) {
		order = permutation.getPermutation();
		return process().doubleValue();
	}

	@Override
	public int size() {
		return items.size();
	}

	private Integer process() {
		Integer processingTime = 0;
		while (!isEverythingFinished()) {
			//logger.debug("CurrentTime: " + processingTime);
			for (Machine machine : machines) {
				if (!machine.isBusy()) { // Jesli maszyna pusta to szukaj itemu, ktory mozna by na niej procesowac
					int machineId = machine.getId(machineTypes);
					//logger.debug("Time: " + processingTime + " FreeMachine: " + machine.getType());
					for(Integer which : order) {
						Item item = items.get(which);
						if(!item.isCurrentlyProcessed() && !item.isFinished() && item.nextMachineID() == machineId) {
							machine.setItem(item);
							//logger.debug("Item: " + item.getName());
							break;
						}
					}
					//logger.debug("Taken: " + machine.isBusy());
				}
			}
			for (Machine machine : machines)
				if(machine.isBusy())
					machine.run();
			processingTime++;
		}
		this.reset();
		return processingTime;
	}
	public Map<Item, List<Integer>> getMapForVisualization(PermutationVector permutation) {
		order = permutation.getPermutation();
		return vis();
	}
	private Map<Item, List<Integer>> vis() {
		Map<Item, List<Integer>> vis = new HashMap<Item, List<Integer>>();
		for(Item item : items)
			vis.put(item, new ArrayList<Integer>());
		Integer processingTime = 0;
		while (!isEverythingFinished()) {
			for (Machine machine : machines) {
				if (!machine.isBusy()) { // Jesli maszyna pusta to szukaj itemu, ktory mozna by na niej procesowac
					int machineId = machine.getId(machineTypes);
					for(Integer which : order) {
						Item item = items.get(which);
						if(!item.isCurrentlyProcessed() && !item.isFinished() && item.nextMachineID() == machineId) {
							machine.setItem(item);
							break;
						}
					}

				}
			}
			for (Item item : items) {
				int numberToPut = 0;
				if(item.isCurrentlyProcessed())
					numberToPut = item.getCurrentProcess();
				vis.get(item).add(numberToPut);
			}
			for (Machine machine : machines)
				if(machine.isBusy())
					machine.run();
			processingTime++;
		}
		this.reset();
		return vis;
	}
	
	void reset() {
		for(Item item : items) {
			item.reset();
		}
	}
	
	boolean isEverythingFinished() {
		for (Item item : items) {
			if (!item.isFinished())
				return false;
		}
		return true;
	}

	/*
	 * 
	 * private Double process() throws ItemProblemException { Double
	 * processingTime = 0.0; Integer currentItem = 0;
	 * items.get(order.get(currentItem)).run(); while(!isEverythingFinished()) {
	 * while(isFirstMachineFree() && currentItem < this.size() - 1) {
	 * currentItem++; items.get(order.get(currentItem)).run(); } Double
	 * incrementTime = getIncrementTime(); logger.debug("INC: " +
	 * incrementTime); processingTime += incrementTime;
	 * increment(incrementTime); } return processingTime; }
	 * 
	 * void increment(double time) { for(Item item : items)
	 * if(!item.isFinished() && item.isProcessed()) item.process(time); }
	 * 
	 * double getIncrementTime() throws ItemProblemException { double
	 * incrementTime = 100000.0; for(Item item : items) { if(!item.isFinished()
	 * && item.isProcessed()) if(item.getTimeToFinishCurrentProcess() <
	 * incrementTime) incrementTime = item.getTimeToFinishCurrentProcess(); }
	 * return incrementTime; }
	 * 
	 * boolean isFirstMachineFree() { Integer
	 * numberOfItemsProcessedByFirstMachine = 0; for(Item item : items) {
	 * if(!item.isFinished && item.isProcessed &&
	 * item.isProcessingByFirstMachine())
	 * numberOfItemsProcessedByFirstMachine++; }
	 * if(numberOfItemsProcessedByFirstMachine < machineCount.get(0)) return
	 * true; else return false; }
	 * 
	 * 
	 * 
	 * 
	 * public List<Item> getItems() { return items; }
	 * 
	 * public void setItems(List<Item> items) { this.items = items; }
	 * 
	 * public List<Integer> getOrder() { return order; }
	 * 
	 * public void setOrder(List<Integer> order) { this.order = order; }
	 * 
	 * public List<Integer> getMachineCount() { return machineCount; }
	 * 
	 * public void setMachineCount(List<Integer> machineCount) {
	 * this.machineCount = machineCount; }
	 */
}
