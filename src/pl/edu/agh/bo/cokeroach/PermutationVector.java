package pl.edu.agh.bo.cokeroach;

import java.util.List;

/**
 * @author marpiech
 * TODO: (creampie) dopisac sie do listy autorow
 */
public class PermutationVector {
	List<Integer> permutation;
	public PermutationVector(Integer length) {
		// wypelnienie listy liczb kolejnymi
		for(int i = 1; i <= length; i++)
			permutation.add(i);
	}
	public void permute() {
		// TODO: (creampie) w oparciu o liczby losowe dokonac permutacji listy 'permutation' 
	}
	
	public void swap(int i, int j) {
		// TODO: (creampie) zamienic ze soba dwa elementy w liscie 'permutation' o indeksach i, j
	}
}
