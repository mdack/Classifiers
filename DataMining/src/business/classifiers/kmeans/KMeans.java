package business.classifiers.kmeans;

import business.classifiers.cluster.Cluster;
import business.transfers.*;

public interface KMeans {

	public Cluster executeKMeans(TKMeans transfer);
}
