package business.classifiers.agrupamientosecuencial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import presentation.views.mainview.MainView;

public class AgrupamientoSecImp implements AgrupamientoSec{

	private List<Signal> signals;
	private int total_files;
	private List<Image> imgs;
	private boolean areSignals =true;
	private List<Cluster> clusters = new ArrayList<>();
	private int A = 0;

	@Override
	public TResult executeAlgorithm(TAgrupamientoSec transfer) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Data data = FactoryAS.getInstance().readData2(transfer.gettZip().getList());
		
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		
		MainView.getInstance().UpdateArea("Se han cargado todos los archivos : " + hourdateFormat.format(date) + "\n");
		MainView.getInstance().UpdateArea("\n ******************************************************************************** \n");
		MainView.getInstance().UpdateArea("Empieza el agrupamiento secuencial : " + hourdateFormat.format(date));
		
		//Selecciona aleatoriamente primer centro
		int index = getFirstCluster();
		
		Cluster cluster = null;
		if(this.areSignals) {
			cluster = new ClusterSig(A, signals.get(index));
			signals.get(index).setId_cluster(A);
			
			clusters.add(cluster);
			A = 1;
			
			this.loopSignals(transfer.getR(), transfer.getM(), transfer);
			
			this.mezcla(transfer);
		}else {
			cluster = new ClusterImg(A, imgs.get(index));
			imgs.get(index).setId_cluster(A);
			
			clusters.add(cluster);
			A = 1;
			
			this.loopImgs(transfer.getR(), transfer.getM(), transfer);
			
			this.mezcla(transfer);
		}
		
		MainView.getInstance().UpdateArea("El algoritmo ha terminado : " + hourdateFormat.format(date) + "\n");
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setN(A);
				
		return result;
	}
	
	// ---------------------------------------------------------------------------------
	// INICIALIZACION
	// ---------------------------------------------------------------------------------
	
	private int getFirstCluster() {
		Random rdn = new Random();
		return rdn.nextInt(total_files);
	}
	
	// ---------------------------------------------------------------------------------------
	// BUCLES
	// ---------------------------------------------------------------------------------------
	
	private void loopSignals(double R, int M, TAgrupamientoSec transfer) {
		int p = 1; //patrones
		int c = 1; //lote
		
		while(p < signals.size()) {
			
			if(c == M) {
				c= 0;
				this.mezcla(transfer);
			}else {
			//Calcular distancia de patron actual mas cercano (a su centroide)
				
				int nearest_cluster = this.getIdNearestCluster(signals.get(p), Double.MAX_VALUE);
				
				double distance = this.calculateDistanceSignal(signals.get(p), clusters.get(nearest_cluster));
			
				int id = signals.get(p).getId_cluster();
				if(id != -1)
					clusters.get(id).removeItem(signals.get(p));	
				
			//Si distancia <= R -> se asigna a agrupamiento más cercano y se recalcula centro
				if(distance <= R) {
					signals.get(p).setId_cluster(nearest_cluster);
					clusters.get(nearest_cluster).recalculateCentroid();
				}
			//Sino se crea nuevo agrupamiento
				else {
					signals.get(p).setId_cluster(A);
					Cluster cluster = new ClusterSig(A, signals.get(p));
					clusters.add(cluster);
					A++;
				}
			}
			
			p++;
			c++;
		}
	}
	
	private void loopImgs(double R, int M, TAgrupamientoSec transfer) {
		int p = 1; //patrones
		int c = 1; //lote
		
		while(p < imgs.size()) {	
			
			if(c == M) {
				c= 0;
				this.mezcla(transfer);
			}else {
			//Calcular distancia de patron actual mas cercano (a su centroide)				
				int nearest_cluster = this.getIdNearestCluster(imgs.get(p), Double.MAX_VALUE);
				double distance = this.calculateDistanceImgs(imgs.get(p), clusters.get(nearest_cluster));
				
				int id = imgs.get(p).getId_cluster();
				if(id != -1)
					clusters.get(id).removeItem(imgs.get(p));
				
			//Si distancia <= R -> se asigna a agrupamiento más cercano y recalculamos centro
				if(distance <= R) {
					imgs.get(p).setId_cluster(nearest_cluster);
					clusters.get(nearest_cluster).recalculateCentroid();
				}
			//Sino se crea nuevo agrupamiento
				else {
					imgs.get(p).setId_cluster(A);
					Cluster cluster = new ClusterImg(A, imgs.get(p));
					clusters.add(cluster);
					A++;
				}
			}
			
			p++;
			c++;
		}
	}
	
	// ---------------------------------------------------------------------------------------
	// MEZCLA
	// ---------------------------------------------------------------------------------------	
	
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
					double t = transfer.getT() / 100; //Obtenemos porcentaje
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
							int m = this.getIdNearestCluster(cl.getCentroid(), Double.MAX_VALUE);
							
							this.mezclaClusters(m, i);
						}
					}
					
					A = clusters.size();
				}
				
					// MEZCLA FORZADA
					while(A > transfer.getK()) {
						this.mezclaClusters(transfer.getK(),A-1);
						
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
		
		reasignIdClusters();
	}
	
	private void reasignIdClusters() {
		for(int i = 0; i < clusters.size(); i++) {
			clusters.get(i).setId_cluster(i);
		}
	}

	// ---------------------------------------------------------------------------------------
	// DISTANCIAS
	// ---------------------------------------------------------------------------------------	
	
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
        		Image centroid = (Image) cluster.getCentroid();
        		sum += Math.pow(centroid.getPixel(i, j) - img.getPixel(i, j), 2);
        	}
        }
        
		return sum;
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
