package business.transfers;

public class TAgrupamientoSec {
	private int K, M;
	private double R, C, T;
	private TZip tZip;
	
	public TAgrupamientoSec(int k2, double r2, double c2, int m2, double t2, TZip tZip2) {
		K = k2;
		R = r2;
		C = c2;
		M = m2;
		T = t2;
		tZip = tZip2;
	}
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K = k;
	}
	public double getR() {
		return R;
	}
	public void setR(int r) {
		R = r;
	}
	public double getC() {
		return C;
	}
	public void setC(int c) {
		C = c;
	}
	public int getM() {
		return M;
	}
	public void setM(int m) {
		M = m;
	}
	public double getT() {
		return T;
	}
	public void setT(int t) {
		T = t;
	}
	public TZip gettZip() {
		return tZip;
	}
	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
	
}
