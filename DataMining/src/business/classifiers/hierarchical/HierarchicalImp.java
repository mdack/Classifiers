package business.classifiers.hierarchical;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterImg;
import business.classifiers.cluster.ClusterPair;
import business.classifiers.cluster.ClusterSig;
import business.classifiers.cluster.Distance;
import business.classifiers.hierarchical.strategy.AverageLinkageStrategy;
import business.classifiers.hierarchical.strategy.CompleteLinkageStrategy;
import business.classifiers.hierarchical.strategy.DistanceMap;
import business.classifiers.hierarchical.strategy.LinkageStrategy;
import business.classifiers.hierarchical.strategy.SingleLinkageStrategy;
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.THierarchical;
import business.transfers.TResult;
import presentation.views.mainview.MainView;

public class HierarchicalImp implements Hierarchical{

    private List<Image> imgs;
    private List<Signal> signals;
    private DistanceMap distances;
    private List<Cluster> clusters = new ArrayList<Cluster>();
    private int globalClusterIndex = 0;
	private int total_files;
	private boolean areSignals = true;
	private double[][] m_distances;
	
	@Override
	public TResult executeHierarchical(THierarchical transfer) {
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		
		Data data = FactoryAS.getInstance().readData2(transfer.gettZip().getList());
		
		MainView.getInstance().UpdateArea("Obteniendo información de archivos : " + hourdateFormat.format(date) + "\n");
		
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
		MainView.getInstance().UpdateArea("Empieza el algoritmo Jerárquico aglomerativo : " + hourdateFormat.format(date) + "\n");
		
		m_distances = new double[total_files][total_files];
		
		//Calcular las distancias entre todas las parejas de patrones
		if(this.areSignals)
			calculateDistancesSig();
		else
			calculateDistancesImgs();
		
		createClusters();
		this.distances = createLinkages();
		LinkageStrategy strategy = getLinkStrategy(transfer);
		
		while (!isTreeComplete()) {
			agglomerate(strategy);
		}
		
		
        MainView.getInstance().UpdateArea("El algoritmo ha terminado : " + hourdateFormat.format(date) + "\n");
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setN(clusters.size());
		result.setRoot_cluster(clusters.get(0));
		result.setStrategy(strategy);
		
		return result;
	}
	
	// ---------------------------------------------------------------------------------
	// INICIALIZACION
	// ---------------------------------------------------------------------------------
		
	private void calculateDistancesSig() {
		for(int i = 0; i < signals.size(); i++) {
			for(int j = 0; j < signals.size(); j++) {
					double dist = signals.get(i).calculateDistanceTo(signals.get(j));
					m_distances[i][j] = dist;
			}
		}
		
	}
	
	private void calculateDistancesImgs() {
		for(int i = 0; i < imgs.size(); i++) {
			for(int j = 0; j < imgs.size(); j++) {
					double dist = imgs.get(i).calculateDistanceTo(imgs.get(j));
					m_distances[i][j] = dist;
			}
		}		
	}
	
	private void createClusters() {

		for (int i = 0; i < this.total_files; i++) {
			Cluster cluster = null;
			
			if(this.areSignals) {
				signals.get(i).setId_cluster(i);
				cluster = new ClusterSig(i, signals.get(i));
			}
			else {
				imgs.get(i).setId_cluster(i);
				cluster = new ClusterImg(i, imgs.get(i));
			}
			
			clusters.add(cluster);
		}
	}
	
	// ---------------------------------------------------------------------------------
	// BUCLE
	// ---------------------------------------------------------------------------------
	
	public void agglomerate(LinkageStrategy linkageStrategy) {
        ClusterPair minDistLink = distances.removeFirst();
        
        if (minDistLink != null) {
            clusters.remove(minDistLink.getrCluster());
            clusters.remove(minDistLink.getlCluster());

            Cluster oldClusterL = minDistLink.getlCluster();
            Cluster oldClusterR = minDistLink.getrCluster();
            Cluster newCluster = minDistLink.agglomerate(globalClusterIndex++, this.areSignals);

            for (Cluster cluster : clusters) {
                ClusterPair link1 = findByClusters(cluster, oldClusterL);
                ClusterPair link2 = findByClusters(cluster, oldClusterR);
                ClusterPair newLinkage = new ClusterPair();
                newLinkage.setlCluster(cluster);
                newLinkage.setrCluster(newCluster);
                Collection<Distance> distanceValues = new ArrayList<Distance>();

                if (link1 != null) {
                    double distVal = link1.getLinkageDistance();
                    double weightVal = 0;   
                    
                    if(!this.areSignals) {
                    	ClusterImg clImg = (ClusterImg) link1.getOtherCluster(cluster);
                    	weightVal = clImg.calculateValue();
                    }else {
                    	ClusterSig clSig = (ClusterSig) link1.getOtherCluster(cluster);
                    	double[] weightVals = clSig.calculateValue();
                    	weightVal = weightVals[0] + weightVals[1];
                    }
                    
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link1);
                }
                if (link2 != null) {
                    double distVal = link2.getLinkageDistance();
                    double weightVal = 0;
                    
                    if(!this.areSignals) {
                    	ClusterImg clImg = (ClusterImg) link2.getOtherCluster(cluster);
                    	weightVal = clImg.calculateValue();
                    }else {
                    	ClusterSig clSig = (ClusterSig) link2.getOtherCluster(cluster);
                    	double[] weightVals = clSig.calculateValue();
                    	weightVal = weightVals[0] + weightVals[1];
                    }
                    
                    distanceValues.add(new Distance(distVal, weightVal));
                    distances.remove(link2);
                }

                Distance newDistance = linkageStrategy.calculateDistance(distanceValues);

                newLinkage.setLinkageDistance(newDistance.getDistance());
                distances.add(newLinkage);
            }
            clusters.add(newCluster);
        }
    }
	
	// ---------------------------------------------------------------------------------
	// ESTRATEGIA
	// ---------------------------------------------------------------------------------
	
	private LinkageStrategy getLinkStrategy(THierarchical transfer) {
		LinkageStrategy ls = null;
		switch(transfer.getTipo_link()) {
		case 0:
			ls = new SingleLinkageStrategy();
			break;
		case 1:
			ls = new CompleteLinkageStrategy();
			break;
		case 2:
			ls = new AverageLinkageStrategy();
			break;
		}
		return ls;
	}
	
	// ---------------------------------------------------------------------------------
	// DISTANCIAS
	// ---------------------------------------------------------------------------------
	
	    private DistanceMap createLinkages() {
	        DistanceMap linkages = new DistanceMap();
	        for (int col = 0; col < clusters.size(); col++) {
	            for (int row = col + 1; row < clusters.size(); row++) {
	            	int index = accessFunction(row, col, clusters.size());
	            	if(index < clusters.size()) {
		                ClusterPair link = new ClusterPair();
		                Double d = m_distances[0][index];
		                link.setLinkageDistance(d);
		                link.setlCluster(clusters.get(col));
		                link.setrCluster(clusters.get(row));
		                linkages.add(link);
	            	}
	            }
	        }
	        return linkages;
	    }
	
	 public DistanceMap getDistances() {
	        return distances;
	    }

	    public List<Cluster> flatAgg(LinkageStrategy linkageStrategy, Double threshold)
	    {
	        while((!isTreeComplete()) && (distances.minDist() != null) && (distances.minDist() <= threshold))
	        {
	            agglomerate(linkageStrategy);
	        }
	        
	        return clusters;
	    }

		private static int accessFunction(int i, int j, int n) {
			return n * j - j * (j + 1) / 2 + i - 1 - j;
		}
	    

	    private ClusterPair findByClusters(Cluster c1, Cluster c2) {
	        return distances.findByCodePair(c1, c2);
	    }

	    public boolean isTreeComplete() {
	        return clusters.size() == 1;
	    }
}
