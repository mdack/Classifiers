package presentation.command.classifiers;

import business.classifiers.hierarchical.Hierarchical;
import business.factory.FactoryAS;
import business.transfers.THierarchical;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandHierarchical implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		Hierarchical alg = factory.executeHierarchical();
		Context context_1 = (Context) datos;
		TResult id = alg.executeHierarchical((THierarchical)context_1.getDatos());
		
		Context contexto = new Context();
		contexto.setDatos(id);
		if(id != null) 
			contexto.setEvento(DispatcherResults.HierarchicalCorrect);
		else 
			contexto.setEvento(DispatcherResults.HierarchicalError);
		
		return contexto;
	}

}
