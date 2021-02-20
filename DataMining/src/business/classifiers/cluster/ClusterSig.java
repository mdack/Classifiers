package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public class ClusterSig extends Cluster {
	
	private List<Signal> list_files = new ArrayList<>();
	private Signal centroid;

	public ClusterSig(int i, Signal signal) {
		super(i);
		list_files.add(signal);
		
	}

	public ClusterSig() {
		super();
	}

	@Override
	public float calculateValue() {
		float value = 0;
		
		for(Signal sig: this.list_files) {
				value += sig.calculateValue();
		}
	
		return value / list_files.size();
	}

	@Override
	public List<Signal> getSignals() {
		// TODO Auto-generated method stub
		return list_files;
	}

	@Override
	public List<Image> getImages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Cluster o) {
		
		return 0;
	}

	@Override
	public void setCentroid(Object obj) {
		centroid = (Signal) obj;		
	}

	@Override
	public Object getCentroid() {
		// TODO Auto-generated method stub
		return centroid;
	}

	@Override
	public void recalculateCentroid() {
		int p = list_files.size()-1;
		
		Signal sig = list_files.get(p);
		
		HashMap<Double,Double> new_sig = new HashMap<Double, Double>();
		double sum =  0;
		for(HashMap.Entry<Double,Double> entry: centroid.getSignal().entrySet()) {
			sum += entry.getValue() + sig.getSignal().get(entry.getKey());
			new_sig.put(entry.getKey(), sum/p);
		}
		
		centroid.setSignal(new_sig);
		
	}

	@Override
	public void addClustersSig(List<Signal> list) {
		list_files.addAll(list);
	}

	@Override
	public double calculateDistanceTo(Cluster cluster) {
		
		Signal sig = (Signal) cluster.getCentroid();
		
        double sum = 0;
        List<Double> list = new ArrayList<Double>(sig.getSignal().values());
        List<Double> list_c = new ArrayList<Double>(centroid.getSignal().values());
        
        for(int i = 0; i < sig.getSignal().size(); i++) {
        		sum += Math.pow(list_c.get(i) - list.get(i), 2);
        }
        
		return sum;
	}

	@Override
	public void addClustersImg(List<Image> imgs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItem(Object obj) {
		 Signal sig = (Signal) obj;
		 this.list_files.add(sig);
	}

	@Override
	public String toString() {
		String cad = "Cluster " + this.id_cluster +  " con centro: " + centroid.getName() + "\n";
		cad += "Total patrones: " + list_files.size();
		return cad;
	}

}
