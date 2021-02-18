package presentation.dispatcher;

import presentation.controller.BusinessEvent;
import presentation.views.MainView;
import presentation.views.agrupamientosecuencial.JAgrupamientoSec;
import presentation.views.batchelorwilkins.JBatchelorWilkins;
import presentation.views.hierarchical.JHierarchical;
import presentation.views.kmeans.JKMeans;
import presentation.views.matrizsimilitud.JMatrizSimilitud;

public class DispatcherImp extends Dispatcher {


	@Override
	public void update(Context contexto) {
				
		int evento = (int) contexto.getEvento();
		
		MainView main;
		JKMeans kmeans;
		JAgrupamientoSec as;
		JBatchelorWilkins bw;
		JMatrizSimilitud ms;
		JHierarchical h;
		
		switch (evento) {
		
		case DispatcherResults.readZipOK:
			main= MainView.getInstance();
			main.update(contexto);	
			break;	
		case DispatcherResults.KMeansCorrect:
			kmeans = JKMeans.getInstance();
			kmeans.update(contexto);
			break;
		case BusinessEvent.AGRUPAMIENTO_SECUENCIAL:
			as = JAgrupamientoSec.getInstance();
			as.update(contexto);
			break;
		case BusinessEvent.BATCHELOR_WILKINS:
			bw = JBatchelorWilkins.getInstance();
			bw.update(contexto);
			break;
		case BusinessEvent.HIERARCHICAL:
			h = JHierarchical.getInstance();
			h.update(contexto);
			break;
		case BusinessEvent.MATRIZSIMILITUD:
			ms = JMatrizSimilitud.getInstance();
			ms.update(contexto);
			break;
			
		}
	}
}