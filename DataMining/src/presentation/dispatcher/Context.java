package presentation.dispatcher;


public class Context {

	private Object dato;
	private Object evento;

	public Object getDatos() {
		return dato;
	}

	public Object getEvento() {
		return evento;
	}

	public void setDatos(Object datos) {
		dato = datos;
	}

	public void setEvento(Object event) {
		evento = event;
	}
}