package business.transfers;

public class TMatrizSim {
	private double O;
	private TZip tZip;
	
	public TMatrizSim(double o2, TZip tZip2) {
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
	
	
}
