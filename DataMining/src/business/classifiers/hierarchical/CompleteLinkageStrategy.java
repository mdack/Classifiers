package business.classifiers.hierarchical;

import java.util.Collection;

import business.classifiers.distance.Distance;

public class CompleteLinkageStrategy implements LinkageStrategy {

	@Override
	public Distance calculateDistance(Collection<Distance> distances) {
		double max = Double.NaN;

		for (Distance dist : distances) {
		    if (Double.isNaN(max) || dist.getDistance() > max)
		        max = dist.getDistance();
		}
		return new Distance(max);
	}
}
