package business.classifiers.kmeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterImg;
import business.classifiers.cluster.ClusterSig;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TKMeans;
import business.transfers.TResult;
import presentation.views.mainview.MainView;


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
		public TResult executeKMeans(TKMeans transfer) {
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
			MainView.getInstance().UpdateArea("Empieza algoritmo K-Means : " + hourdateFormat.format(date) + "\n");
			
			this.K = transfer.getK();
	        this.clusters = new ArrayList<>();
			
			//Inicialización
	        initClusters(transfer.gettInit());
			
			//Asignación y actualización de los centros
		    boolean	goal = false;

	        while(!goal) {
	        	if(transfer.gettZip().isAreSignals()) {
	        		this.loopKMeans_Signals();
	        	}else {
	        		this.loopKMeans_Imgs();
	        	}
	            goal = recalculateCluster();
	        }		
	        
	        MainView.getInstance().UpdateArea("El algoritmo ha terminado : " + hourdateFormat.format(date) + "\n");
	        
			TResult result = new TResult();
			result.setList(clusters);
			result.setN(clusters.size());
			return result;
		}
		
		// ---------------------------------------------------------------------------------
		// INICIALIZACION
		// ---------------------------------------------------------------------------------

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

			        //inicializo los cluster con un archivo aleatorio que no pertenezca a otro cluster
			        for(int i = 0; i < K; i++) {
			            boolean found = false;

			            while(!found) {
			                int index_point = (int) Math.floor(Math.random()*this.total_files);

			                if(!prohibited_indexes.contains(index_point)) {
			                    prohibited_indexes.add(index_point);
			                    
			                    Cluster cluster = null;
			                    if(this.areSignals) {
			                    	this.signals.get(index_point).setId_cluster(i);
			                    	cluster = new ClusterSig(i, signals.get(index_point));	                    	
			                    }
			                    else {
			                    	this.imgs.get(index_point).setId_cluster(i);
			                    	cluster = new ClusterImg(i, imgs.get(index_point));
			                    }	
			                    clusters.add(cluster);
			                    found = true;
			                }
			            }
			        }
			    }

				private void initClusterDirecta() {
					double max_t = Double.MIN_VALUE;
					double min_t = Double.MAX_VALUE;
					double max_s = Double.MIN_VALUE;
					double min_s = Double.MAX_VALUE;
					double max_dist = Double.MIN_VALUE;
					double min_dist = Double.MAX_VALUE;
					
					for(int l = 0; l < K; l++) {
						//Obtener la menor y mayor distancia entre los patrones
						if(this.areSignals) {
							
							for(int i = 0; i < signals.size();i++) {
								double[] v = signals.get(i).calculateValue();
										
								if(v[0] > max_t) {
									max_t = v[0];
								}
								else if(v[0] < min_t) {
									min_t = v[0];
								}
								
								if(v[1] > max_s) {
									max_s = v[1];
								}
								else if(v[1] < min_s) {
									min_s = v[1];
								}
								
							}
							
							double seg1 = (max_t - min_t) / (K+1);
							double seg2 = (max_s - min_s) / (K+1);
							
							ClusterSig c1 = new ClusterSig();
							double[] values = new double[2];
							values[0] = seg1;
							values[1] = seg2;
							c1.setCentral_values(values);
							c1.setId_cluster(l);
				
							clusters.add(c1);
						}
							
						else {
							double dist = 0;
							for(int i = 0; i < imgs.size();i++) {
								for(int j = 0; j < imgs.size(); j++) {
									if(i != j) {
										dist = imgs.get(i).calculateDistanceTo(imgs.get(j));
										if(dist > max_dist) {
											max_dist = dist;
										}
										else if(dist < min_dist) {
											min_dist = dist;
										}
									}
								}
							}
							double seg = (max_dist - min_dist) / (K+1);
							
							Cluster c1 = new ClusterImg();
							c1.setCentral_value(seg);
							c1.setId_cluster(l);

							clusters.add(c1);
						}
					}
				}

				private void initClustersInversa() {
					 int index = this.total_files-1;
				        //inicializo los cluster con un archivo aleatorio que no pertenezca a otro cluster
				        for(int i = 0; i < K; i++) {
				        	Cluster cluster = null;
		                    if(this.areSignals) {
		                    	this.signals.get(index).setId_cluster(i);
		                    	cluster = new ClusterSig(i, signals.get(index));
		                    }
		                    else {
		                    	this.imgs.get(index).setId_cluster(i);
		                    	cluster = new ClusterImg(i, imgs.get(index));
		                    }	                    
		                    clusters.add(cluster);
		                    index--;
				        }
				}
				
				// ---------------------------------------------------------------------------------------
				// BUCLES
				// ---------------------------------------------------------------------------------------	
				
				private void loopKMeans_Imgs() {
		            for (Image img : this.imgs) {
		                int id_old_cluster = img.getId_cluster();
		                int id_nearest_center = getIdNearestCluster(img);

		                if(id_old_cluster != id_nearest_center) { //si le toca un nuevo cluster
		                    if(id_old_cluster != -1)  //Se borra del anterior
		                        clusters.get(id_old_cluster).removeItem(img);;

		                    //Se añade en el nuevo cluster
		                    img.setId_cluster(id_nearest_center);
		                    clusters.get(id_nearest_center).addItem(img);
		                }
		            }
				}
				
				private void loopKMeans_Signals() {
					
		            for (Signal sig : this.signals) {
		                int id_old_cluster = sig.getId_cluster();
		                int id_nearest_center = getIdNearestCluster(sig);

		                if(id_old_cluster != id_nearest_center) { //si le toca un nuevo cluster
		                    if(id_old_cluster != -1)  //Se borra del anterior
		                        clusters.get(id_old_cluster).removeItem(sig);

		                    //Se añade en el nuevo cluster
		                    sig.setId_cluster(id_nearest_center);
		                    clusters.get(id_nearest_center).addItem(sig);
		                }
		            }
				}
				
				   private int getIdNearestCluster(Object obj) {
				        double min_dist = Float.MAX_VALUE;
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
			            
						return Math.sqrt(sum);
				    }
				    
				    private double calculateDistanceSignal(Signal sig, Cluster cluster) {
				    	 ClusterSig cl = (ClusterSig) cluster;

				    	double sum1 = 0;
						double sum2 = 0;
						
							for(Map.Entry<Double,Double> entry : sig.getSignal().entrySet()) {
								sum1 += Math.pow(cl.getCentral_values()[0] - entry.getKey() , 2);
								sum2 += Math.pow(cl.getCentral_values()[1] - entry.getValue() , 2);
							}
						return Math.sqrt(sum1+sum2);
				    }
				
		
		// ---------------------------------------------------------------------------------------
		// CENTROIDE
		// ---------------------------------------------------------------------------------------		
	    private boolean recalculateCluster(){
	        boolean done = true;

	        for(Cluster cluster: clusters) {
	        	if(this.areSignals) {
	        		ClusterSig cl = (ClusterSig) cluster;
	        		double[] new_value = cl.calculateValue();
	        		double[] old_value = cl.getCentral_values();
	                cl.setCentral_values(new_value);

	                double result = (new_value[0] - old_value[0]) + (new_value[1] - old_value[1]);
	                
	                if(Math.abs(result) > TOL) { //Nivel de tolerancia para ver si los clusters ya están estabilizados
	                    done = false;
	                }
	        	}
	        	else {
	        		ClusterImg cl = (ClusterImg) cluster;
	        		double new_value = cl.calculateValue();
	        		double value = cl.getCentral_value();
	        		cl.setCentral_value(new_value);
	        		
	        		if(Math.abs(new_value - value) > TOL) {
	        			done = false;
	        		}
	        	}
	        }

	        return done;
	    }
		
}
