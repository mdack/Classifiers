package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import business.elements.Image;
import business.elements.Signal;

public class ClusterSig extends Cluster {
	
	private List<Signal> list_files = new ArrayList<>();
	private Signal centroid;

	public ClusterSig(int i, Signal signal) {
		super(i);
		list_files.add(signal);
		centroid = signal;
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
		
		TreeMap<Double,Double> new_sig = new TreeMap<Double, Double>();
		
		int size=0;
    	
    	//Elegimos el menor tamaño de la señal para no salirnos del array
        if(sig.getSignal().size() > centroid.getSignal().size())
        	size = centroid.getSignal().size();
        else
        	size = sig.getSignal().size();
        	        
        Object[] list_t = sig.getSignal().keySet().toArray();
        Object[] list_t_centroid = centroid.getSignal().keySet().toArray();
		double sum1 =  0;
		double sum2 = 0;
		
        for(int i = 0; i < size; i++) {
        	double key1 = (Double) list_t[i];
        	double key2 = (Double) list_t_centroid[i];
        	sum1 += key1 + key2;
        	sum2 += sig.getSignal().get(key1) + centroid.getSignal().get(key2);

			new_sig.put(sum1/list_files.size(), sum2/list_files.size());
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
