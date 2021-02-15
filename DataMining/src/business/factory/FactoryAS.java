package business.factory;

import java.io.InputStream;
import java.util.List;

import business.classifiers.kmeans.KMeans;
import business.files.Data;
import business.files.Zip;

public abstract class FactoryAS {
	private static FactoryAS instance;

	public static FactoryAS getInstance() {
		if(instance == null)
			return instance = new FactoryASImp();
		else
			return instance;
	}
	
	public abstract Zip readZip(String path);
	
	public abstract KMeans executeKmeans();
	
	public abstract Data readData(List<InputStream> list);
}
