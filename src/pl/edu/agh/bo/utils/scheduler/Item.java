package pl.edu.agh.bo.utils.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Item {
	List <Process> processSchedule;
	ProductionStatus productionStatus;
	Boolean isProcessed = false;
	Boolean isFinished = false;
	
	public Item (List <Double> processingTimes) {
		processSchedule = new ArrayList <Process> ();
		for(Double processingTime : processingTimes) {
			processSchedule.add(new Process(processingTime));
		}
	}

	public void run() {
		isProcessed = true;
	}
	
	public void process(double time) {
		this.productionStatus.process(time, processSchedule.get(this.productionStatus.getProcess()).getProcessingTime());
		if (this.productionStatus.getProcess() >= this.processSchedule.size()) {
			isProcessed = false;
			isFinished = true;
		}
	}
	
	public double getTimeToFinishCurrentProcess() throws ItemProblemException {
		if (!isProcessed || isFinished) {
			throw (new ItemProblemException());
		}
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
	}
}

class ProductionStatus {
	Integer process;
	Double time;
	ProductionStatus () {
		this.process = 0;
		this.time = 0.0;
	}
	public void process(Double time, Double maxTime) {
		this.time += time;
		if (this.time >= maxTime) {
			time = this.time - maxTime;
			this.process++;
		}
	}
	public Integer getProcess() {
		return process;
	}
	public void setProcess(Integer process) {
		this.process = process;
	}
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
	}
}
