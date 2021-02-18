package presentation.command.classifiers;

import business.classifiers.matrizsimilitud.MatrizSim;
import business.factory.FactoryAS;
import business.transfers.TMatrizSim;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandMatrizSimilitud implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		MatrizSim kmeans = factory.executeMatrizSimilitud();
		Context context_1 = (Context) datos;
		TResult id = kmeans.executeAlgorithm((TMatrizSim)context_1.getDatos());
		
		Context contexto = new Context();
		contexto.setDatos(id);
		if(id != null) 
			contexto.setEvento(DispatcherResults.MatrizCorrect);
		else 
			contexto.setEvento(DispatcherResults.MatrizError);
		
		return contexto;
	}

}
