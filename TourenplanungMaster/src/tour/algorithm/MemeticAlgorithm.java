package tour.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemeticAlgorithm implements IAlgorithm<Tour> {

	private static final int POP_SIZE = 20;
	
	private double[][] pathMatrice = 
								   {
								   {0,4.5,4,3.5,3,2.5,3,3.5,4,2.5,3,2.5,2,3,3.5,4,4.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   // ab hier neu eintragen
								   {4.9,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
								   {4.5,0,0.5,1,2.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5}
								   };
	
	private double[] comTimes = {6,6,6,6,5,5,5,5,4,4,4,4,2,2,2,2};
	
	private double[] maxTimes = {38,20,32,50,25,39,36,54,30,39,23,20,51,41,40,25};
	
	@Override
	public List<Tour> createStartingPopulation() {
		List<Tour> population = new ArrayList<Tour>();
		Random randomizer = new Random();
		for (int i = 0; i < POP_SIZE; i++)
		{
			Tour tour = new Tour();
			List<Integer> currentRoute = new ArrayList<Integer>();
			List<Integer> currentCuts = new ArrayList<Integer>();
			for (int j = 0; j < 16; j++)
			{
				boolean found = false;
				while(!found)
				{
					int randomNumber = randomizer.nextInt(16) + 1;
					if (!currentRoute.contains(randomNumber))
					{
						currentRoute.add(randomNumber);
						found = true;
					}
				}
			}
			
			boolean isValidCutConfiguration = false;
			while(!isValidCutConfiguration)
			{
				int firstCut = randomizer.nextInt(9);
				int secondCut = randomizer.nextInt(9);
				int thirdCut = randomizer.nextInt(9);
				
				if (firstCut + secondCut + thirdCut > 8)
				{
					isValidCutConfiguration = true;
					currentCuts.add(firstCut);
					currentCuts.add(secondCut);
					currentCuts.add(thirdCut);
				}
			}
			tour.setRoute(currentRoute);
			tour.setCuts(currentCuts);
			population.add(tour);
		}
		
		// @TODO: Hier 4 aus population nochmal lokal optimieren
		
		
		
		return population;
	}

	@Override
	public List<Tour> chooseParents(List<Tour> population) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> makeChildren(List<Tour> population) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> mutate(List<Tour> population) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> localOptimization(List<Tour> population) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tour> selectNewPopulation(List<Tour> population) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinished(List<Tour> population) {
		return false;
	}

	@Override
	public Tour updateEntry(Tour entry) {
		double value = 0;
		List<Integer> globalRoute = new ArrayList<Integer>(entry.getRoute());
		List<List<Integer>> subRoutes = new ArrayList<List<Integer>>();
		for (int cut : entry.getCuts())
		{
			if (globalRoute.size() > cut)
			{
				subRoutes.add(globalRoute.subList(0, cut));
				globalRoute.removeAll(subRoutes.get(subRoutes.size()-1));
			}
		}
		entry.setSubRoutes(subRoutes);
		
		Integer previousKey = 0;
		double pathSum = 0;
		for (Integer key : entry.getRoute())
		{
			double pathTime = pathMatrice[previousKey][key];
			pathSum += pathTime;
			previousKey = key;
		}
		value = pathSum;
		entry.setFitness(value);
		return entry;
	}

}
