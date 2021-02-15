package presentation.command.file;

import business.factory.FactoryAS;
import business.files.Zip;
import business.transfers.TZip;
import presentation.command.Command;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

public class CommandFile implements Command{

	@Override
	public Context execute(Object datos) {
		FactoryAS factory = FactoryAS.getInstance();
		
		Zip zip = factory.readZip((String) datos);
		TZip transfer = zip.readZip((String) datos);
		
		Context context = new Context();
		context.setDatos(transfer);
		
		if(transfer != null)
			context.setEvento(DispatcherResults.readZipOK);
		else
			context.setEvento(DispatcherResults.readZipError);
		
		return context;
	}

}
