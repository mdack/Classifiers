package presentation.dispatcher;

import presentation.views.adaptative.JAdaptative;
import presentation.views.agrupamientosecuencial.JAgrupamientoSec;
import presentation.views.batchelorwilkins.JBatchelorWilkins;
import presentation.views.hierarchical.JHierarchical;
import presentation.views.kmeans.JKMeans;
import presentation.views.mainview.MainView;
import presentation.views.matrizsimilitud.JMatrizSimilitud;

public class DispatcherImp extends Dispatcher {


	@Override
	public void update(Context contexto) {
				
		int evento = (int) contexto.getEvento() - 100;
		int aux = (int) contexto.getEvento();
		
		MainView main;
		JKMeans kmeans;
		JAgrupamientoSec as;
		JBatchelorWilkins bw;
		JMatrizSimilitud ms;
		JHierarchical h;
		JAdaptative a;
		
		if(evento < 0) {
			main= MainView.getInstance();
			main.update(contexto);	
		}else if(evento > 0 && evento < 100) {
			a = JAdaptative.getInstance();
			contexto.setEvento(evento);
			a.update(contexto);
		}else if(evento > 100 && evento < 200) {
			bw = JBatchelorWilkins.getInstance();
			contexto.setEvento(aux - 200);
			bw.update(contexto);
		}
		else if(evento > 200 && evento < 300) {
			h = JHierarchical.getInstance();
			contexto.setEvento(aux - 300);
			h.update(contexto);
		}else if(evento > 300 && evento < 400) {
			kmeans = JKMeans.getInstance();
			contexto.setEvento(aux - 400);
			kmeans.update(contexto);
		}else if(evento > 400 && evento < 500) {
			as = JAgrupamientoSec.getInstance();
			contexto.setEvento(aux - 500);
			as.update(contexto);
		}else if(evento > 500){
			ms = JMatrizSimilitud.getInstance();
			contexto.setEvento(aux - 600);
			ms.update(contexto);
		}
	}
}