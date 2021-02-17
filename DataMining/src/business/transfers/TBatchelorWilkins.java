package business.transfers;

import java.util.ArrayList;
import java.util.List;

import business.classifiers.cluster.Cluster;

public class TBatchelorWilkins {
	private double O;
	private TZip tZip;
	private int N; //nº agrupamientos
	List<Cluster> list = new ArrayList<>();
	
	public TBatchelorWilkins(double o2, TZip tZip2) {
		O = o2;
		tZip = tZip2;
	}
	public double getO() {
		return O;
	}
	public void setO(int o) {
		O = o;
	}
	public TZip gettZip() {
		return tZip;
	}
	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
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
