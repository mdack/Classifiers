package presentation.command.classifiers;

import business.classifiers.kmeans.Cluster;
import business.classifiers.kmeans.KMeans;
import business.factory.FactoryAS;
import business.transfers.TKMeans;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandKMeans implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		KMeans kmeans = factory.executeKmeans();
		Context context_1 = (Context) datos;
		Cluster id = kmeans.executeKMeans((TKMeans)context_1.getDatos());
		
		Context contexto = new Context();
		contexto.setDatos(id);
		if(id != null) 
			contexto.setEvento(DispatcherResults.KMeansCorrect);
		else 
			contexto.setEvento(DispatcherResults.KMeansError);
		
		return contexto;
	}

}
