package business.classifiers.hierarchical.strategy;

import java.util.Collection;

import business.classifiers.cluster.Distance;

public class SingleLinkageStrategy implements LinkageStrategy {

	@Override
	public Distance calculateDistance(Collection<Distance> distances) {
		double min = Double.MAX_VALUE;

		for (Distance dist : distances) {
		    if (Double.isNaN(min) || dist.getDistance() < min)
		        min = dist.getDistance();
		}
		return new Distance(min);
	}
}
