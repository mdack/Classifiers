package business.factory;

import business.classifiers.kmeans.KMeans;
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
}
