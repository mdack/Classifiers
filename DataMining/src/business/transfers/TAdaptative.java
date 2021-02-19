package business.transfers;

public class TAdaptative {
	
	private double T;
	private double O;
	private TZip zip;
	
	public TAdaptative(double t, double o, TZip zip) {
		super();
		T = t;
		O = o;
		this.zip = zip;
	}

	public double getT() {
		return T;
	}

	public void setT(double t) {
		T = t;
	}

	public double getO() {
		return O;
	}

	public void setO(double o) {
		O = o;
	}

	public TZip getZip() {
		return zip;
	}

	public void setZip(TZip zip) {
		this.zip = zip;
	}	
}
