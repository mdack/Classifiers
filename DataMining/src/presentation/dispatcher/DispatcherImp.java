package presentation.dispatcher;

public class DispatcherImp extends Dispatcher {


	@Override
	public void update(Context contexto) {
		
		
		
		int evento = (int) contexto.getEvento();
		/*
		JPrincipalRuta ruta;
		JPrincipalMotor motor;
		JPrincipalAvion avion;
		
		switch (evento) {
		
		//RUTA
		case EventoNegocio.crearRUTA:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.crearRUTA);
			ruta.update(contexto.getDatos());	
			break;
			
		case EventoNegocio.mostrarRUTA:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.mostrarRUTA);
			ruta.update(contexto.getDatos());
			break;
			
		case EventoNegocio.modificarRUTA:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.modificarRUTA);
			ruta.update(contexto.getDatos());
			break;
			
		case EventoNegocio.borrarRUTA:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.borrarRUTA);
			ruta.update(contexto.getDatos());
			break;
			
		case EventoNegocio.listarRUTA:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.listarRUTA);
			ruta.update(contexto.getDatos());
			break;
		case EventoNegocio.crearVUELO:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.crearVUELO);
			ruta.update(contexto.getDatos());
			break;
		case EventoNegocio.borrarVUELO:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.borrarVUELO);
			ruta.update(contexto.getDatos());
			break;
		case EventoNegocio.VuelosPorAvion:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.VuelosPorAvion);
			ruta.update(contexto.getDatos());
			break;
		case EventoNegocio.VuelosPorRuta:
			ruta= JPrincipalRuta.getInstance(EventoNegocio.VuelosPorAvion);
			ruta.update(contexto.getDatos());
			break;
		//AVION
		case EventoNegocio.crearAVION:
			avion = JPrincipalAvion.getInstance(EventoNegocio.crearAVION);
			avion.update(contexto.getDatos());
			break;
			
		case EventoNegocio.mostrarAVION:
			avion = JPrincipalAvion.getInstance(EventoNegocio.mostrarAVION);
			avion.update(contexto.getDatos());
			break;
			
		case EventoNegocio.modificarAVION:
			avion = JPrincipalAvion.getInstance(EventoNegocio.modificarAVION);
			avion.update(contexto.getDatos());
			break;
			
		case EventoNegocio.borrarAVION:
			avion = JPrincipalAvion.getInstance(EventoNegocio.borrarAVION);
			avion.update(contexto.getDatos());
			break;
			
		case EventoNegocio.listarAVION:
			avion = JPrincipalAvion.getInstance(EventoNegocio.listarAVION);
			avion.update(contexto.getDatos());
			break;
			
		
		//MOTOR
		case EventoNegocio.crearMOTOR:
			motor = JPrincipalMotor.getInstance(EventoNegocio.crearMOTOR);
			motor.update(contexto.getDatos());
			break;
			
		case EventoNegocio.mostrarMOTOR:
			motor = JPrincipalMotor.getInstance(EventoNegocio.mostrarMOTOR);
			motor.update(contexto.getDatos());
			break;
			
		case EventoNegocio.modificarMOTOR:
			motor = JPrincipalMotor.getInstance(EventoNegocio.modificarMOTOR);
			motor.update(contexto.getDatos());
			break;
			
		case EventoNegocio.borrarMOTOR:
			motor = JPrincipalMotor.getInstance(EventoNegocio.borrarMOTOR);
			motor.update(contexto.getDatos());
			break;
			
		case EventoNegocio.listarMOTOR:
			motor = JPrincipalMotor.getInstance(EventoNegocio.listarMOTOR);
			motor.update(contexto.getDatos());
			break;
		
		
		}
		*/
	}
}