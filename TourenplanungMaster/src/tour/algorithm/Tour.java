package tour.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Tour {

	private List<Integer> route = new ArrayList<Integer>();
	private List<Integer> cuts = new ArrayList<Integer>();
	private List<List<Integer>> subRoutes = null;
	private List<Double> subRouteComTimes = new ArrayList<Double>();
	
	private double penaltySum = 0;
	
	private double fitness = 0;
	
	public List<Integer> getCuts() {
		return cuts;
	}
	public void setCuts(List<Integer> cuts) {
		this.cuts = cuts;
	}
	public List<Integer> getRoute() {
		return route;
	}
	public void setRoute(List<Integer> route) {
		this.route = route;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public List<List<Integer>> getSubRoutes() {
		return subRoutes;
	}
	public void setSubRoutes(List<List<Integer>> subRoutes) {
		this.subRoutes = subRoutes;
	}
	public double getPenaltySum() {
		return penaltySum;
	}
	public void setPenaltySum(double penaltySum) {
		this.penaltySum = penaltySum;
	}
	public List<Double> getSubRouteComTimes() {
		return subRouteComTimes;
	}
	public void setSubRouteComTimes(List<Double> subRouteComTimes) {
		this.subRouteComTimes = subRouteComTimes;
	}
	
}
