package presentation.command.classifiers;

import business.classifiers.agrupamientosecuencial.AgrupamientoSec;
import business.factory.FactoryAS;
import business.transfers.TAgrupamientoSec;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandAgrupamientoSec implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		AgrupamientoSec alg = factory.executeAgrupamientoSec();
		Context context_1 = (Context) datos;
		TResult id = alg.executeAlgorithm((TAgrupamientoSec)context_1.getDatos());
		
		Context contexto = new Context();
		contexto.setDatos(id);
		if(id != null) 
			contexto.setEvento(DispatcherResults.Agrupamientocorrect);
		else 
			contexto.setEvento(DispatcherResults.AgrupamientoError);
		
		return contexto;
	}

}
