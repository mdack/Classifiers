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
		for(Cluster cl: list) {
			cad += cl.toString() + "\n";
		}
		return cad;	
	}
	
	
}
