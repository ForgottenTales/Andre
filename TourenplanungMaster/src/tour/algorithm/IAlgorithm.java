package tour.algorithm;

import java.util.List;

public interface IAlgorithm<T> {

	public List<T> createStartingPopulation();
	
	public List<T> chooseParents(List<T> population);
	
	public List<T> makeChildren(List<T> parents);
	
	public List<T> mutate(List<T> population);
	
	public List<T> localOptimization(List<T> population);
	
	public List<T> selectNewPopulation(List<T> population);
	
	public boolean isFinished(List<T> population);
	
	public T updateEntry(T entry);
}
