package presentation.command.commandFactory;

import presentation.command.Command;
import presentation.controller.BusinessEvent;

public class CommandFactoryImp extends CommandFactory {
	
	public Command getCommand(int CommandName) {
		
		switch(CommandName){
		case BusinessEvent.BATCHELOR_WILKINS:
			return null;
		case BusinessEvent.BAYES:
			return null;
		case BusinessEvent.HIERARCHICAL:
			return null;				
		case BusinessEvent.KMEANS:
			return null;
		case BusinessEvent.KNN:
		}
		
		return null;
	}



	
}