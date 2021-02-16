package business.classifiers.hierarchical;

import java.util.List;

import business.classifiers.cluster.Cluster;


public interface ClusteringAlgorithm
{

    public Cluster performClustering(double[][] distances, String[] clusterNames,
                                     LinkageStrategy linkageStrategy);

    public Cluster performWeightedClustering(double[][] distances, String[] clusterNames,
                                             double[] weights, LinkageStrategy linkageStrategy);

    public List<Cluster> performFlatClustering(double[][] distances,
                                               String[] clusterNames, LinkageStrategy linkageStrategy, Double threshold);
}
