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
import presentation.views.mainview.MainView;

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
		
		MainView.getInstance().UpdateArea("Obteniendo informaci�n de archivos : " + hourdateFormat.format(date) + "\n");
		
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		teta = transfer.getO();
		
		MainView.getInstance().UpdateArea("Se han cargado todos los archivos : " + hourdateFormat.format(date) + "\n");
		MainView.getInstance().UpdateArea("\n ******************************************************************************** \n");
		MainView.getInstance().UpdateArea("Empieza el algoritmo Batchelor y Wilkins : " + hourdateFormat.format(date) + "\n");
		
		//1� Centro: Escogemos un patr�n al azar
		int index = this.getFirstCluster();
		Cluster cluster = null;
		double max_dist = Double.MIN_VALUE;
		int second_cluster = 0;
		
		if(this.areSignals) {
			signals.get(index).setId_cluster(A);
			cluster = new ClusterSig(A, signals.get(index));
			signals.remove(index);
			
			clusters.add(cluster);
			A++;
			
			//2� Centro: Escogemos patrones m�s alejado del primero
			for(int i = 0; i < signals.size();i++) {
				double dist = this.calculateDistanceSignal(signals.get(i), cluster);
				
				if(dist > max_dist) {
					max_dist = dist;
					second_cluster = i;
				}
			}
			
			signals.get(second_cluster).setId_cluster(A);
			clusters.add(new ClusterSig(A, signals.get(second_cluster)));
			signals.remove(second_cluster);
			
			// Obtenemos centros
			this.loopSignals(transfer);
			
			//Agrupamos libremente
			this.agruparPatronesSig();
		}
		else {
			imgs.get(index).setId_cluster(A);
			cluster = new ClusterImg(A, imgs.get(index));
			imgs.remove(index);
			
			clusters.add(cluster);
			A++;
			
			//2� Centro: Escogemos patr�n m�s alejado del primero
			for(int i = 0; i < imgs.size(); i++) {
				double dist = this.calculateDistanceImgs(imgs.get(i), cluster);
				
				if(dist > max_dist) {
					max_dist = dist;
					second_cluster = i;
				}
			}
			
			imgs.get(second_cluster).setId_cluster(A);
			clusters.add(new ClusterImg(A, imgs.get(second_cluster)));
			imgs.remove(second_cluster);
			A++;
			
			this.loopImgs(transfer);
			
			agruparPatronesImg();
		}
		
		MainView.getInstance().UpdateArea("El algoritmo ha terminado : " + hourdateFormat.format(date) + "\n");
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setCluste_rejection(false);
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
	
	  private void initClustersSig() {
			for(int i = 0; i< signals.size(); i++) {
				double distance = Double.MAX_VALUE;
				int m = this.getIdNearestCluster(signals.get(i), distance);
				signals.get(i).setId_cluster(m);
			}
		
	}
	
	// ---------------------------------------------------------------------------------------
	// BUCLES
	// ---------------------------------------------------------------------------------------
	

	private void loopSignals(TBatchelorWilkins transfer) {
		boolean fin = false;

		double umbral = calculateUmbral();
		while(!fin) {	
			//Crear conjunto T: compuesto por archivos m�s cercanos a los centros
			initClustersSig();
			
			//Obtener n ->m�ximo  de  las  distancias  m�nimas  de  los  patrones  a  los agrupamientos
			
			//Si la distancia de Xn > umbral -> se crea nuevo agrupamiento 
			int n = 0;
			double max_dist = this.findMaxDistanceofSignals(n);
			
			if(max_dist > umbral) {
				signals.get(n).setId_cluster(A);
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
	
	/**
	 * Bucle principal
	 * @param transfer
	 */
	private void loopImgs(TBatchelorWilkins transfer) {
		boolean fin = false;

		double umbral = calculateUmbral();
		while(!fin) {	
			//Crear conjunto T: compuesto por archivos m�s cercanos a los centros
			initClustersImgs();
			
			//Obtener n ->m�ximo  de  las  distancias  m�nimas  de  los  patrones  a  los agrupamientos
			
			//Si la distancia de Xn > umbral -> se crea nuevo agrupamiento 
			int n = 0;
			double max_dist = findMaxDistanceofTImgs(n);
			
			if(max_dist > umbral) {
				imgs.get(n).setId_cluster(A);
				Cluster cl = new ClusterImg(A, imgs.get(n));
				cl.recalculateCentroid();
				
				clusters.add(cl);
				imgs.remove(n);
				A++;
			}
			else {
				fin = true;
			}
		}
		
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
	 * Calcula umbral centrandose en los dos centros m�s alejados
	 * @return umbral
	 */
	private double calculateUmbral() {
		return (clusters.get(0).calculateDistanceTo(clusters.get(1)) * teta);
	}
	
	
	// ---------------------------------------------------------------------------------------
	// AGRUPAMIENTO
	// ---------------------------------------------------------------------------------------	
	
	/**
	 * Agrupar seg�n regla de la m�nima distancia
	 */
	private void agruparPatronesImg() {
		for(int i = 0; i < imgs.size();i++) {
			double distance = Double.MAX_VALUE;
			
			int m = this.getIdNearestCluster(imgs.get(i), distance);
			clusters.get(m).addItem(imgs.get(i));
			clusters.get(m).recalculateCentroid();
		}
	}

	
	private void agruparPatronesSig() {
		
		for(int i = 0; i < signals.size();i++) {			
			int m = this.getIdNearestCluster(signals.get(i), Double.MAX_VALUE);
			clusters.get(m).addItem(signals.get(i));
			clusters.get(m).recalculateCentroid();
		}
		
	}

	// ---------------------------------------------------------------------------------------
	// DISTANCIAS
	// ---------------------------------------------------------------------------------------	
	
	/**
	 * Obtenemos el cluster m�s cercano
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
	    	
	    	//Elegimos el menor tama�o de la se�al para no salirnos del array
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
