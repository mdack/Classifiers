package presentation.controller;

import presentation.command.Command;
import presentation.command.commandFactory.CommandFactory;
import presentation.dispatcher.Context;
import presentation.dispatcher.Dispatcher;

public class ControllerImp extends Controller {
	
	@Override
	public void action(Context ct) {
		Context context = null;
		
		CommandFactory factoriaComando = CommandFactory.getInstance(true);
		Object evento = ct.getEvento();
		Command comando = factoriaComando.getCommand((int)evento);
		
		context = comando.execute(ct.getDatos());

		Dispatcher dispatcher = Dispatcher.getInstance(true);
		dispatcher.update(context);
	}
}
