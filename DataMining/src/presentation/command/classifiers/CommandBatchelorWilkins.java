package presentation.command.classifiers;

import business.classifiers.batchelorwilkins.BatchelorWilkins;
import business.factory.FactoryAS;
import business.transfers.TBatchelorWilkins;
import business.transfers.TResult;
import presentation.command.Command;
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
		if(id != null) 
			contexto.setEvento(DispatcherResults.BatchelorWilkinsCorrect);
		else 
			contexto.setEvento(DispatcherResults.BatchelorWilkinsError);
		
		return contexto;
	}

}
