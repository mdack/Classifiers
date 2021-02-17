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
			
			this.loopSignals(transfer.getR(), transfer.getM(), transfer);
		}else {
			cluster = new ClusterImg(A, imgs.get(index));
			imgs.get(index).setId_cluster(A);
			
			clusters.add(cluster);
			
			this.loopImgs(transfer.getR(), transfer.getM(), transfer);
		}
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setN(A);
		
		
		return result;
	}
	
	private void mezcla(TAgrupamientoSec transfer) {
		// HEURISTICA
				//Cada M patrones se mezclan agrupamientos por cercanía, es decir, si la distancia entre ellos es < C
				
				for(int i = 0; i < clusters.size(); i++) {
					for(int j = 0; j < clusters.size(); j++) {
						if(i != j) {
							double dist = clusters.get(i).calculateDistanceTo(clusters.get(j));
							
							if(dist < transfer.getC()) {
								this.mezclaClusters(i, j);
							}
						}
					}
				}
				
				A = clusters.size();
				
				/*
				 * Si,  tras  la  mezcla  por  cercanía,  quedan  másagrupamientos que los deseados por el usuario (K), 
				 * se mezclan los agrupamientos por tamaño (semezclan  los  agrupamientos  con  menos  del  T%  de  M  
				 * miembros  con  sus  agrupamientos  máscercanos).
				 * Si aún quedan demasiados agrupamientos, se mezclan los agrupamientos más cercanoshasta obtener el 
				 * número deseado de agrupamientos K.
				 */
				if(A > transfer.getK()) {
					//MEZCLA POR TAMAÑO
					double t = transfer.getT() / 100; //%
					double min = t * transfer.getM();
					
					for(int i = 0; i < clusters.size(); i++) {
						Cluster cl = clusters.get(i);
						int modulo = 0;
						if(this.areSignals) {
							modulo = cl.getSignals().size();
						}else {
							modulo = cl.getImages().size();
						}
						
						if(modulo < min) {
							double distance = Double.MAX_VALUE;
							int m = this.getIdNearestCluster(cl.getCentroid(), distance);
							
							this.mezclaClusters(i, m);
						}
					}
					
					A = clusters.size();
				}
				
					// MEZCLA FORZADA
					while(A > transfer.getK()) {
						this.mezclaClusters(transfer.getK(),A);
						
						double min_dist = Double.MAX_VALUE;
						int c1 = 0, c2 = 0;
						for(int i = 0; i < clusters.size(); i++) {
							for(int j = 0; j < clusters.size(); j++) {
								if(i != j) {
									double dist = clusters.get(i).calculateDistanceTo(clusters.get(j));
									
									if(dist < min_dist) {
										c1 = i;
										c2 = j;
										min_dist = dist;
									}
								}
							}
						}
						this.mezclaClusters(c1, c2);
						A = clusters.size();
					}
	}
	
	private void mezclaClusters(int i, int j) {
		if(this.areSignals)
			clusters.get(i).addClustersSig(clusters.get(j).getSignals());
		else
			clusters.get(i).addClustersImg(clusters.get(j).getImages());
		
		clusters.get(i).recalculateCentroid();
		clusters.remove(j);
	}
	
	private void loopSignals(double R, int M, TAgrupamientoSec transfer) {
		int p = 0; //patrones
		int c = 0; //lote
		while(p < total_files) {
			p++;
			c++;
			
			if(c == M) {
				c= 0;
				this.mezcla(transfer);
			}else {
			//Calcular distancia de patron actual mas cercano (a su centroide)
				
				double distance = Double.MAX_VALUE;
				int nearest_cluster = this.getIdNearestCluster(signals.get(p), distance);
			
			//Si distancia <= R -> se asigna a agrupamiento más cercano y se recalcula centro
				if(distance <= R) {
					signals.get(p).setId_cluster(nearest_cluster);
					clusters.get(nearest_cluster).recalculateCentroid();
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
	}
	
	private void loopImgs(double R, int M, TAgrupamientoSec transfer) {
		int p = 0; //patrones
		int c = 0; //lote
		while(p < total_files) {
			p++;
			c++;
			
			if(c == M) {
				c= 0;
				this.mezcla(transfer);
			}else {
			//Calcular distancia de patron actual mas cercano (a su centroide)
				
				double distance = Double.MAX_VALUE;
				int nearest_cluster = this.getIdNearestCluster(imgs.get(p), distance);
			
			//Si distancia <= R -> se asigna a agrupamiento más cercano y recalculamos centro
				if(distance <= R) {
					imgs.get(p).setId_cluster(nearest_cluster);
					clusters.get(nearest_cluster).recalculateCentroid();
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
        List<Double> list = new ArrayList<Double>(sig.getSignal().values());
        
        for(int i = 0; i < sig.getSignal().size(); i++) {
        		sum += Math.pow(cluster.getCentral_value() - list.get(i), 2);
        }
        
		return sum;
    }

	private int getFirstCluster() {
		Random rdn = new Random();
		return rdn.nextInt(total_files);
	}
	
	

}
