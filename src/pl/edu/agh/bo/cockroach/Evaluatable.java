package pl.edu.agh.bo.cockroach;

public interface Evaluatable {
	Double evaluate(PermutationVector permutation);
	int size();
}
