package pl.edu.agh.bo.cockroach;

public class MockEvaluation implements Evaluatable {

	int size;
	
	public MockEvaluation (int size) {
		this.size = size;
	}
	
	@Override
	public Double evaluate(PermutationVector permutation) {
		if (size != permutation.getPermutation().size()) throw new RuntimeException("Permutation size doesn't match");
		Double sum = 0.0;
		Integer counter = 1;
		for (Integer i : permutation.getPermutation())
			sum += i * counter++;
		return 15000 - sum;
	}

	@Override
	public int size() {
		return size;
	}

}
