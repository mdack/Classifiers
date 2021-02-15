package business.factory;

import java.io.InputStream;
import java.util.List;

import business.classifiers.kmeans.KMeans;
import business.classifiers.kmeans.KMeansImp;
import business.files.Data;
import business.files.DataImp;
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

	@Override
	public Data readData(List<InputStream> list) {
		// TODO Auto-generated method stub
		return new DataImp(list);
	}
	

}
