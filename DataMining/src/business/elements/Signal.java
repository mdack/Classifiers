package business.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Signal {
	HashMap<Float,Float> signal;
    int id_cluster = -1;

	public int getId_cluster() {
		return id_cluster;
	}

	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}

	public HashMap<Float, Float> getSignal() {
		return signal;
	}

	public void setSignal(HashMap<Float, Float> signal) {
		this.signal = signal;
	}

	public float calculateValue() {
		int sum = 0;
		List<Float> list = new ArrayList<Float>(signal.values());
		
		for(int i = 0; i < list.size();i++)
			sum += signal.get(i);
		
		return sum / list.size();
	}
	
}
