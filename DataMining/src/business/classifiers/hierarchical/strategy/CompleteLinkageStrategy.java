package business.classifiers.hierarchical.strategy;

import java.util.Collection;

import business.classifiers.cluster.Distance;

public class CompleteLinkageStrategy implements LinkageStrategy {

	@Override
	public Distance calculateDistance(Collection<Distance> distances) {
		double max = Double.MIN_VALUE;

		for (Distance dist : distances) {
		    if (Double.isNaN(max) || dist.getDistance() > max)
		        max = dist.getDistance();
		}
		return new Distance(max);
	}
}
