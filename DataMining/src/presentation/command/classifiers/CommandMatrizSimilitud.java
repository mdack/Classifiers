package presentation.command.classifiers;

import business.classifiers.matrizsimilitud.MatrizSim;
import business.factory.FactoryAS;
import business.transfers.TMatrizSim;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandMatrizSimilitud implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		MatrizSim kmeans = factory.executeMatrizSimilitud();
		TResult id = kmeans.executeAlgorithm((TMatrizSim)datos);
		
		Context contexto = new Context();
		contexto.setDatos(id);
		int event = BusinessEvent.MATRIZSIMILITUD;
		if(id != null) 
			contexto.setEvento(event + DispatcherResults.MatrizCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.MatrizError);
		
		return contexto;
	}

}
