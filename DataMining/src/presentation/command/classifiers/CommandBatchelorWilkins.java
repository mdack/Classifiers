package presentation.command.classifiers;

import business.classifiers.batchelorwilkins.BatchelorWilkins;
import business.factory.FactoryAS;
import business.transfers.TBatchelorWilkins;
import business.transfers.TResult;
import presentation.command.Command;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandBatchelorWilkins implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		BatchelorWilkins alg = factory.executeBatchelorWilkins();
		TResult t = alg.executeAlgorithm((TBatchelorWilkins)datos);
		
		Context contexto = new Context();
		contexto.setDatos(t);
		
		int event = BusinessEvent.BATCHELOR_WILKINS;
		if(t != null) 
			contexto.setEvento(event + DispatcherResults.BatchelorWilkinsCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.BatchelorWilkinsError);
		
		return contexto;
	}

}
