package business.transfers;

import java.util.ArrayList;
import java.util.List;

import business.classifiers.cluster.Cluster;
import business.classifiers.hierarchical.LinkageStrategy;

public class TResult {
	private int N;
	private List<Cluster> list = new ArrayList<>();
	private LinkageStrategy strategy;
	private Cluster root_cluster;
	private boolean cluster_rejection;
	
	public int getN() {
		return N;
	}
	public void setN(int n) {
		N = n;
	}
	public List<Cluster> getList() {
		return list;
	}
	public void setList(List<Cluster> list) {
		this.list = list;
	}
	public LinkageStrategy getStrategy() {
		return strategy;
	}
	public void setStrategy(LinkageStrategy strategy) {
		this.strategy = strategy;
	}
	public Cluster getRoot_cluster() {
		return root_cluster;
	}
	public void setRoot_cluster(Cluster root_cluster) {
		this.root_cluster = root_cluster;
	}
	
	public String toString() {
		String cad = "Hay " + N + " clusters:\n";
		int size = list.size();
		
		if(this.cluster_rejection) {
			size-=1;
			
			if(list.get(size).getImages() != null) {
				cad += "El cluster de rechazo contiene " + list.get(size).getImages().size() + " patrones.\n";
			}else {
				cad += "El cluster de rechazo contiene " + list.get(size).getSignals().size() + " patrones.\n";
			}
		}
		
		
		for(int i = 0; i < size; i++) {
			if(list.get(i) != null)
				cad += list.get(i).toString() + "\n";
		}
		
		return cad;	
	}
	public boolean isCluste_rejection() {
		return cluster_rejection;
	}
	public void setCluste_rejection(boolean cluste_rejection) {
		this.cluster_rejection = cluste_rejection;
	}
	
	
}
