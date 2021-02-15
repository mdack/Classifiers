package business.classifiers.kmeans;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import business.classifiers.Cluster;
import business.elements.Image;
import business.elements.Pixel;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TKMeans;


public class KMeansImp implements KMeans{
	  private final static int NUM_CLUSTERS = 6;
	    private final static double TOL = 0.01;
	    private int K = NUM_CLUSTERS;

	    private List<Image> imgs;
	    private List<Signal> signals;
	    private int total_files;
	    private ArrayList<Cluster> clusters;
	    private boolean areSignals = true;

	    public KMeansImp() {
	        this.imgs = new ArrayList<>();
	        this.signals = new ArrayList<>();
	        this.total_files = 0;
	    }

	    /**
	     * Get List of clusters
	     * @return List of Clusters
	     */
	    public ArrayList<Cluster> getClusters() {
	        return clusters;
	    }

		@Override
		public Cluster executeKMeans(TKMeans transfer) {
			
			Data data = FactoryAS.getInstance().readData(transfer.gettZip().getFiles());
			
			if(transfer.gettZip().isAreSignals()) {
				this.signals = data.readSignals();
				this.total_files = signals.size();
			}else {
				this.imgs = data.readImages();
				this.total_files = imgs.size();
				this.areSignals = false;
			}
			
	        this.clusters = new ArrayList();
			
			initClusters(transfer.gettInit());
			
		    boolean	goal = false;

	        //Asigno cada pixel a un cluster y recalculo
	        while(!goal) {
	        	if(transfer.gettZip().isAreSignals()) {
	        		this.loopKMeans_Signals();
	        	}else {
	        		this.loopKMeans_Imgs();
	        	}
	            goal = recalculateCluster();
	        }			
			return null;
		}
		
	    private boolean recalculateCluster(){
	        boolean done = true;

	        for(Cluster cluster: clusters) {
	                float new_value = cluster.calculateValue();
	                float old_value = cluster.getCentral_value();
	                cluster.setCentral_value(new_value);

	                if(Math.abs(new_value - old_value) > TOL) { //Nivel de tolerancia para ver si los clusters ya están estabilizados
	                    done = false;
	                }
	        }

	        return done;
	    }
		
		private void loopKMeans_Imgs() {
            for (Image img : this.imgs) {
                int id_old_cluster = img.getId_cluster();
                int id_nearest_center = getIdNearestCluster(img);

                if(id_old_cluster != id_nearest_center) { //si le toca un nuevo cluster
                    if(id_old_cluster != -1)  //Se borra del anterior
                        clusters.get(id_old_cluster).getImages().remove(img);

                    //Se añade en el nuevo cluster
                    img.setId_cluster(id_nearest_center);
                    clusters.get(id_nearest_center).getImages().add(img);
                }
            }
		}
		
		private void loopKMeans_Signals() {
            for (Signal sig : this.signals) {
                int id_old_cluster = sig.getId_cluster();
                int id_nearest_center = getIdNearestCluster(sig);

                if(id_old_cluster != id_nearest_center) { //si le toca un nuevo cluster
                    if(id_old_cluster != -1)  //Se borra del anterior
                        clusters.get(id_old_cluster).getSignals().remove(sig);

                    //Se añade en el nuevo cluster
                    sig.setId_cluster(id_nearest_center);
                    clusters.get(id_nearest_center).getSignals().add(sig);
                }
            }
		}

		private void initClusters(int gettInit) {
			switch(gettInit) {
			case 0:
				this.initClustersArbitraria();
				break;
			case 1:
				initClustersInversa();
				break;
			case 2:
				initClusterDirecta();
				break;
			}
			
		}
		
	    private void initClustersArbitraria(){
	        ArrayList<Integer> prohibited_indexes = new ArrayList<>();

	        //inicializo los cluster con un pixel aleatorio que no pertenezca a otro cluster
	        for(int i = 0; i < K; i++) {
	            boolean found = false;

	            while(!found) {
	                int index_point = (int) Math.floor(Math.random()*this.total_files);

	                if(!prohibited_indexes.contains(index_point)) {
	                    prohibited_indexes.add(index_point);
	                    
	                    Cluster cluster = null;
	                    if(this.areSignals) {
	                    	this.signals.get(index_point).setId_cluster(i);
	                    	cluster = new Cluster(i, signals.get(index_point));
	                    }
	                    else {
	                    	this.imgs.get(index_point).setId_cluster(i);
	                    	cluster = new Cluster(i, imgs.get(index_point));
	                    }	                    
	                    clusters.add(cluster);
	                    found = true;
	                }
	            }
	        }
	    }

		private void initClusterDirecta() {
			// TODO Auto-generated method stub
			
		}

		private void initClustersInversa() {
			// TODO Auto-generated method stub
			
		}
		
	    private int getIdNearestCluster(Object obj) {
	        double min_dist = Float.MAX_VALUE;
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

	            if(dist < min_dist) {
	                min_dist = dist;
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


}
