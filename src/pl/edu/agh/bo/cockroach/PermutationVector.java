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
			if(permutation.get(positionOfDifference) == target.getPermutation().get(positionOfDifference))
				positionOfDifference++;
			else
				isDifferent = true;
		}
		if(isDifferent) {
			int positionToSwap = positionOfDifference;
			while(permutation.get(positionToSwap) != target.getPermutation().get(positionOfDifference) && positionToSwap < permutation.size()) {
				positionToSwap++;
			}
			this.swap(positionOfDifference, positionToSwap);
		}
		return isDifferent;
	}
	
	public void nRStep(int n) {
		for(int i = 1; i <= n; i++) {
			int stochasticX = (int) Math.floor(Math.random() * (permutation.size()));
			int stochasticY = (int) Math.floor(Math.random() * (permutation.size()));
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
	
}
