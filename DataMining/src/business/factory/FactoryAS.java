package business.factory;

import java.util.List;

import business.classifiers.adaptative.Adaptative;
import business.classifiers.agrupamientosecuencial.AgrupamientoSec;
import business.classifiers.batchelorwilkins.BatchelorWilkins;
import business.classifiers.hierarchical.Hierarchical;
import business.classifiers.kmeans.KMeans;
import business.classifiers.matrizsimilitud.MatrizSim;
import business.elements.FileData;
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
	
	public abstract AgrupamientoSec executeAgrupamientoSec();
	
	public abstract BatchelorWilkins executeBatchelorWilkins();
	
	public abstract MatrizSim executeMatrizSimilitud();
	
	public abstract Hierarchical executeHierarchical();
	
	public abstract Adaptative executeAdaptative();
		
	public abstract Data readData2(List<FileData> list);
	
	public abstract Data writeResult();
}
