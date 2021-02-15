package business.transfers;

public class TKMeans {
	private final static int NUM_CLUSTERS = 6;
	
	int K;
	int tInit;
	TZip tZip;
	
	public TKMeans() {
		K = NUM_CLUSTERS;
		tInit = 0;
	}
	
	public TKMeans(int k, int init, TZip tz) {
		K = k;
		tInit = init;
		tZip = tz;
	}
	
	public int getK() {
		return K;
	}
	public void setK(int k) {
		K = k;
	}
	public int gettInit() {
		return tInit;
	}
	public void settInit(int tInit) {
		this.tInit = tInit;
	}
	public TZip gettZip() {
		return tZip;
	}

	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
}
