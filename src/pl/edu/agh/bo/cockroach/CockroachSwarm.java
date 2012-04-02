package pl.edu.agh.bo.cockroach;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * @author marpiech
 * Cockroach Swarm Algorithm based on Cheng et al., 2011.
 */
public class CockroachSwarm {
	
	/**
	 * List of cockroaches
	 */
	private List <PermutationVector> cockroaches;
	
	/**
	 * List of foods
	 */
	private List <PermutationVector> foods;
	
	/**
	 * LOF Local Optimal Food
	 */
	private PermutationVector localOptimalFood;
	private Double LOFVal;
		
	/**
	 * Objective function
	 */
	private Evaluatable eval;
	
	private int splitDistance;
	
	private static final Logger logger = Logger.getLogger(CockroachSwarm.class);
	
	public CockroachSwarm (int populationSize, int foodSize, Evaluatable eval, int splitDistance) {
		this.eval = eval;
		this.splitDistance = splitDistance;
		this.stepOne(populationSize, foodSize);
	}
	
	public void run(int iterations) {
		logger.info("Iteration: 0, Val: " + LOFVal + ", " + localOptimalFood.toString());
		for(int i = 1; i <= iterations; i++) {
			stepTwo();
			stepThree();
			logger.info("Iteration: " + i + ", Val: " + LOFVal + ", " + localOptimalFood.toString());
		}
	}
	
	private void stepOne(int populationSize, int foodSize) {
		logger.debug("Eval size: " + eval.size());
		cockroaches = new ArrayList<PermutationVector>();
		for(int i = 0; i < populationSize; i++)
			cockroaches.add(new PermutationVector(eval.size(), true));
		foods = new ArrayList<PermutationVector>();
		for(int i = 0; i < foodSize; i++)
			foods.add(new PermutationVector(eval.size(), true));
		setNewLocalOptimalFood(foods.get(0));
		for(PermutationVector food : foods)
			compareAndSetLOF(food);		
	}
	
	private void stepTwo() {
		for(int foodIndex = 0; foodIndex < foods.size(); foodIndex++) {
			for(int cockroachIndex = 0; cockroachIndex < cockroaches.size(); cockroachIndex++) {
				boolean isCrawling = true;
				while(isCrawling) {
					compareAndSetLOF(cockroaches.get(cockroachIndex));
					isCrawling = cockroaches.get(cockroachIndex).crawlTo(foods.get(foodIndex));
				}
				cockroaches.get(cockroachIndex).permute();
			}
		}
	}
	
	private void stepThree() {
		for(int cockroachIndex = 0; cockroachIndex < cockroaches.size(); cockroachIndex++) {
			boolean isCrawling = true;
			while(isCrawling) {
				compareAndSetLOF(cockroaches.get(cockroachIndex));
				isCrawling = cockroaches.get(cockroachIndex).crawlTo(localOptimalFood);
			}
			cockroaches.get(cockroachIndex).permute();			
		}
	}
	
	private void foodSplitting() {
		for(int foodIndex = 1; foodIndex <= foods.size(); foodIndex++) {
			foods.get(foodIndex).inject(localOptimalFood);
			foods.get(foodIndex).nRStep(splitDistance);
		}
	}
	
	private void compareAndSetLOF(PermutationVector food) {
		if(LOFVal > eval.evaluate(food))
			setNewLocalOptimalFood(food);
	}
	
	private void setNewLocalOptimalFood(PermutationVector food) {
		localOptimalFood = food.copy();
		LOFVal = eval.evaluate(localOptimalFood);
		logger.info("New optimum: " + LOFVal + " " + food);
	}
	
}
