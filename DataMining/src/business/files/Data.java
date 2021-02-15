package business.files;

import java.util.List;

import business.elements.Image;
import business.elements.Signal;

public interface Data {
	public List<Image> readImages();
	
	public List<Signal> readSignals();
}
