package business.classifiers.hierarchical;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterPair;
import business.classifiers.distance.Distance;
import business.classifiers.distance.DistanceMap;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.THierarchical;

public class HierarchicalImp implements Hierarchical{

    private List<Image> imgs;
    private List<Signal> signals;
    private DistanceMap distances;
    private List<Cluster> clusters;
    private int globalClusterIndex = 0;
	private int total_files;
	private boolean areSignals;

    public DistanceMap getDistances() {
        return distances;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public HierarchicalImp(List<Cluster> clusters, DistanceMap distances) {
        this.clusters = clusters;
        this.distances = distances;
    }

    /**
     * Returns Flattened clusters, i.e. clusters that are at least apart by a given threshold
     * @param linkageStrategy
     * @param threshold
     * @return flat list of clusters
     */
    public List<Cluster> flatAgg(LinkageStrategy linkageStrategy, Double threshold)
    {
        while((!isTreeComplete()) && (distances.minDist() != null) && (distances.minDist() <= threshold))
        {
            //System.out.println("Cluster Distances: " + distances.toString());
            //System.out.println("Cluster Size: " + clusters.size());
            agglomerate(linkageStrategy);
        }

        //System.out.println("Final MinDistance: " + distances.minDist());
        //System.out.println("Tree complete: " + isTreeComplete());
        return clusters;
    }

    public void agglomerate(LinkageStrategy linkageStrategy) {
        ClusterPair minDistLink = distances.removeFirst();
        if (minDistLink != null) {
            clusters.remove(minDistLink.getrCluster());
            clusters.remove(minDistLink.getlCluster());

            Cluster oldClusterL = minDistLink.getlCluster();
            Cluster oldClusterR = minDistLink.getrCluster();
            Cluster newCluster = minDistLink.agglomerate(++globalClusterIndex);

            for (Cluster iClust : clusters) {
                ClusterPair link1 = findByClusters(iClust, oldClusterL);
                ClusterPair link2 = findByClusters(iClust, oldClusterR);
                ClusterPair newLinkage = new ClusterPair();
                newLinkage.setlCluster(iClust);
                newLinkage.setrCluster(newCluster);
                Collection<Distance> distanceValues = new ArrayList<Distance>();

                if (link1 != null) {
                    Double distVal = link1.getLinkageDistance();
                    Double weightVal = link1.getOtherCluster(iClust).getWeightValue();
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link1);
                }
                if (link2 != null) {
                    Double distVal = link2.getLinkageDistance();
                    Double weightVal = link2.getOtherCluster(iClust).getWeightValue();
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link2);
                }

                Distance newDistance = linkageStrategy.calculateDistance(distanceValues);

                newLinkage.setLinkageDistance(newDistance.getDistance());
                distances.add(newLinkage);
            }
            clusters.add(newCluster);
        }
    }

    private ClusterPair findByClusters(Cluster c1, Cluster c2) {
        return distances.findByCodePair(c1, c2);
    }

    public boolean isTreeComplete() {
        return clusters.size() == 1;
    }

    public Cluster getRootCluster() {
        if (!isTreeComplete()) {
            throw new RuntimeException("No root available");
        }
        return clusters.get(0);
    }

	@Override
	public void executeHierarchical(THierarchical transfer) {
		Data data = FactoryAS.getInstance().readData(transfer.gettZip().getFiles());
		
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		
	}

}
