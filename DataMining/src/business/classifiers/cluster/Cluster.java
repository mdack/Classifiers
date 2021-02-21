package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public abstract class Cluster implements Comparable<Cluster>{

	protected int id_cluster=-1;
    protected double central_value=0;
    protected boolean areSignals=true;
    
    // Para Jerárquico
    protected Distance distance = new Distance();
    private Cluster parent;
    private List<Cluster> children;

	public Cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}   

	public Cluster() {
		id_cluster=-1;
		central_value=0;
		areSignals=true;
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
	
	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance2) {
		this.distance = distance2;
	}
	
	 public List<Cluster> getChildren(){
		 if (children == null){
			 children = new ArrayList<Cluster>();
	     }

	     return children;
	 }
	 
	 public void setChildren(List<Cluster> children){
		 this.children = children;
	 }

	 public Cluster getParent(){
		 return parent;
	 }

	 public void setParent(Cluster parent){
		 this.parent = parent;
	 }
	    
	 public void addChild(Cluster cluster){
		 getChildren().add(cluster);
	 }

	 public boolean contains(Cluster cluster) {
		 return getChildren().contains(cluster);
	 }
	    
	 public double getWeightValue(){
		 return distance.getWeight();
	 }
	 
	 public boolean isLeaf(){
	        return getChildren().size() == 0;
	 }

	    public int countLeafs()
	    {
	        return countLeafs(this, 0);
	    }

	    public int countLeafs(Cluster node, int count)
	    {
	        if (node.isLeaf()) count++;
	        for (Cluster child : node.getChildren())
	        {
	            count += child.countLeafs();
	        }
	        return count;
	    }
	    
	    public String toNewickString(int indent)
	    {
	        String cad = "";
	        
	        cad += "Cluster: " + this.id_cluster;
	        
	        if(this.children != null) {
		        if(this.children.size() != 0) 
		        	cad += "hijos: ";
	
		        List<Cluster> children = getChildren();
		        
		        for (Cluster child : children){  	
		        	if(child != null) {
			            cad += " -> " + child.toNewickString(indent);
			            
			            cad += distance.toString() + "\n";
		        	}
		        }
		        
	        }
	        
	        return cad;
	    }

	    public double getTotalDistance()
	    {
	        Double dist = getDistance() == null ? 0 : getDistance().getDistance();
	        if (getChildren().size() > 0)
	        {
	            dist += children.get(0).getTotalDistance();
	        }
	        return dist;

	    }

	public abstract List<Signal> getSignals();

	public abstract List<Image> getImages();
	
	public abstract void setCentroid(Object obj);
	
	public abstract Object getCentroid();
	
	public abstract void recalculateCentroid();
	
	public abstract double calculateDistanceTo(Cluster cluster);

	public abstract void addClustersSig(List<Signal> signals);

	public abstract void addClustersImg(List<Image> imgs);

	public abstract void addItem(Object obj);
	
	public abstract void removeItem(Object obj);
	
	public abstract String toString();

}
