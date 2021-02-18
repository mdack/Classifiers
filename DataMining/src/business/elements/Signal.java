package business.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Signal {
	HashMap<Double,Double> signal;
    int id_cluster = -1;
    private String name;

	public int getId_cluster() {
		return id_cluster;
	}

	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}

	public HashMap<Double, Double> getSignal() {
		return signal;
	}

	public void setSignal(HashMap<Double, Double> signal) {
		this.signal = signal;
	}

	@SuppressWarnings("unlikely-arg-type")
	public double calculateValue() {
		int sum = 0;
		List<Double> list = new ArrayList<Double>(signal.values());
		
		for(int i = 0; i < list.size();i++) {
			sum += signal.get(i);
		}
		
		return sum / list.size();
	}

	public double calculateDistanceTo(Signal sig) {		
        double sum = 0;
        List<Double> list = new ArrayList<Double>(sig.getSignal().values());
        List<Double> list_c = new ArrayList<Double>(this.getSignal().values());
        
        for(int i = 0; i < sig.getSignal().size(); i++) {
        		sum += Math.pow(list_c.get(i) - list.get(i), 2);
        }
        
		return sum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
