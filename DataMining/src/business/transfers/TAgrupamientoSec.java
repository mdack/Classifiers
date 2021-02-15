package business.transfers;

public class TAgrupamientoSec {
	private int K, R, C, M, T;
	private int tMezcla;
	private TZip tZip;
	
	public TAgrupamientoSec(int k2, int r2, int c2, int m2, int t2, int tMezcla2, TZip tZip2) {
		K = k2;
		R = r2;
		C = c2;
		M = m2;
		T = t2;
		tMezcla = tMezcla2;
		tZip = tZip2;
	}
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K = k;
	}
	public int getR() {
		return R;
	}
	public void setR(int r) {
		R = r;
	}
	public int getC() {
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
	public int getT() {
		return T;
	}
	public void setT(int t) {
		T = t;
	}
	public int gettMezcla() {
		return tMezcla;
	}
	public void settMezcla(int tMezcla) {
		this.tMezcla = tMezcla;
	}
	public TZip gettZip() {
		return tZip;
	}
	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
	
}
