package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public class ClusterSig extends Cluster {
	
	private List<Signal> list_files = new ArrayList<>();
	
    public ClusterSig(String clusterName) {
		super(clusterName);
		// TODO Auto-generated constructor stub
	}

	public ClusterSig(int i, Signal signal) {
		super(i);
		list_files.add(signal);
		
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

}
