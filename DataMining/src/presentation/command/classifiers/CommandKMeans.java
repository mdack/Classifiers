package presentation.command.classifiers;

import business.classifiers.kmeans.KMeans;
import business.factory.FactoryAS;
import business.transfers.TKMeans;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandKMeans implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		KMeans kmeans = factory.executeKmeans();
		TResult id = kmeans.executeKMeans((TKMeans)datos);
		
		Context contexto = new Context();
		contexto.setDatos(id);
		
		int event = BusinessEvent.KMEANS;
		if(id != null) 
			contexto.setEvento(event + DispatcherResults.KMeansCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.KMeansError);
		
		return contexto;
	}

}
