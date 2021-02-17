package business.classifiers.agrupamientosecuencial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterImg;
import business.classifiers.cluster.ClusterSig;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TAgrupamientoSec;
import business.transfers.TResult;

public class AgrupamientoSecImp implements AgrupamientoSec{

	private List<Signal> signals;
	private int total_files;
	private List<Image> imgs;
	private boolean areSignals;
	private List<Cluster> clusters = new ArrayList<>();
	private int A = 0;

	@Override
	public TResult executeAlgorithm(TAgrupamientoSec transfer) {
		Data data = FactoryAS.getInstance().readData(transfer.gettZip().getFiles());
		
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		
		//Selecciona aleatoriamente primer centro
		int index = getFirstCluster();
		
		Cluster cluster = null;
		if(this.areSignals) {
			cluster = new ClusterSig(A, signals.get(index));
			signals.get(index).setId_cluster(A);
			
			clusters.add(cluster);
			
			this.loopSignals(transfer.getR(), transfer.getM());
		}else {
			cluster = new ClusterImg(A, imgs.get(index));
			imgs.get(index).setId_cluster(A);
			
			clusters.add(cluster);
			
			this.loopImgs(transfer.getR(), transfer.getM());
		}
				
		//Cada M patrones se mezclan agrupamientos por cercanía, es decir, si la distancia entre ellos es < C
		
		for(int i = 0; i < clusters.size(); i++) {
			for(int j = 0; j < clusters.size(); i++) {
				
			}
		}
		
		/*
		 * Si,  tras  la  mezcla  por  cercanía,  quedan  másagrupamientos que los deseados por el usuario (K), 
		 * se mezclan los agrupamientos por tamaño (semezclan  los  agrupamientos  con  menos  del  T%  de  M  
		 * miembros  con  sus  agrupamientos  máscercanos).
		 * Si aún quedan demasiados agrupamientos, se mezclan los agrupamientos más cercanoshasta obtener el 
		 * número deseado de agrupamientos K.
		 */
		
		
		
		return null;
	}
	
	private void loopSignals(double R, int M) {
		int p = 0; //patrones
		int c = 0; //lote
		while(p < total_files && c != M) {
			p++;
			c++;
		//Calcular distancia de patron actual mas cercano (a su centroide)
			
			double distance = Double.MAX_VALUE;
			int nearest_cluster = this.getIdNearestCluster(signals.get(p), distance);
		
		//Si distancia <= R -> se asigna a agrupamiento más cercano
			if(distance <= R) {
				signals.get(p).setId_cluster(nearest_cluster);
			}
		//Sino se crea nuevo agrupamiento
			else {
				A++;
				Cluster cluster = new ClusterSig(A, signals.get(p));
				signals.get(A);
				clusters.add(cluster);
			}
		}
	}
	
	private void loopImgs(double R, int M) {
		int p = 0; //patrones
		int c = 0; //lote
		while(p < total_files && c != M) {
			p++;
			c++;
		//Calcular distancia de patron actual mas cercano (a su centroide)
			
			double distance = Double.MAX_VALUE;
			int nearest_cluster = this.getIdNearestCluster(imgs.get(p), distance);
		
		//Si distancia <= R -> se asigna a agrupamiento más cercano
			if(distance <= R) {
				imgs.get(p).setId_cluster(nearest_cluster);
				this.recalculateCentroid(clusters.get(nearest_cluster));
			}
		//Sino se crea nuevo agrupamiento
			else {
				A++;
				Cluster cluster = new ClusterImg(A, imgs.get(p));
				imgs.get(A);
				clusters.add(cluster);
			}
		}
	}
	
    private int getIdNearestCluster(Object obj, double distance) {
        int id_cluster = 0;

        for(Cluster cluster: clusters) {
            double dist;
            double sum = 0;
            
            if(this.areSignals) {
            	Signal sig = (Signal) obj;
            	sum = this.calculateDistanceSignal(sig, cluster);
            }else {
            	Image img = (Image) obj;
            	sum = this.calculateDistanceImgs(img, cluster);
            }
            dist = Math.sqrt(sum);

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
        		sum += Math.pow(cluster.getCentral_value() - img.getPixel(i, j), 2);
        	}
        }
        
		return sum;
    }
    
    private double calculateDistanceSignal(Signal sig, Cluster cluster) {
        double sum = 0;
        List<Float> list = new ArrayList<Float>(sig.getSignal().values());
        
        for(int i = 0; i < sig.getSignal().size(); i++) {
        		sum += Math.pow(cluster.getCentral_value() - list.get(i), 2);
        }
        
		return sum;
    }

	private int getFirstCluster() {
		Random rdn = new Random();
		return rdn.nextInt(total_files);
	}
	
    private void recalculateCentroid(Cluster cluster){
    	double min_dist = Double.MAX_VALUE;
    	double dist = 0;
    	if(this.areSignals) {
            for(int i = 0; i < cluster.getSignals().size(); i++) {
            	dist = this.calculateDistanceSignal(cluster.getSignals().get(i), cluster);
            	if(dist < min_dist) {
            		cluster.setCentral_value(i);
            		min_dist = dist;
            	}
            }
        }else {
            for(int i = 0; i < cluster.getImages().size(); i++) {
            	dist = this.calculateDistanceImgs(cluster.getImages().get(i), cluster);
            	if(dist < min_dist) {
            		cluster.setCentral_value(i);
            		min_dist = dist;
            	}
            }
        }
    }

}
