package presentation.command.commandFactory;

import presentation.command.Command;
import presentation.command.classifiers.CommandAgrupamientoSec;
import presentation.command.classifiers.CommandBatchelorWilkins;
import presentation.command.classifiers.CommandHierarchical;
import presentation.command.classifiers.CommandKMeans;
import presentation.command.file.CommandFile;
import presentation.controller.BusinessEvent;

public class CommandFactoryImp extends CommandFactory {
	
	@Override
	public Command getCommand(int CommandName) {
		
		switch(CommandName){
		case BusinessEvent.BATCHELOR_WILKINS:
			return new CommandBatchelorWilkins();
		case BusinessEvent.READ_ZIP:
			return new CommandFile();
		case BusinessEvent.HIERARCHICAL:
			return new CommandHierarchical();				
		case BusinessEvent.KMEANS:
			return new CommandKMeans();
		case BusinessEvent.AGRUPAMIENTO_SECUENCIAL:
			return new CommandAgrupamientoSec();
		}
		
		return null;
	}



	
}