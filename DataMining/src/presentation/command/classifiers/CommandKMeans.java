package presentation.command.classifiers;

import business.classifiers.kmeans.Cluster;
import business.classifiers.kmeans.KMeans;
import business.factory.FactoryAS;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandKMeans implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		KMeans kmeans = factory.executeKmeans();
		Cluster id = kmeans.executeKMeans();
		
		Context contexto = new Context();
		contexto.setDatos(id);
		if(id != null) 
			contexto.setEvento(DispatcherResults.KMeansCorrecto);
		else 
			contexto.setEvento(DispatcherResults.KMeansError);
		
		return contexto;
	}

}
