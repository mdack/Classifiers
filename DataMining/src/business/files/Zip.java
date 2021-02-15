package business.files;

import business.transfers.TZip;

public interface Zip {
	public void unZip(String pathUnZip);
	
	public TZip readZip(String pathTarget);
}
