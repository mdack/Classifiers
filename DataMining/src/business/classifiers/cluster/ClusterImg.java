package business.classifiers.cluster;

import java.util.ArrayList;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public class ClusterImg extends Cluster {

	protected List<Image> list_files = new ArrayList<>();
	private Image centroid;

	public ClusterImg(int i, Image image) {
		super(i);
		list_files.add(image);
		centroid = image;
		this.central_value = this.calculateValue();
	}	
	
	public ClusterImg() {
		super();
	}

	public List<Image> getImages() {;
		return list_files;
	}
	
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

	@Override
	public int compareTo(Cluster o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void recalculateCentroid() {
		int p = list_files.size()-1;
		Image img = list_files.get(p);
		Image img_aux = new Image();
		img_aux.setImage(centroid.getImage());
		double[][] list_aux = new double[centroid.getRows()][centroid.getCols()];
		
		double sum = 0;
		
		for(int c = 0; c < centroid.getCols(); c++) {
			int row = 0;
			sum = 0;
			for(int r = 0; r < centroid.getRows();r++) {
				sum += centroid.getPixel(r, c) + img.getPixel(r, c);
			}
			list_aux[row][c] = sum/p;
			row++;
		}
		
		img_aux.convertMatrixToList(list_aux);
		centroid = img_aux;
	}

	@Override
	public void setCentroid(Object obj) {
		centroid = (Image) obj;		
	}

	@Override
	public Object getCentroid() {
		// TODO Auto-generated method stub
		return centroid;
	}

	@Override
	public double calculateDistanceTo(Cluster cluster) {
		
		Image img = (Image) cluster.getCentroid();
		
        double sum = 0;
        
        for(int i = 0; i < img.getRows(); i++) {
        	for(int j = 0; j < img.getCols(); j++) {
        		sum += Math.pow(this.centroid.getPixel(i, j) - img.getPixel(i, j), 2);
        	}
        }
        
		return sum;
	}

	@Override
	public void addClustersSig(List<Signal> signals) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addClustersImg(List<Image> imgs) {
		this.list_files.addAll(imgs);		
	}

	@Override
	public void addItem(Object obj) {
		Image img = (Image) obj;
		this.list_files.add(img);
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
		Image img = (Image) obj;
		
		if(this.list_files.contains(img))
			this.list_files.remove(img);
	}
	
	
	
}
