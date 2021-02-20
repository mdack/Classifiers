package business.elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.classifiers.adaptative.AdaptativeElement;

public class Signal implements AdaptativeElement{
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


	public double calculateValue() {
		int sum = 0;
		List<Double> list = new ArrayList<Double>(signal.values());
		
		for(int i = 0; i < list.size();i++) {
			sum += list.get(i);
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

	// -----------------------------------------------------------------------------------
	// PARA MÉTODO ADAPTATIVO
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
