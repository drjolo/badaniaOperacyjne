package pl.edu.agh.bo.cockroach;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author marpiech
 */
public class PermutationVector {
	
	/**
	 *  Wektor pozycji dla karalucha
	 */
	List<Integer> permutation;
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(PermutationVector.class);
	
	public PermutationVector(Integer length) {
		
		permutation = new ArrayList<Integer>();
		
		/*
		 *  Wypelnienie listy pozycji kolejnymi liczbami 
		 */
		for(int i = 0; i < length; i++)
			permutation.add(i);
	}
	
	public PermutationVector(Integer length, boolean permute) {
		this(length);
		if (permute)
			this.permute();
	}
	
	public void permute() {
		List<Integer> newPermutation = new ArrayList<Integer>();
		while(permutation.size() > 0) {
			int randomIndex = (int) Math.floor(Math.random() * (permutation.size()));
			newPermutation.add(permutation.get(randomIndex));
			permutation.remove(randomIndex);
		}
		permutation = newPermutation;
	}
	
	public void swap(int i, int j) {
		Integer tmp = permutation.get(i);
		permutation.add(i, permutation.get(j));
		permutation.remove(i + 1);
		permutation.remove(j);
		permutation.add(j, tmp);
	}
	
	@Override
	public String toString() {
		String toString = new String();
		for(Integer number : permutation)
			toString = toString.concat("" + number + ", ");
		toString = toString.substring(0, toString.length() - 2);
		return toString;
	}
	public List<Integer> getPermutation() {
		return permutation;
	}
	public void setPermutation(List<Integer> permutation) {
		this.permutation = permutation;
	}
	public boolean crawlTo(PermutationVector target) {
		boolean isDifferent = false;
		int positionOfDifference = 0;
		while(!isDifferent && positionOfDifference < permutation.size()) {
			if(permutation.get(positionOfDifference).equals(target.getPermutation().get(positionOfDifference)))
				positionOfDifference++;
			else
				isDifferent = true;
		}
		//logger.trace("CrawlTo:" + positionOfDifference);
		if(isDifferent) {
			int positionToSwap = positionOfDifference;
			while(positionToSwap < permutation.size() && !permutation.get(positionToSwap).equals(target.getPermutation().get(positionOfDifference))) {
				positionToSwap++;
			}
//			if (positionToSwap >= permutation.size()) {
//				logger.trace("WARNING!: " +  positionOfDifference + " " + permutation.get(0) + ":" + target.getPermutation().get(positionOfDifference));
//				logger.trace("WARNING!: " + permutation.size() + ":" + target.getPermutation().size());
//			
//			for(int i = 0; i < permutation.size(); i++) {
//				//int x1 = target.getPermutation().get(i);
//				//int x2 = permutation.get(positionOfDifference);
//				int x1 = permutation.get(i);
//				int x2 = target.getPermutation().get(positionOfDifference);		
//				if (x1 == x2) {
//					positionToSwap = i;
//					
//					//logger.trace("WARNING!: " + "FOUND!" + i + " " + target.getPermutation().get(i) + " " + permutation.get(positionOfDifference));
//					//logger.trace("WARNING!: " + "FOUND!" + i + " " + x1 + " " + x2);
//				}
//			}
			//logger.trace("One: " + permutation.get(positionOfDifference) + " " + target.getPermutation().get(positionOfDifference));
			this.swap(positionOfDifference, positionToSwap);
			//logger.trace("Two: " + permutation.get(positionOfDifference) + " " + target.getPermutation().get(positionOfDifference));
			//logger.trace("Three: " + (permutation.get(positionOfDifference).equals(target.getPermutation().get(positionOfDifference))));
		}
		return isDifferent;
	}
	
	public void nRStep(int n, boolean random) {
		if(random)
			n = (int) Math.floor(Math.random() * n) + 1;
		int stochasticX = (int) Math.floor(Math.random() * (permutation.size()));
		for(int i = 1; i <= n; i++) {
			
			int stochasticY = (int) Math.floor(Math.random() * (permutation.size()));
			if(permutation.size() > 1)
				if(stochasticX == stochasticY)
					i--;
				else
					this.swap(stochasticX, stochasticY);
		}
	}
	
	public void localNRStep(int n, int dist, boolean random) {
		if(random)
			n = (int) Math.floor(Math.random() * n) + 1;
		int stochasticX = (int) Math.floor(Math.random() * (permutation.size()));
		for(int i = 1; i <= n; i++) {
			int stochasticY = -1;
			while (stochasticY < 0 || stochasticY >= permutation.size())
				stochasticY = stochasticX + (int) (Math.floor(Math.random() * dist) - (dist / 2));
			if(permutation.size() > 1)
				if(stochasticX == stochasticY)
					i--;
				else
					this.swap(stochasticX, stochasticY);
		}
	}	
	
	public PermutationVector copy() {
		PermutationVector newVector = new PermutationVector(0);
		for(Integer i : this.getPermutation())
			newVector.getPermutation().add(i);
		return newVector;
	}
	
	public void inject(PermutationVector vector) {
		permutation = new ArrayList<Integer>();
		for(Integer i : vector.getPermutation())
			permutation.add(i);
	}

	public void rInvert() {
		int stochasticX = (int) Math.floor(Math.random() * (permutation.size()));
		int stochasticY = (int) Math.floor(Math.random() * (permutation.size()));
		int min, max;
		if(stochasticX < stochasticY) {
			min = stochasticX; max = stochasticY;
		} else {
			max = stochasticX; min = stochasticY;
		}
		while (min < max) {
			this.swap(min, max);
			min++; max--;
		}
	}
	
}
