package business.classifiers.adaptative;

public interface AdaptativeElement {

    public enum MyState { ASIGNED, INDETERMINATE, INCOMING };
    
	/**
	 * Al concluir una iteraci�n el estado de cada patr�n es asignado (asignado a un agrupamiento) o indeterminado.
	 * @return el id del cluste si ha sido asignado o 0 si es indeterminado
	 */
    public int getCurrentState();
    
    /**
     * Fija el estado de X a nuevoEstado
     * @return
     */
    public void changeState(MyState newState, int id_c);
}
