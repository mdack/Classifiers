package presentation.command.classifiers;

import business.classifiers.hierarchical.Hierarchical;
import business.factory.FactoryAS;
import business.transfers.THierarchical;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandHierarchical implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		Hierarchical alg = factory.executeHierarchical();
		TResult t = alg.executeHierarchical((THierarchical)datos);
		
		Context contexto = new Context();
		contexto.setDatos(t);
		
		int event = BusinessEvent.HIERARCHICAL;
		if(t != null) 
			contexto.setEvento(event + DispatcherResults.HierarchicalCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.HierarchicalError);
		
		return contexto;
	}

}
