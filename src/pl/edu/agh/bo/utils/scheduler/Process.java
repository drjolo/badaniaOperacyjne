package pl.edu.agh.bo.utils.scheduler;

public class Process {
	private double processingTime;
	private String name;
	
	public Process (double time) {
		this.processingTime = time;
	}
	public Process (double time, String name) {
		this(time);
		this.name = name;
	}
	
	
	public double getProcessingTime() {
		return processingTime;
	}
	public void setProcessingTime(double processingTime) {
		this.processingTime = processingTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
	
}
