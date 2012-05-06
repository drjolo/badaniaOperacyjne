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
	private boolean randomNest = true;
	
	boolean randomSplit = false;
	boolean localSplit = false;
	int invertStep = 0;
	int localSize = 10;
	
	
	private static final Logger logger = Logger.getLogger(CockroachSwarm.class);
	
	public CockroachSwarm (int populationSize, int foodSize, Evaluatable eval, int splitDistance) {
		this.eval = eval;
		this.splitDistance = splitDistance;
		this.stepOne(populationSize, foodSize);
	}
	
	/**
	 * @param populationSize ilosc karaluchow
	 * @param foodSize ilosc jedzenia
	 * @param eval problem musi implementowac interfejs evaluatable
	 * @param splitDistance rozrzut jedzenia
	 * @param randomNest czy karaluchy losowac (true) czy rozrzucac wokol rozwiazania (false)
	 */
	
	public CockroachSwarm (int populationSize, int foodSize, Evaluatable eval, int splitDistance, boolean randomNest) {
		this.eval = eval;
		this.splitDistance = splitDistance;
		this.randomNest = randomNest;
		this.stepOne(populationSize, foodSize);
	}	
	
	/**
	 * @param populationSize ilosc karaluchow
	 * @param foodSize ilosc jedzenia
	 * @param eval problem musi implementowac interfejs evaluatable
	 * @param splitDistance rozrzut jedzenia
	 * @param randomNest czy karaluchy losowac (true) czy rozrzucac wokol rozwiazania (false)
	 * @param randomSplit czy rozrzucac jedzenie w losowej odleglosc (max = splitDistance)
	 * @param localSplit czy karaluchy rozchodza sie lokalnie (split na wektorze permutacji blisko siebie)
	 * @param invertStep ile razy probowac redukowac osemki
	 */
	public CockroachSwarm (int populationSize, int foodSize, Evaluatable eval, int splitDistance,
			boolean randomNest, boolean randomSplit, boolean localSplit, int invertStep, int localSize) {
		this(populationSize, foodSize, eval, splitDistance, randomNest);
		this.randomSplit = randomSplit;
		this.localSplit = localSplit;
		this.invertStep = invertStep;
		this.localSize = localSize;
	}	
	
	
	public void run(int iterations) {
		logger.info("Iteration: 0, Val: " + LOFVal + ", " + localOptimalFood.toString());
		for(int i = 1; i <= iterations; i++) {
			stepTwo();
			stepThree();
			foodSplitting();
			invert();
			//logger.info("Iteration: " + i + ", Val: " + LOFVal + ", " + localOptimalFood.toString());
			logger.info("Iteration: " + i + ", Val: " + LOFVal);
		}
	}
	public void run(long time) {
		logger.info("Iteration: 0, Val: " + LOFVal + ", " + localOptimalFood.toString());
		long start = System.currentTimeMillis();
		int i = 0;
		while((System.currentTimeMillis() - start) < time) {
			stepTwo();
			stepThree();
			foodSplitting();
			invert();
			logger.info("Iteration: " + i++ + ", Val: " + LOFVal);
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
				if (randomNest)
					cockroaches.get(cockroachIndex).permute();
				else
					cockroaches.get(cockroachIndex).localNRStep(splitDistance, 10, randomSplit);
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
			if (randomNest)
				cockroaches.get(cockroachIndex).permute();
			else
				cockroaches.get(cockroachIndex).nRStep(splitDistance, randomSplit);
		}
	}
	
	private void foodSplitting() {
		for(int foodIndex = 0; foodIndex < foods.size(); foodIndex++) {
			foods.get(foodIndex).inject(localOptimalFood);
			foods.get(foodIndex).nRStep(splitDistance, randomSplit);
		}
	}
	
	private void invert() {
		for (int i = 0; i < this.invertStep; i++) {
			PermutationVector inverted = new PermutationVector(eval.size());
			inverted.inject(localOptimalFood);
			inverted.rInvert();
			compareAndSetLOF(inverted);
		}
	}
	
	private void compareAndSetLOF(PermutationVector food) {
		if(LOFVal > eval.evaluate(food))
			setNewLocalOptimalFood(food);
	}
	
	private void setNewLocalOptimalFood(PermutationVector food) {
		localOptimalFood = food.copy();
		LOFVal = eval.evaluate(localOptimalFood);
		logger.info("New optimum: " + LOFVal);
	}
	public PermutationVector getSolution() {
		return localOptimalFood;
	}
}
