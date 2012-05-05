package pl.edu.agh.bo.utils.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Item {
	private static final Logger logger = Logger.getLogger(Item.class);
	private String name;
	Map <String, Integer> process;
	List <String> machineTypes;
	ProductionStatus status = new ProductionStatus();
		
	public Item (Map <String, Integer> process, List <String> machineTypes, String name) {
		this.process = process;
		this.machineTypes = machineTypes;
		this.name = name;
	}

	public boolean isFinished() {
		return status.isFinished;
	}

	public boolean isCurrentlyProcessed() {
		return status.isCurrentlyProcessed;
	}

	public int nextMachineID() {
		return status.currentProcess;
	}

	public int timeToFinish() {
		return process.get(machineTypes.get(status.currentProcess));
	}

	public void finishTask() {
		status.currentProcess++;
		status.isCurrentlyProcessed = false;
		if(status.currentProcess == machineTypes.size())
			status.isFinished = true;
		if(!status.isFinished && timeToFinish() == 0)
			this.finishTask();
	}

	public String getName() {
		return name;
	}

	public void setForProcessing() {
		status.isCurrentlyProcessed = true;
	}

	public void reset() {
		status = new ProductionStatus();
	}

	public int getCurrentProcess() {
		return status.currentProcess + 1;
	}
	
	
/*
	public void run() {
		isProcessed = true;
	}
	dfd
	public void process(double time) {
		this.productionStatus.process(time, processSchedule.get(this.productionStatus.getProcess()).getProcessingTime());
		if (this.productionStatus.getProcess() >= this.processSchedule.size()) {
			isProcessed = false;
			isFinished = true;
		}
	}
	
	public double getTimeToFinishCurrentProcess() throws ItemProblemException {
		logger.trace("Function: TimeToFinish");
		if (!isProcessed || isFinished) {
			throw (new ItemProblemException());
		}
		logger.trace("Time, current process: " + this.productionStatus.getProcess());
		logger.trace("Time, current processing time: " + this.processSchedule.get(this.productionStatus.getProcess()).getProcessingTime());
		logger.trace("Time, current time: " + this.productionStatus.getTime());
		return this.processSchedule.get(this.productionStatus.getProcess()).getProcessingTime() - this.productionStatus.getTime();
	}
	
	public Boolean isProcessingByFirstMachine() {
		if (this.productionStatus.getProcess() == 0)
			return true;
		else
			return false;
	}
	
	public Boolean isProcessed() {
		return isProcessed;
	}

	public Boolean isFinished() {
		return isFinished;
	}*/
}


class ProductionStatus {
	private static final Logger logger = Logger.getLogger(ProductionStatus.class);
	boolean isCurrentlyProcessed = false;
	boolean isFinished = false;
	int currentProcess = 0;
}
