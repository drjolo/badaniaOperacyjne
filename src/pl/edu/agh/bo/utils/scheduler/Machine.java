package pl.edu.agh.bo.utils.scheduler;

import java.util.List;

import org.apache.log4j.Logger;

public class Machine {
	private static final Logger logger = Logger.getLogger(Machine.class);
	private String type;
	private boolean isBusy;
	private int timeToFinish;
	private Item item;
	
	public Machine(String type) {
		this.type = type;
		this.isBusy = false;
	}
	boolean isBusy() {
		return isBusy;
	}
	Integer getId(List <String> machineTypes) {
		return machineTypes.indexOf(this.type);
	}
	void setItem(Item item) {
		this.item = item;
		item.setForProcessing();
		this.timeToFinish = item.timeToFinish();
		this.isBusy = true;
	}
	void run() {
		//logger.debug("Machine: " + type + "; Item: " + item.getName() + "; Time:" + timeToFinish);
		timeToFinish--;
		if(timeToFinish < 1) {
			item.finishTask();
			isBusy = false;
		}
	}
	public String getType() {
		return type;
	}
}
