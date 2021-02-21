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
import business.elements.Image;
import business.elements.Signal;
import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.THierarchical;
import business.transfers.TResult;

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
		
		System.out.println("Obteniendo información de archivos : " + hourdateFormat.format(date));
		if(transfer.gettZip().isAreSignals()) {
			this.signals = data.readSignals();
			this.total_files = signals.size();
		}else {
			this.imgs = data.readImages();
			this.total_files = imgs.size();
			this.areSignals = false;
		}
		System.out.println("Se han cargado todos los archivos : " + hourdateFormat.format(date));
		
		System.out.println("Empieza el algoritmo Jerárquico aglomerativo : " + hourdateFormat.format(date));
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
		
		
		System.out.println("El algoritmo ha terminado : " + hourdateFormat.format(date));
		
		TResult result = new TResult();
		result.setList(clusters);
		result.setN(clusters.size());
		result.setRoot_cluster(clusters.get(0));
		result.setStrategy(strategy);
		
		return result;
	}
	
	private LinkageStrategy getLinkStrategy(THierarchical transfer) {
		LinkageStrategy ls = null;
		switch(transfer.getTipo_link()) {
		case 0:
			ls = new SingleLinkageStrategy();
			break;
		case 1:
			ls = new CompleteLinkageStrategy();
			break;
		}
		return ls;
	}

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
	
	    private DistanceMap createLinkages() {
	        DistanceMap linkages = new DistanceMap();
	        for (int col = 0; col < clusters.size(); col++) {
	            Cluster cluster_col = clusters.get(col);
	            for (int row = col + 1; row < clusters.size(); row++) {
	            	int index = accessFunction(row, col, clusters.size());
	            	if(index < clusters.size()) {
		                ClusterPair link = new ClusterPair();
		                Double d = m_distances[0][index];
		                link.setLinkageDistance(d);
		                link.setlCluster(cluster_col);
		                link.setrCluster(clusters.get(row));
		                linkages.add(link);
	            	}
	            }
	        }
	        return linkages;
	    }
		
	private void createClusters() {

		for (int i = 0; i < this.total_files; i++) {
			Cluster cluster = null;
			
			if(this.areSignals) {
				cluster = new ClusterSig(i, signals.get(i));
			}
			else {
				cluster = new ClusterImg(i, imgs.get(i));
			}
			
			clusters.add(cluster);
		}
	}

	private static int accessFunction(int i, int j, int n) {
		return n * j - j * (j + 1) / 2 + i - 1 - j;
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

	    public void agglomerate(LinkageStrategy linkageStrategy) {
	        ClusterPair minDistLink = distances.removeFirst();
	        if (minDistLink != null) {
	            clusters.remove(minDistLink.getrCluster());
	            clusters.remove(minDistLink.getlCluster());

	            Cluster oldClusterL = minDistLink.getlCluster();
	            Cluster oldClusterR = minDistLink.getrCluster();
	            Cluster newCluster = minDistLink.agglomerate(++globalClusterIndex);

	            for (Cluster iClust : clusters) {
	                ClusterPair link1 = findByClusters(iClust, oldClusterL);
	                ClusterPair link2 = findByClusters(iClust, oldClusterR);
	                ClusterPair newLinkage = new ClusterPair();
	                newLinkage.setlCluster(iClust);
	                newLinkage.setrCluster(newCluster);
	                Collection<Distance> distanceValues = new ArrayList<Distance>();

	                if (link1 != null) {
	                    Double distVal = link1.getLinkageDistance();
	                    //Double weightVal = (double) link1.getOtherCluster(iClust).calculateValue();
	                    //distanceValues.add(new Distance(distVal, weightVal));
	                    distances.remove(link1);
	                }
	                if (link2 != null) {
	                    Double distVal = link2.getLinkageDistance();
	                    //Double weightVal = (double) link2.getOtherCluster(iClust).calculateValue();
	                    //distanceValues.add(new Distance(distVal, weightVal));
	                    distances.remove(link2);
	                }

	                Distance newDistance = linkageStrategy.calculateDistance(distanceValues);

	                newLinkage.setLinkageDistance(newDistance.getDistance());
	                distances.add(newLinkage);
	            }
	            clusters.add(newCluster);
	        }
	    }

	    private ClusterPair findByClusters(Cluster c1, Cluster c2) {
	        return distances.findByCodePair(c1, c2);
	    }

	    public boolean isTreeComplete() {
	        return clusters.size() == 1;
	    }
}
