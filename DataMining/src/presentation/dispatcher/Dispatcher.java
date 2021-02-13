package presentation.dispatcher;


public abstract class Dispatcher {

	private static Dispatcher instancia;

	public static Dispatcher getInstance(Boolean esDispatcherJPA) {
		if(instancia == null){
				return instancia = new DispatcherImp();
		}
		
		return instancia;
	}

	public abstract void update(Context contexto);
}