package pl.edu.agh.bo.utils.salesman;

import pl.edu.agh.bo.cockroach.Evaluatable;
import pl.edu.agh.bo.cockroach.PermutationVector;

public class TravellingSalesman implements Evaluatable {
	Road road;
	
	public TravellingSalesman (String roadFile) {
		this.road = new Road(roadFile);
	}

	public TravellingSalesman (Road road) {
		this.road = road;
	}	
	
	@Override
	public Double evaluate(PermutationVector permutation) {
		road.setOrder(permutation.getPermutation());
		return road.distance();
	}

	@Override
	public int size() {
		return road.getSize();
	}
	
	public Road getRoad() {
		return road;
	}
}
