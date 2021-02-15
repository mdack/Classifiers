package business.classifiers;

import java.util.ArrayList;
import java.util.List;

import business.elements.Image;
import business.elements.Signal;


public class Cluster {
	private int id_cluster;
    private float central_value=0;
    private boolean areSignals=true;

    private ArrayList<Object> list_files = new ArrayList<>();;

    public Cluster(int id, Signal signal) {
        this.id_cluster = id;
        this.list_files.add(signal);
        this.central_value = signal.calculateValue();
        }
    
    public Cluster(int id, Image image) {
        this.id_cluster = id;
        this.list_files.add(image);
        this.central_value = image.calculateValue();
        this.areSignals = false;
    }

    public int getId_cluster() {
        return id_cluster;
    }

	public List<Image> getImages() {
		List<Image> list = new ArrayList(this.list_files);
		return list;
	}

	public List<Signal> getSignals() {
		List<Signal> list = new ArrayList(this.list_files);
		return list;
	}
	
    public float getCentral_value() {
		return central_value;
	}

	public void setCentral_value(float central_value) {
		this.central_value = central_value;
	}

	public ArrayList<Object> getList_files() {
		return list_files;
	}

	public void setList_files(ArrayList<Object> list_files) {
		this.list_files = list_files;
	}

	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}

	public float calculateValue() {
		float value = 0;
		

			for(Object sig: this.list_files) {
				if(this.areSignals) {
					Signal signal = (Signal) sig;
					value += signal.calculateValue();
				}else {
					Image image = (Image) sig;
					value += image.calculateValue();
				}
			}
		
		return value / list_files.size();
	}


}
