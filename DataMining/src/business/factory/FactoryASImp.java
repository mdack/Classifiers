package business.factory;

import business.classifiers.kmeans.KMeansImp;

public class FactoryASImp extends FactoryAS {

	@Override
	public KMeansImp executeKmeans() {
		// TODO Auto-generated method stub
		return new KMeansImp();
	}

}
