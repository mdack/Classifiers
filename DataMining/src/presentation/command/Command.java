package presentation.command;

import presentation.dispatcher.Context;

public interface Command {

	public Context execute(Object datos);
}