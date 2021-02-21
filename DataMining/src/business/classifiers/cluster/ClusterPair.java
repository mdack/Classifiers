
package business.classifiers.cluster;

public class ClusterPair implements Comparable<ClusterPair> {

    private Cluster lCluster;
    private Cluster rCluster;
    private Double linkageDistance;

    public ClusterPair(){
    }

    public ClusterPair(Cluster left, Cluster right, Double distance) {
        lCluster = left;
        rCluster = right;
        linkageDistance = distance;
    }

    public Cluster getOtherCluster(Cluster c) {
    return lCluster == c ? rCluster : lCluster;
  }

    public Cluster getlCluster() {
        return lCluster;
    }

    public void setlCluster(Cluster lCluster) {
        this.lCluster = lCluster;
    }

    public Cluster getrCluster() {
        return rCluster;
    }

    public void setrCluster(Cluster rCluster) {
        this.rCluster = rCluster;
    }

    public Double getLinkageDistance() {
        return linkageDistance;
    }

    public void setLinkageDistance(Double distance) {
        this.linkageDistance = distance;
    }

    public ClusterPair reverse() {
        return new ClusterPair(getrCluster(), getlCluster(), getLinkageDistance());
    }

    @Override
    public int compareTo(ClusterPair o) {
        int result;
        if (o == null || o.getLinkageDistance() == null) {
            result = -1;
        } else if (getLinkageDistance() == null) {
            result = 1;
        } else {
            result = getLinkageDistance().compareTo(o.getLinkageDistance());
        }

        return result;
    }

    public Cluster agglomerate(int id, boolean isSignal) {
    	
        Cluster cluster = null;
        if(isSignal)
        	cluster = new ClusterSig(id);
        else
        	cluster = new ClusterImg(id);
        
        cluster.setDistance(new Distance(getLinkageDistance()));
       
        cluster.addChild(lCluster);
        cluster.addChild(rCluster);
        lCluster.setParent(cluster);
        rCluster.setParent(cluster);

     
        double lWeight = lCluster.getWeightValue();
        double rWeight = rCluster.getWeightValue();
        double weight = lWeight + rWeight;
        
        cluster.getDistance().setWeight(weight);

        return cluster;
    }
}
