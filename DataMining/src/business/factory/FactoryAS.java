package business.factory;

import business.classifiers.kmeans.KMeansImp;

public abstract class FactoryAS {
	private static FactoryAS instance;

	public static FactoryAS getInstance() {
		if(instance == null)
			return instance = new FactoryASImp();
		else
			return instance;
	}

	public abstract KMeansImp executeKmeans();
}
