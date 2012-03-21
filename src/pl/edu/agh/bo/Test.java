package pl.edu.agh.bo;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pl.edu.agh.bo.cockroach.CockroachSwarm;
import pl.edu.agh.bo.cockroach.Evaluatable;
import pl.edu.agh.bo.cockroach.MockEvaluation;
import pl.edu.agh.bo.cockroach.PermutationVector;

import junit.framework.TestCase;

public class Test extends TestCase {
	private static final Logger logger = Logger.getLogger(Test.class);
	
	public void test () {
		PropertyConfigurator.configure("lib/log4j.properties");
		logger.trace("Running test");
		PermutationVector permutation = new PermutationVector(5);
		assertEquals(permutation.getPermutation().size(), 5);
		logger.trace("Permutation: " + permutation);
		assertEquals(permutation.getPermutation().get(0).intValue(), 1);
		permutation.swap(1, 3);
		assertEquals(permutation.getPermutation().get(1).intValue(), 4);
		logger.trace("Permutation: " + permutation);
		assertEquals(permutation.crawlTo(new PermutationVector(5)), true);
		logger.trace("Permutation: " + permutation);
		assertEquals(permutation.crawlTo(new PermutationVector(5)), false);
		permutation.swap(1, 3);
		permutation.swap(2, 4);
		permutation.crawlTo(new PermutationVector(5));
		logger.trace("Permutation: " + permutation);
		PermutationVector permutationCopy = permutation.copy();
		permutation.swap(2, 4);
		logger.trace("Permutation: " + permutation);
		logger.trace("Permutation: " + permutationCopy);
		permutation.permute();
		logger.trace("Permutation: " + permutation);
		permutation.nRStep(1);
		logger.trace("Permutation: " + permutation);
		permutation.nRStep(3);
		logger.trace("Permutation: " + permutation);
		Evaluatable mock = new MockEvaluation(10);
		CockroachSwarm cockroachSwarm = new CockroachSwarm(15, 50, mock, 3);
		cockroachSwarm.run(100);
		assertEquals(1,1);
	}
}
