package presentation.controller;

import presentation.command.Command;
import presentation.command.commandFactory.CommandFactory;
import presentation.dispatcher.Context;
import presentation.dispatcher.Dispatcher;

public class ControllerImp extends Controller {
	
	public void action(Context contexto) {
		Context context = null;
		CommandFactory factoriaComando = CommandFactory.getInstance(true);
		Object evento = contexto.getEvento();
		Command comando = factoriaComando.getCommand((int)evento);
		context = comando.execute(contexto.getDatos());

		Dispatcher dispatcher = Dispatcher.getInstance(true);
		dispatcher.update(context);
	}
}
