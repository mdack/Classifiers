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
		Context context_1 = (Context) datos;
		TResult id = alg.executeAlgorithm((TBatchelorWilkins)context_1.getDatos());
		
		Context contexto = new Context();
		contexto.setDatos(id);
		int event = BusinessEvent.BATCHELOR_WILKINS;
		if(id != null) 
			contexto.setEvento(event + DispatcherResults.BatchelorWilkinsCorrect);
		else 
			contexto.setEvento(event + DispatcherResults.BatchelorWilkinsError);
		
		return contexto;
	}

}
