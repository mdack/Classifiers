package business.classifiers.cluster;

import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public abstract class Cluster implements Comparable<Cluster>{

	protected int id_cluster;
    protected double central_value=0;
    protected boolean areSignals=true;

    public Cluster(String clusterName) {
		// TODO Auto-generated constructor stub
	}
    
	public Cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}
    
    

	public int getId_cluster() {
        return id_cluster;
    }
	
    public double getCentral_value() {
		return central_value;
	}

	public void setCentral_value(double central_value) {
		this.central_value = central_value;
	}

	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public Double getWeightValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addLeafName(String clusterName) {
		// TODO Auto-generated method stub
		
	}
		
	public abstract float calculateValue();

	public abstract List<Signal> getSignals();

	public abstract List<Image> getImages();
	
	public abstract void setCentroid(Object obj);
	
	public abstract Object getCentroid();
	
	public abstract void recalculateCentroid();
	
	public abstract double calculateDistanceTo(Cluster cluster);

	public abstract void addClustersSig(List<Signal> signals);

	public abstract void addClustersImg(List<Image> imgs);

}
