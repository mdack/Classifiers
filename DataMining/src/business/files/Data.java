package business.files;

import java.util.List;

import business.elements.Image;
import business.elements.Signal;
import business.transfers.TResult;

public interface Data {
	public List<Image> readImages();
	
	public List<Signal> readSignals();
	
	public void writeCluster(TResult transfer);
}
