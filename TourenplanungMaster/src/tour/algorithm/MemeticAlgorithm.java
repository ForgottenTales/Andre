package tour.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MemeticAlgorithm implements IAlgorithm<Tour> {

	private int generationCounter = 0;
	private static final int POP_SIZE = 20;
	
	
	private double[][] pathMatrice = 
								   {
								   {0,4.5,4,3.5,3,2.5,3,3.5,4,3.5,3,2.5,2,3,3.5,4,4.5},
								   {4.5,0,0.5,1,1.5,3.5,3,2.5,2,2.5,3,3.5,4,5,4.5,4,3.5},
							       {4,0.5,0,0.5,1,3,3.5,3,2.5,3,3.5,4,3.5,4.5,5,4.5,4},
								   {3.5,1,0.5,0,0.5,2.5,3,3.5,3,3.5,4,3.5,3,4,4.5,5,4.5},
								   {3,1.5,1,0.5,0,2,2.5,3,3.5,4,3.5,3,2.5,3.5,4,4.5,5},
								   {2.5,3.5,3,2.5,2,0,0.5,1,1.5,3.5,3,2.5,2,3,3.5,4,4.5},
								   {3,3,3.5,3,2.5,0.5,0,0.5,1,3,3.5,3,2.5,3.5,4,4.5,4},
								   {3.5,2.5,3,3.5,3,1,0.5,0,0.5,2.5,3,3.5,3,4,4.5,4,3.5},
								   {4,2,2.5,3,3.5,1.5,1,0.5,0,2,2.5,3,3.5,4.5,4,3.5,3},
								   {3.5,2.5,3,3.5,4,3.5,3,2.5,2,0,0.5,1,1.5,4,3.5,3,2.5},
								   {3,3,3.5,4,3.5,3,3.5,3,2.5,0.5,0,0.5,1,3.5,4,3.5,3},
								   {2.5,3.5,4,3.5,3,2.5,3,3.5,3,1,0.5,0,0.5,3,3.5,4,3.5},
								   {2,4,3.5,3,2.5,2,2.5,3,3.5,1.5,1,0.5,0,2.5,3,3.5,4},
								   {3,5,4.5,4,3.5,3,3.5,4,4.5,4,3.5,3,2.5,0,0.5,1,1.5},
								   {3.5,4.5,5,4.5,4,3.5,4,4.5,4,3.5,4,3.5,3,0.5,0,0.5,1},
								   {4,4,4.5,5,4.5,4,4.5,4,3.5,3,3.5,4,3.5,1,0.5,0,0.5},
								   {4.5,3.5,4,4.5,5,4.5,4,3.5,3,2.5,3,3.5,4,1.5,1,0.5,0} //hat bis jetzt gefehlt. 
								   													     //Warum hat die Berechnung trotzdem funktioniert?
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
	public List<Tour> chooseParents(List<Tour> population) 
	{
		Random randomizer = new Random();
		double fitnessSum = 0;
		List<Tour> parents = new ArrayList<Tour>();
		for (Tour key : population)
		{
			fitnessSum += key.getFitness();
		}
		for (int u = 0; u < 2; u++)
		{
			double p = fitnessSum / 5.0; // hier verbunden mit
			double start = randomizer.nextDouble()*p;
			double[] pointers = 
				{
						start + 0*p, // da
						start + 1*p,
						start + 2*p,
						start + 3*p,
						start + 4*p,
				};
			double currentFitnessSum = 0;
			int i = -1;
			for (double pet : pointers)
			{
				while (currentFitnessSum <= pet)
				{
					currentFitnessSum += population.get(i+1).getFitness();
					i++;
				}
				Tour parent = new Tour();
				parent.setRoute(new ArrayList<>(population.get(i).getRoute()));
				parent.setCuts(new ArrayList<>(population.get(i).getCuts()));
				parents.add(parent);
			}
		}
		return parents;
	}

	@Override
	public List<Tour> makeChildren(List<Tour> parents) {
		Random rand = new Random();
		List<Tour> children = new ArrayList<Tour>();
		for (int i = 0; i < parents.size(); i = i +2 )
		{
			Tour parent1 = parents.get(i);
			Tour parent2 = parents.get(i+1);
			
			int dx1 = rand.nextInt(16);
			int dx2 = rand.nextInt(16);
			
			int swap = Math.min(dx1, dx2);
			dx2 = Math.max(dx1, dx2);
			dx1 = swap;
			
			Tour child = new Tour();
			List<Integer> route = new ArrayList<Integer>();
			List<Integer> routeNew = new ArrayList<Integer>();
			List<Integer> donts = new ArrayList<Integer>();
			for (int j = 0; j < 16; j++)
			{
				if (j >= dx1 -1 && j <= dx2 -1)
				{
					donts.add(parent1.getRoute().get(j));
				} 
			}
			for (int j = 0; j < 16; j++)
			{
				if (j >= dx1 -1 && j <= dx2 -1)
				{
					route.add(parent1.getRoute().get(j));
				} else {
					for (int u = 0; u < 16; u++)
					{
						int entry = parent2.getRoute().get(u);
						if (donts.contains(entry))
						{
							continue;
						}
						route.add(entry);
						donts.add(entry);
						break;
					}
				}
			}
			routeNew = route;
			
//			List<Integer> parent2Copy = new ArrayList<Integer>();
//			for (int j = 0; j < 16; j++)
//			{
//				if (route.get(j) == -1)
//				{	
//					parent2Copy.add(parent2.getRoute().get(j));
//				}
//			}
//			for (int j = 0; j < 16; j++)
//			{
//				if (route.get(j) == -1)
//				{
//					// Ersatz finden.
//					boolean finisher = true;
//					for (int z = 0; z < 16; z++)
//					{
//						int loc = (z+j)%parent2Copy.size();
//						int potV = parent2Copy.get(loc);
//						if (route.get(loc) == -1)
//						{
//							continue;
//						}
//						if (routeNew.contains(potV))
//						{
//							continue;
//						}
//						routeNew.add(potV);
//						parent2Copy.remove(loc);
//						finisher = false;
//						break;
//					}
//					if (!finisher)
//					{
//						System.out.println("Kacke gelaufen");
//					}
//				} else {
//					if (routeNew.contains(route.get(j)))
//					{
//						System.out.println("panic!!!! c " + j);
//						System.out.println(Arrays.toString(parent2.getRoute().toArray()));
//						System.out.println(Arrays.toString(route.toArray()));
//						System.out.println(Arrays.toString(routeNew.toArray()));
//					}
//					routeNew.add(route.get(j));
//				}
//			}
			child.setRoute(routeNew);
			
			// CrossOver Cuts here
			child.setCuts(parent1.getCuts());
			children.add(child);
		}
		generationCounter++;
		return children;
	}

	@Override
	public List<Tour> mutate(List<Tour> population) {
		// TODO Auto-generated method stub
		return population;
	}

	@Override
	public List<Tour> localOptimization(List<Tour> population) {
		// TODO Auto-generated method stub
		return population;
	}

	@Override
	public List<Tour> selectNewPopulation(List<Tour> population) {
		Collections.sort(population);
		return population.subList(0, 20);
	}

	@Override
	public boolean isFinished(List<Tour> population) {
		return generationCounter > 15000;
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
				List<Integer> tmp = new ArrayList<Integer>(globalRoute.subList(0, cut));
				if (!tmp.isEmpty())
				{
					subRoutes.add(tmp);
					globalRoute.removeAll(subRoutes.get(subRoutes.size()-1));
				}
			} else {
				List<Integer> tmp = new ArrayList<Integer>(globalRoute);
				if (!tmp.isEmpty())
				{
					subRoutes.add(tmp);
				}
				globalRoute.clear();
			}
		}
		if (globalRoute.size() > 0)
		{
			List<Integer> tmp = new ArrayList<Integer>(globalRoute);
			if (!tmp.isEmpty())
			{
				subRoutes.add(tmp);
			}
			globalRoute.clear();
		}
		entry.setSubRoutes(subRoutes);
		
		double[] subRouteSums = new double[entry.getSubRoutes().size()];
		double[] subRoutePenalty = new double[entry.getSubRoutes().size()];
		double[] values = new double[entry.getSubRoutes().size()];
		
		for (int i = 0; i < subRouteSums.length; i++)
		{
			subRouteSums[i] = 0;
			subRoutePenalty[i] = 0;
			values[i] = 0;
			for (Integer key : entry.getSubRoutes().get(i))
			{
				subRouteSums[i] += comTimes[key-1];
			}
		}
		int length = subRouteSums.length;
		if (length == 3)
		{
			if (subRouteSums[0] <= subRouteSums[1])
			{
				subRouteSums[2] += subRouteSums[0];
			} else {
				subRouteSums[2] += subRouteSums[1];
			}
		} else if (length == 4)
		{
			if (subRouteSums[0] <= subRouteSums[1])
			{
				subRouteSums[2] += subRouteSums[0];
				if (subRouteSums[2] < subRouteSums[1])
				{
					subRouteSums[3] += subRouteSums[2];
				} else {
					subRouteSums[3] += subRouteSums[1];
				}
			} else 
			{
				subRouteSums[2] += subRouteSums[1];
				if (subRouteSums[2] < subRouteSums[0])
				{
					subRouteSums[3] += subRouteSums[2];
				} else 
				{
					subRouteSums[3] += subRouteSums[0];
				}
			}
		}
		entry.setPenaltySum(0);
		double penaltySum = 0;
		for (int i = 0; i < subRouteSums.length; i++)
		{
			entry.getSubRouteComTimes().add(subRouteSums[i]);
			Integer previousKey = 0;
			for (Integer key : entry.getSubRoutes().get(i))
			{
				double pathTime = pathMatrice[previousKey][key];
				subRouteSums[i] += pathTime;
				if (subRouteSums[i] > maxTimes[key -1])
				{
					subRoutePenalty[i] += subRouteSums[i] - maxTimes[key-1];
				}
				previousKey = key;
			}
			penaltySum += subRoutePenalty[i];
			entry.setPenaltySum(entry.getPenaltySum() + subRoutePenalty[i]);
		}
		
		double maxSum = Double.MIN_VALUE; 
		for (int i = 0; i < length; i++)
		{
			if (subRouteSums[i] > maxSum)
			{
				maxSum = subRouteSums[i];
			}
		}
		value = maxSum + penaltySum;
		
		entry.setFitness(value);
		return entry;
	}

}
