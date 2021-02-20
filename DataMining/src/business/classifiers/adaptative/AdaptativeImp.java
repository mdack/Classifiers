package business.classifiers.adaptative;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import business.classifiers.adaptative.AdaptativeElement.MyState;
import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterImg;
import business.classifiers.cluster.ClusterSig;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TAdaptative;
import business.transfers.TResult;
import presentation.views.mainview.MainView;

public class AdaptativeImp implements Adaptative {
	private List<Signal> signals;
	private List<Image> imgs;
	private boolean areSignals =true;
	private List<Cluster> clusters = new ArrayList<>();
	private int A = 0;
	private double T;
	private double O;
	
	@Override
	public TResult executeAlgorithm(TAdaptative transfer) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Data data = FactoryAS.getInstance().readData2(transfer.getZip().getList());
		
		T = transfer.getT();
		O = transfer.getO();
		
		Cluster cl_1 = null;
		if(transfer.getZip().isAreSignals()) {
			this.signals = data.readSignals();
			MainView.getInstance().updateTextarea("Se han cargado todos los archivos : " + hourdateFormat.format(date));
			
			MainView.getInstance().updateTextarea("Empieza el método adaptativo : " + hourdateFormat.format(date));
			// Creamos primer agrupamiento con patrón 1.
			signals.get(0).changeState(MyState.ASIGNED, A);
			cl_1 = new ClusterSig(A,signals.get(0));
			
			clusters.add(cl_1);
			A++;
			
			// Inicializa clusters y estados
			initSignals();
			
			//loop
			this.loopSignals();
			
			//reagrupamiento
			regroupSignals();
			
		}else {
			this.imgs = data.readImages();
			this.areSignals = false;
			
			// Creamos primer agrupamiento con patrón 1.
			imgs.get(0).changeState(MyState.ASIGNED, A);
			cl_1 = new ClusterImg(A, imgs.get(0));
			
			clusters.add(cl_1);
			A++;
			
			// Inicializa clusters y estados
			initImages();
			
			//bucle
			this.loopImgs();
			
			//reagrupamiento
			this.regroupImages();
		}

		MainView.getInstance().updateTextarea("El algoritmo ha terminado : " + hourdateFormat.format(date));
		
		TResult result = new TResult();
		result.setCluste_rejection(true);
		result.setList(clusters);
		result.setN(A);
		
	    return result;
	}
	
	// --------------------------------------------------------------------------------------------	
	// INICIALIZACION
	// --------------------------------------------------------------------------------------------	
	
	private void initImages() {
		for(int i = 1; i < imgs.size();i++) {
			int m = this.getIdNearestCluster(imgs.get(i), Double.MAX_VALUE);
			
			//Obtenemos la distancia de ese patron al cluster
			double d = this.calculateDistanceImgs(imgs.get(i), clusters.get(m));
			System.out.println("Distancia " + i + ": " + d);
			
			if(d > T) {
				imgs.get(i).changeState(MyState.ASIGNED, A);
				Cluster cl = new ClusterImg(A,imgs.get(i));
				clusters.add(cl);
				A++;
			}
			else {
				if(d <= (O*T)) {
					imgs.get(i).changeState(MyState.ASIGNED, m);
					clusters.get(m).addItem(imgs.get(i));
					clusters.get(m).recalculateCentroid();
				}
				else {
					imgs.get(i).changeState(MyState.INDETERMINATE, m);
				}
			}
		}		
	}

	private void initSignals() {
		for(int i = 1; i < signals.size();i++) {
			int m = this.getIdNearestCluster(signals.get(i), Double.MAX_VALUE);
			
			//Obtenemos la distancia de ese patron al cluster
			double d = this.calculateDistanceSignal(signals.get(i), clusters.get(m));
			System.out.println("Distancia " + i + ": " + d);
			
			if(d > T) {
				signals.get(i).changeState(MyState.ASIGNED, A);
				Cluster cl = new ClusterSig(A,signals.get(i));
				clusters.add(cl);
				A++;
			}
			else {
				if(d <= (O*T)) {
					signals.get(i).changeState(MyState.ASIGNED, m);
					clusters.get(m).addItem(signals.get(i));
					clusters.get(m).recalculateCentroid();
				}
				else {
					signals.get(i).changeState(MyState.INDETERMINATE, m);
				}
			}
		}
		
	}
	
	// --------------------------------------------------------------------------------------------
	// BUCLE PRINCIPAL
	// --------------------------------------------------------------------------------------------	
	
	private void loopSignals() {
		boolean stable = false;
		
		while(!stable) {
			stable = true;
			for(int i = 0; i < signals.size(); i++) {
				int m = this.getIdNearestCluster(signals.get(i), Double.MAX_VALUE);
				
				
				MyState newState = getNewStateSig(signals.get(i),m);
				
				if(signals.get(i).getState() == newState) {
					if(signals.get(i).getState() == MyState.ASIGNED) {
						int id = signals.get(i).getId_cluster();
						
						if(id != m) {
							signals.get(i).getState();
							clusters.get(m).addItem(signals.get(i));
							clusters.get(id).getSignals().remove(signals.get(i));
							
							clusters.get(m).recalculateCentroid();
							clusters.get(id).recalculateCentroid();
							
							signals.get(i).changeState(newState, m);
							stable = false; //Fuerza una iteración
						}
					}
				}
				else {
					switch(newState) {
					case INCOMING:
						
						if(signals.get(i).getState() == MyState.ASIGNED) {
							int id = signals.get(i).getId_cluster();
							clusters.get(id).getSignals().remove(signals.get(i));
							clusters.get(id).recalculateCentroid();
						}
						Cluster cl = new ClusterSig(A,signals.get(i));
						clusters.add(cl);
						signals.get(i).changeState(MyState.ASIGNED, A);
						A++;
						
						break;
					case ASIGNED:
						
						clusters.get(m).addItem(signals.get(i));
						clusters.get(m).recalculateCentroid();
						signals.get(i).changeState(newState, m);
						
						break;
					case INDETERMINATE:
						
						if(signals.get(i).getState() == MyState.ASIGNED) {
							int id = signals.get(i).getId_cluster();
							clusters.get(id).getSignals().remove(signals.get(i));
							clusters.get(id).recalculateCentroid();
							signals.get(i).changeState(MyState.INDETERMINATE, -1);
						}
						stable = false;
						break;
					}
				}
			}
		}
	}
	
	private MyState getNewStateSig(Signal signal, int m) {
		MyState new_state = MyState.INCOMING;
		
		if(signal.getState() == MyState.INDETERMINATE && m != signal.getId_cluster())
			new_state = MyState.ASIGNED;
		else if(m == signal.getId_cluster())
			new_state = MyState.ASIGNED;
		
		return new_state;
	}

	private void loopImgs() {
		boolean stable = false;
		
		while(!stable) {
			stable = true;
			for(int i = 0; i < imgs.size(); i++) {
				double distance = Double.MAX_VALUE;
				int m = this.getIdNearestCluster(imgs.get(i), distance);
				
				MyState newState = this.getNewStateImg(imgs.get(i), m);
				
				if(imgs.get(i).getState() == newState) {
					if(imgs.get(i).getState() == MyState.ASIGNED) {
						int id = imgs.get(i).getId_cluster();
						
						if(id != m) {
							imgs.get(i).getState();
							clusters.get(m).addItem(imgs.get(i));
							clusters.get(id).getImages().remove(imgs.get(i));
							
							clusters.get(m).recalculateCentroid();
							clusters.get(id).recalculateCentroid();
							
							imgs.get(i).changeState(newState, m);
							stable = false; //Fuerza una iteración
						}
					}
				}
				else {
					switch(newState) {
					case INCOMING:
						
						if(imgs.get(i).getState() == MyState.ASIGNED) {
							int id = imgs.get(i).getId_cluster();
							clusters.get(id).getImages().remove(imgs.get(i));
							clusters.get(id).recalculateCentroid();
						}
						Cluster cl = new ClusterImg(A,imgs.get(i));
						clusters.add(cl);
						imgs.get(i).changeState(MyState.ASIGNED, A);
						A++;
						
						break;
					case ASIGNED:
						
						clusters.get(m).addItem(imgs.get(i));
						clusters.get(m).recalculateCentroid();
						imgs.get(i).changeState(newState, m);
						
						break;
					case INDETERMINATE:
						
						if(imgs.get(i).getState() == MyState.ASIGNED) {
							int id = imgs.get(i).getId_cluster();
							clusters.get(id).getImages().remove(imgs.get(i));
							clusters.get(id).recalculateCentroid();
							imgs.get(i).changeState(MyState.INDETERMINATE, -1);
						}
						stable = false;
						break;
					}
				}
			}
		}
	}
	
	private MyState getNewStateImg(Image img, int m) {
		MyState new_state = MyState.INCOMING;
		
		if(img.getState() == MyState.INDETERMINATE && m != img.getId_cluster())
			new_state = MyState.ASIGNED;
		else if(m == img.getId_cluster())
			new_state = MyState.ASIGNED;
		
		return new_state;
	}
	
	// --------------------------------------------------------------------------------------------
	// REAGRUPAMIENTO
	// --------------------------------------------------------------------------------------------	
	
	private void regroupSignals() {
		Cluster cl_rechazo = new ClusterSig();
		for(int i = 0; i < signals.size(); i++) {
			if(signals.get(i).getCurrentState() == -1) {
				double distance = Double.MAX_VALUE;
				int m = this.getIdNearestCluster(signals.get(i), distance);
				
				if(distance <= T) {
					clusters.get(m).addItem(signals.get(i));
					clusters.get(m).recalculateCentroid();
				}
				else {
					cl_rechazo.addItem(signals.get(i));
				}
			}
		}
		clusters.add(cl_rechazo);
	}
	
	private void regroupImages() {
		Cluster cl_rechazo = new ClusterImg();
		for(int i = 0; i < imgs.size(); i++) {
			if(imgs.get(i).getCurrentState() == -1) {
				double distance = Double.MAX_VALUE;
				int m = this.getIdNearestCluster(imgs.get(i), distance);
				
				if(distance <= T) {
					clusters.get(m).addItem(imgs.get(i));
					clusters.get(m).recalculateCentroid();
				}
				else {
					cl_rechazo.addItem(imgs.get(i));
				}
			}
		}
		clusters.add(cl_rechazo);
		
	}
	
	// --------------------------------------------------------------------------------------------
	// OTROS
	// --------------------------------------------------------------------------------------------	
	
	/**
	 * Devuelve el nº del agrupamiento más cercano al patrón X.
	 * @param sig
	 * @param cluster
	 * @return
	 */
	private int getIdNearestCluster(Object obj, double distance) {
        int id_cluster = 0;

        for(Cluster cluster: clusters) {
            double dist;
            
            if(this.areSignals) {
            	Signal sig = (Signal) obj;
            	dist = this.calculateDistanceSignal(sig, cluster);
            }else {
            	Image img = (Image) obj;
            	dist = this.calculateDistanceImgs(img, cluster);
            }

            if(dist < distance) {
                distance = dist;
                id_cluster = cluster.getId_cluster();
            }
        }

        return id_cluster;
	}
	    
	    private double calculateDistanceImgs(Image img, Cluster cluster) {
	        double sum = 0;
	        
	        for(int i = 0; i < img.getRows(); i++) {
	        	for(int j = 0; j < img.getCols(); j++) {
	        		Image centroid = (Image) cluster.getCentroid();
	        		sum += Math.pow(centroid.getPixel(i, j) - img.getPixel(i, j), 2);
	        	}
	        }
	        
			return Math.sqrt(sum);
	    }
	    
	    private double calculateDistanceSignal(Signal sig, Cluster cluster) {
	        double sum = 0;

	    	Signal centroid = (Signal) cluster.getCentroid();
	    	
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

}
