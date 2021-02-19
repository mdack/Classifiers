package presentation.command.classifiers;

import business.classifiers.adaptative.Adaptative;
import business.factory.FactoryAS;
import business.transfers.TAdaptative;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandAdaptative implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		Adaptative alg = factory.executeAdaptative();
		TResult t = alg.executeAlgorithm((TAdaptative)datos);
		
		Context contexto = new Context();
		contexto.setDatos(t);
		
		int event = BusinessEvent.ADAPTATIVE;
		if(t != null) 
			contexto.setEvento(event + DispatcherResults.AdaptativeCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.AdaptativeError);
		
		return contexto;
	}

}
