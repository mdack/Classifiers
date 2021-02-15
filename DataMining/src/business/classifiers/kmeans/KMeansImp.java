package business.classifiers.kmeans;

import java.util.ArrayList;
import java.util.List;

import business.transfers.Pixel;


public class KMeansImp implements KMeans{
	  private final static int NUM_CLUSTERS = 6;
	    private final static int VALUES = 3;
	    private final static double TOL = 0.01;

	    private List<Pixel> pixels;
	    private int total_pixels;
	    private ArrayList<Cluster> clusters;

	    public KMeansImp() {
	        this.pixels = new ArrayList<>();
	        this.total_pixels = 0;
	    }

	    /**
	     * Execute KMeans algorithm
	     */
	    public void algorithm() {
	        this.total_pixels = this.pixels.size();
	        this.clusters = new ArrayList<>();

	        initClusters();

	        boolean	goal = false;

	        //Asigno cada pixel a un cluster y recalculo
	        while(!goal) {
	            for (Pixel pixel : pixels) {
	                int id_old_cluster = pixel.getId_cluster();
	                int id_nearest_center = getIdNearestCluster(pixel);

	                if(id_old_cluster != id_nearest_center) { //si le toca un nuevo cluster
	                    if(id_old_cluster != -1)  //Se borra del anterior
	                        clusters.get(id_old_cluster).getPixels().remove(pixel);

	                    //Se añade en el nuevo cluster
	                    pixel.setId_cluster(id_nearest_center);
	                    clusters.get(id_nearest_center).getPixels().add(pixel);
	                }
	            }

	            goal = recalculateCluster();
	        }

	    }

	    private void initClusters(){
	        ArrayList<Integer> prohibited_indexes = new ArrayList<>();

	        //inicializo los cluster con un pixel aleatorio que no pertenezca a otro cluster
	        for(int i = 0; i < NUM_CLUSTERS; i++) {
	            boolean found = false;

	            while(!found) {
	                int index_point = (int) Math.floor(Math.random()*this.total_pixels);

	                if(!prohibited_indexes.contains(index_point)) {
	                    prohibited_indexes.add(index_point);
	                    pixels.get(index_point).setId_cluster(i);

	                    Cluster cluster = new Cluster(i, pixels.get(index_point));
	                    clusters.add(cluster);
	                    found = true;
	                }
	            }
	        }
	    }

	    private int getIdNearestCluster(Pixel p) {
	        double min_dist = Double.MAX_VALUE;
	        int id_cluster = 0;

	        for(Cluster cluster: clusters) {
	            double dist;
	            double sum = 0;

	            for(int i = 0; i < VALUES; i++) { //Distancia Euclidea
	                sum += Math.pow(cluster.getCentralValue(i) - p.getColorValue(i), 2);
	            }

	            dist = Math.sqrt(sum);

	            if(dist < min_dist) {
	                min_dist = dist;
	                id_cluster = cluster.getId_cluster();
	            }
	        }

	        return id_cluster;
	    }

	    private boolean recalculateCluster(){
	        boolean done = true;

	        for(Cluster cluster: clusters) {
	            for(int i = 0; i < VALUES; i++) {

	                double new_value = cluster.calculateValue(i);
	                double old_value = cluster.getCentral_values().get(i);
	                cluster.getCentral_values().set(i, new_value);

	                if(Math.abs(new_value - old_value) > TOL) { //Nivel de tolerancia para ver si los clusters ya están estabilizados
	                    done = false;
	                }
	            }
	        }

	        return done;
	    }

	    /**
	     * Get List of pixels from the bitmap
	     * @return List of Pixels
	     */
	    public List<Pixel> getPixels() {
	        return pixels;
	    }

	    /**
	     * Get List of clusters
	     * @return List of Clusters
	     */
	    public ArrayList<Cluster> getClusters() {
	        return clusters;
	    }


}
