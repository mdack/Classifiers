package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import business.elements.Image;
import business.elements.Signal;

public class ClusterSig extends Cluster {
	
	private List<Signal> list_files = new ArrayList<>();
	private Signal centroid;
	private double[] central_values = new double[2];

	public ClusterSig(int i, Signal signal) {
		super(i);
		list_files.add(signal);
		centroid = signal;
		this.setCentral_values(this.calculateValue());
	}

	public ClusterSig() {
		super();
	}

	public double[] calculateValue() {
		double[] values = new double[2];
		values[0] = 0;
		values[1] = 0;
		
		for(Signal sig: this.list_files) {
			double[] v = sig.calculateValue();	
			
			values[0] += v[0];
			values[1] += v[1];

		}
		
		values[0] = values[0] / list_files.size();
		values[1] = values[1] / list_files.size();
	
		return values;
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
        int size=0;
    	
    	//Elegimos el menor tamaño de la señal para no salirnos del array
        if(sig.getSignal().size() > centroid.getSignal().size())
        	size = centroid.getSignal().size();
        else
        	size = sig.getSignal().size();
        	        
        Object[] list_t = sig.getSignal().keySet().toArray();
        Object[] list_t_centroid = centroid.getSignal().keySet().toArray();
		
        for(int i = 0; i < size; i++) {
        	double key1 = (Double) list_t[i];
        	double key2 = (Double) list_t_centroid[i];
        	
        	sum += Math.pow(key1 - key2 , 2);
        	sum += Math.pow(sig.getSignal().get(key1) - centroid.getSignal().get(key2) , 2);
		}
        
		return Math.sqrt(sum);
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
		String cad = "Cluster " + this.id_cluster + " : \n";
		
		if(centroid != null)
			cad +=  "Centro: " + centroid.getName() + "\n";
		
		cad += "Total patrones: " + list_files.size() + "\n";
		
		return cad;
	}

	@Override
	public void removeItem(Object obj) {
		 Signal sig = (Signal) obj;
		 this.list_files.remove(sig);	
	}

	public double[] getCentral_values() {
		return central_values;
	}

	public void setCentral_values(double[] central_values) {
		this.central_values = central_values;
	}

}
