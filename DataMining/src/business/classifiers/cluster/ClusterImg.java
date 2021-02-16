package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public class ClusterImg extends Cluster {

    public ClusterImg(String clusterName) {
		super(clusterName);
		// TODO Auto-generated constructor stub
	}

	public ClusterImg(int i, Image image) {
		super(i);
		list_files.add(image);
	}

	protected List<Image> list_files = new ArrayList<>();
	
	
	public List<Image> getImages() {;
		return list_files;
	}
	
	@Override
	public float calculateValue() {
		float value = 0;
			for(Image img: this.list_files) {
					value += img.calculateValue();
				}
		
		return value / list_files.size();
	}

	@Override
	public List<Signal> getSignals() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
