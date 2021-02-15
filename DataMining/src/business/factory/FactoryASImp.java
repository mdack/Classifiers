package business.factory;

import business.classifiers.kmeans.KMeans;
import business.classifiers.kmeans.KMeansImp;
import business.files.Zip;
import business.files.ZipImp;

public class FactoryASImp extends FactoryAS {

	@Override
	public KMeans executeKmeans() {
		// TODO Auto-generated method stub
		return new KMeansImp();
	}

	@Override
	public Zip readZip(String path) {
		// TODO Auto-generated method stub
		return new ZipImp(path);
	}

}
