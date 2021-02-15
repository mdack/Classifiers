package presentation.dispatcher;

import presentation.controller.BusinessEvent;
import presentation.views.MainView;
import presentation.views.kmeans.JKMeans;

public class DispatcherImp extends Dispatcher {


	@Override
	public void update(Context contexto) {
				
		int evento = (int) contexto.getEvento();
		
		MainView main;
		JKMeans kmeans;
		
		switch (evento) {
		
		case BusinessEvent.READ_ZIP:
			main= MainView.getInstance();
			main.update(contexto.getDatos());	
			break;	
		case BusinessEvent.KMEANS:
			kmeans = JKMeans.getInstance(BusinessEvent.KMEANS);
			kmeans.update(contexto.getDatos());
			break;
		}
	}
}