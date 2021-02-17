package business.transfers;

import java.util.ArrayList;
import java.util.List;

import business.classifiers.cluster.Cluster;

public class TResult {
	private int N;
	private List<Cluster> list = new ArrayList<>();
	
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
	
	
}
