package business.factory;

import java.io.InputStream;
import java.util.List;

import business.classifiers.agrupamientosecuencial.AgrupamientoSec;
import business.classifiers.agrupamientosecuencial.AgrupamientoSecImp;
import business.classifiers.batchelorwilkins.BatchelorWilkins;
import business.classifiers.batchelorwilkins.BatchelorWilkinsImp;
import business.classifiers.hierarchical.Hierarchical;
import business.classifiers.hierarchical.HierarchicalImp;
import business.classifiers.kmeans.KMeans;
import business.classifiers.kmeans.KMeansImp;
import business.classifiers.matrizsimilitud.MatrizSim;
import business.classifiers.matrizsimilitud.MatrizSimImpl;
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
	public Data readData(List<InputStream> list, List<String> names) {
		// TODO Auto-generated method stub
		return new DataImp(list, names);
	}

	@Override
	public AgrupamientoSec executeAgrupamientoSec() {
		// TODO Auto-generated method stub
		return new AgrupamientoSecImp();
	}

	@Override
	public BatchelorWilkins executeBatchelorWilkins() {
		// TODO Auto-generated method stub
		return new BatchelorWilkinsImp();
	}

	@Override
	public MatrizSim executeMatrizSimilitud() {
		// TODO Auto-generated method stub
		return new MatrizSimImpl();
	}

	@Override
	public Hierarchical executeHierarchical() {
		// TODO Auto-generated method stub
		return new HierarchicalImp();
	}
	

}
