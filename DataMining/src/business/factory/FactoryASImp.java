package business.factory;

import java.util.List;

import business.classifiers.adaptative.Adaptative;
import business.classifiers.adaptative.AdaptativeImp;
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
import business.elements.FileData;
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

	@Override
	public Data readData2(List<FileData> list) {
		// TODO Auto-generated method stub
		return new DataImp(list);
	}

	@Override
	public Adaptative executeAdaptative() {
		// TODO Auto-generated method stub
		return new AdaptativeImp();
	}
	

}
