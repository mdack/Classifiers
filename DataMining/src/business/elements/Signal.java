package business.elements;

import java.util.Map;
import java.util.TreeMap;

import business.classifiers.adaptative.AdaptativeElement;

public class Signal implements AdaptativeElement{
	private TreeMap<Double,Double> signal;
    int id_cluster = -1;
    private String name;

	public int getId_cluster() {
		return id_cluster;
	}

	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}

	public TreeMap<Double, Double> getSignal() {
		return signal;
	}

	public void setSignal(TreeMap<Double, Double> signal) {
		this.signal = signal;
	}


	public double[] calculateValue() {
		double[] values = new double[2];
		values[0] = 0;
		values[1] = 0;
		
		if(!signal.values().isEmpty()) {
			double sum1 = 0;
			double sum2 = 0;
		
			for(Map.Entry<Double,Double> entry : signal.entrySet()) {
				sum1 += entry.getKey();
				sum2 += entry.getValue();
			}
			
			values[0] = sum1/signal.size();
			values[1] = sum2/signal.size();
		}
		
		return values;
	}

	public double calculateDistanceTo(Signal sig) {		
		 double sum = 0;        
	        int size=0;
	    	
	    	//Elegimos el menor tama�o de la se�al para no salirnos del array
	        if(sig.getSignal().size() > this.signal.size())
	        	size = this.signal.size();
	        else
	        	size = sig.getSignal().size();
	        	        
	        Object[] list_t = sig.getSignal().keySet().toArray();
	        Object[] list = this.signal.keySet().toArray();
			
	        for(int i = 0; i < size; i++) {
	        	double key1 = (Double) list_t[i];
	        	double key2 = (Double) list[i];
	        	
	        	sum += Math.pow(key1 - key2 , 2);
	        	sum += Math.pow(sig.getSignal().get(key1) - this.signal.get(key2) , 2);
			}
	        
			return Math.sqrt(sum);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// -----------------------------------------------------------------------------------
	// PARA M�TODO ADAPTATIVO
	// -----------------------------------------------------------------------------------
	    private MyState state;
	    
		@Override
		public int getCurrentState() {
			int id;
			
			switch(state) {
			case ASIGNED:
				id = this.id_cluster;
				break;
			case INDETERMINATE:
				id = -1;
				break;
			default:
				id = -2;
				break;
			}
			
			return id;
		}
		
		public MyState getState() {
			return state;
		}
		public void setState(MyState state) {
			this.state = state;
		}
		
		@Override
		public void changeState(MyState newState, int id_c) {	
			
			if(newState == MyState.INDETERMINATE) {
				this.id_cluster = 0;
			}else if(newState == MyState.ASIGNED) {
				if(this.id_cluster != id_c) {
					this.id_cluster = id_c;
				}
			}
			
			state = newState;
		}
	
}
