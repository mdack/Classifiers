package business.transfers;

public class TMatrizSim {
	private int O;
	private TZip tZip;
	
	public TMatrizSim(int o2, TZip tZip2) {
		O = o2;
		tZip = tZip2;
	}
	public int getO() {
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
