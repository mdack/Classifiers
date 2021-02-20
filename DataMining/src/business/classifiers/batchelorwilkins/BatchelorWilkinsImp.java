package business.classifiers.batchelorwilkins;

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
import business.transfers.TBatchelorWilkins;
import business.transfers.TResult;

public class BatchelorWilkinsImp implements BatchelorWilkins{
	private List<Signal> signals;
	private int total_files;
	private List<Image> imgs;
	private boolean areSignals = true;
	private List<Cluster> clusters = new ArrayList<>();
	private int A = 0;
	private double teta = 0;
	
	@Override
	public TResult executeAlgorithm(TBatchelorWilkins transfer) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Data data = FactoryAS.getInstance().readData2(transfer.gettZip().getList());
		
		System.out.println("Obteniendo información de archivos : " + hourdateFormat.format(date));
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		teta = transfer.getO();
		
		System.out.println("Se han cargado todos los archivos : " + hourdateFormat.format(date));
		
		System.out.println("Empieza el algoritmo Batchelor y Wilkins : " + hourdateFormat.format(date));
		
		//1º Centro: Escogemos un patrón al azar
		int index = this.getFirstCluster();
		Cluster cluster = null;
		double max_dist = Double.MIN_VALUE;
		int second_cluster = 0;
		
		if(this.areSignals) {
			cluster = new ClusterSig(A, signals.get(index));
			signals.get(index).setId_cluster(A);
			signals.remove(index);
			
			clusters.add(cluster);
			A++;
			
			//2ª Centro: Escogemos patrones más alejado del primero
			for(int i = 0; i < signals.size();i++) {
				double dist = this.calculateDistanceSignal(signals.get(i), cluster);
				
				if(dist > max_dist) {
					max_dist = dist;
					second_cluster = i;
				}
			}
			
			clusters.add(new ClusterSig(A, signals.get(second_cluster)));
			signals.remove(second_cluster);
			
			// Obtenemos centros
			this.loopSignals(transfer);
			
			//Agrupamos libremente
			this.agruparPatronesSig();
		}
		else {
			cluster = new ClusterImg(A, imgs.get(index));
			imgs.get(index).setId_cluster(A);
			imgs.remove(index);
			
			clusters.add(cluster);
			A++;
			//2ª Centro: Escogemos archivo más alejado del primero
			for(int i = 0; i < imgs.size(); i++) {
				double dist = this.calculateDistanceImgs(imgs.get(i), cluster);
				
				if(dist > max_dist) {
					max_dist = dist;
					second_cluster = i;
				}
			}
			
			clusters.add(new ClusterImg(A, imgs.get(second_cluster)));
			imgs.remove(second_cluster);
			A++;
			
			this.loopImgs(transfer);
			
			agruparPatronesImg();
		}
		
		System.out.println("El algoritmo ha terminado : " + hourdateFormat.format(date));
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setCluste_rejection(false);
		result.setN(A);
				
		return result;
	}
	
	/**
	 * Agrupar según regla de la mínima distancia
	 */
	private void agruparPatronesImg() {
		for(int i = 0; i < imgs.size();i++) {
			double distance = Double.MAX_VALUE;
			
			int m = this.getIdNearestCluster(imgs.get(i), distance);
			clusters.get(m).getImages().add(imgs.get(i));
			clusters.get(m).recalculateCentroid();
		}
	}

	
	private void agruparPatronesSig() {
		
		for(int i = 0; i < signals.size();i++) {
			double distance = Double.MAX_VALUE;
			
			int m = this.getIdNearestCluster(signals.get(i), distance);
			clusters.get(m).getSignals().add(signals.get(i));
			clusters.get(m).recalculateCentroid();
		}
		
	}

	/**
	 * Bucle principal
	 * @param transfer
	 */
	private void loopImgs(TBatchelorWilkins transfer) {
		boolean fin = false;

		double umbral = calculateUmbral();
		while(!fin) {	
			//Crear conjunto T: compuesto por archivos más cercanos a los centros
			initClustersImgs();
			
			//Obtener n ->máximo  de  las  distancias  mínimas  de  los  patrones  a  los agrupamientos
			
			//Si la distancia de Xn > umbral -> se crea nuevo agrupamiento 
			int n = 0;
			double max_dist = findMaxDistanceofTImgs(n);
			
			if(max_dist > umbral) {
				Cluster cl = new ClusterImg(A, imgs.get(n));
				cl.recalculateCentroid();
				
				imgs.remove(n);
				clusters.add(cl);
				A++;
			}
			else {
				fin = true;
			}
		}
		
	}

	/**
	 * Calcula umbral centrandose en los dos centros más alejados
	 * @return umbral
	 */
	private double calculateUmbral() {
		return ((clusters.get(0).calculateDistanceTo(clusters.get(1)) * teta) / 1);
	}

	private double findMaxDistanceofTImgs(int n) {
		double max_dist = Double.MIN_VALUE;
		for(int i = 0; i< imgs.size(); i++) {
			int id = imgs.get(i).getId_cluster();
			double dist =  this.calculateDistanceImgs(imgs.get(i), clusters.get(id));
			
			if(dist > max_dist) {
				max_dist = dist;
				n = i;
			}
		}
		return max_dist;
	}
	
	private double findMaxDistanceofSignals(int n) {
		double max_dist = Double.MIN_VALUE;
		
		for(int i = 0; i< signals.size(); i++) {
			int id = signals.get(i).getId_cluster();
			double dist =  this.calculateDistanceSignal(signals.get(i), clusters.get(id));
			
			if(dist > max_dist) {
				max_dist = dist;
				n = i;
			}
		}
		return max_dist;
	}

	/**
	 * Creamos T con las imagenes 
	 */
	private void initClustersImgs() {
		
		for(int i = 0; i< imgs.size(); i++) {
			double distance = Double.MAX_VALUE;
			int m = this.getIdNearestCluster(imgs.get(i), distance);
			imgs.get(i).setId_cluster(m);
		}
	}

	private void loopSignals(TBatchelorWilkins transfer) {
		boolean fin = false;

		double umbral = calculateUmbral();
		while(!fin) {	
			//Crear conjunto T: compuesto por archivos más cercanos a los centros
			initClustersSig();
			
			//Obtener n ->máximo  de  las  distancias  mínimas  de  los  patrones  a  los agrupamientos
			
			//Si la distancia de Xn > umbral -> se crea nuevo agrupamiento 
			int n = 0;
			double max_dist = this.findMaxDistanceofSignals(n);
			
			if(max_dist > umbral) {
				Cluster cl = new ClusterSig(A, signals.get(n));
				cl.recalculateCentroid();
				
				signals.remove(n);
				clusters.add(cl);
				A++;
			}
			else {
				fin = true;
			}
		}
		
	}
	
	  private void initClustersSig() {
			for(int i = 0; i< signals.size(); i++) {
				double distance = Double.MAX_VALUE;
				int m = this.getIdNearestCluster(signals.get(i), distance);
				signals.get(i).setId_cluster(m);
			}
		
	}

	/**
	 * Obtenemos el cluster más cercano
	 * @param obj
	 * @param distance
	 * @return
	 */
	private int getIdNearestCluster(Object obj, double distance) {
	        int id_cluster = 0;

	        for(Cluster cluster: clusters) {
	            double dist = 0;
	            
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

	private int getFirstCluster() {
		Random rdn = new Random();
		return rdn.nextInt(total_files);
	}

}
