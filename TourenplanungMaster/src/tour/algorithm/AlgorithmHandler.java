package tour.algorithm;

import java.util.List;

public class AlgorithmHandler {

	IAlgorithm<Tour> algorithm = null;
	List<Tour> population = null;
	
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
		// Finde bestes Individuum
		return population.get(0);
	}
	
	public void doStep()
	{
		List<Tour> parents = algorithm.chooseParents(population);
		List<Tour> children = algorithm.makeChildren(parents);	
		children = algorithm.mutate(children);
		for (Tour key : children)
		{
			key = algorithm.updateEntry(key);
		}
		children = algorithm.localOptimization(children);
		population.addAll(children);
		for (Tour key : population)
		{
			key = algorithm.updateEntry(key);
		}
		population = algorithm.selectNewPopulation(population);
	}
	
	public List<Tour> getPopulation()
	{
		return population;
	}
}
