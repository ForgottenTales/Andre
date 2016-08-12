package tour.algorithm;

import java.util.Collections;
import java.util.List;

public class AlgorithmHandler {

	private IAlgorithm<Tour> algorithm = null;
	private List<Tour> population = null;
	private Tour best = null;
	
	public Tour getBest() {
		return best;
	}

	public void setBest(Tour best) {
		this.best = best;
	}

	public AlgorithmHandler(IAlgorithm<Tour> algorithm)
	{
		this.algorithm = algorithm;
		this.population = algorithm.createStartingPopulation();
		for (Tour key : population)
		{
			algorithm.updateEntry(key);
		}
	}

	public Tour findBestSolution()
	{
		while(!algorithm.isFinished(population))
		{
			doStep();
		}
		Collections.sort(population);
		// Finde bestes Individuum
		return best;
	}
	
	public void doStep()
	{
		List<List<Tour>> parents = algorithm.chooseParents(population);
		// 0 = parents, 1 = not parents;
		List<Tour> children = algorithm.makeChildren(parents.get(0));	
		children = algorithm.mutate(children);
		
		// update population
		for (Tour key : children)
		{
			key = algorithm.updateEntry(key);
		}
		children = algorithm.localOptimization(children);
		for (Tour key : children)
		{
			key = algorithm.updateEntry(key);
		}
		for (Tour key : parents.get(0))
		{
			key = algorithm.updateEntry(key);
		}
		for (Tour key : parents.get(1))
		{
			key = algorithm.updateEntry(key);
		}
		population = algorithm.selectNewPopulation(parents.get(0),parents.get(1),children);
	
		// always store best solution
		Collections.sort(population);
		if (best == null)
		{
			best = population.get(0);
		} else if (population.get(0).getFitness() < best.getFitness()) {
			best = population.get(0);
		}
		
	}
	
	public List<Tour> getPopulation()
	{
		return population;
	}
}
