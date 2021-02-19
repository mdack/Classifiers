package presentation.command.classifiers;

import business.classifiers.agrupamientosecuencial.AgrupamientoSec;
import business.factory.FactoryAS;
import business.transfers.TAgrupamientoSec;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandAgrupamientoSec implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		AgrupamientoSec alg = factory.executeAgrupamientoSec();
		TResult id = alg.executeAlgorithm((TAgrupamientoSec)datos);
		
		Context contexto = new Context();
		contexto.setDatos(id);
		int event = BusinessEvent.AGRUPAMIENTO_SECUENCIAL;
		if(id != null) 
			contexto.setEvento(event + DispatcherResults.Agrupamientocorrect);
		else 
			contexto.setEvento(event + DispatcherResults.AgrupamientoError);
		
		return contexto;
	}

}
