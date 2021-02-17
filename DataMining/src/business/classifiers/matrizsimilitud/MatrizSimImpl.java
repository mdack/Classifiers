package business.classifiers.matrizsimilitud;

import business.transfers.TMatrizSim;
import business.transfers.TResult;

public class MatrizSimImpl implements MatrizSim{

	@Override
	public TResult executeAlgorithm(TMatrizSim transfer) {
		//Calcular las distancias entre todas las parejas de patrones
		
		//Seleccionan el agrupamiento más numeroso entre todos los candidatos, 
		//siendo Xi el patrón ”central” del agrupamiento seleccionado.
		
		/*
		 * Estos
patrones, aunque están más alejados del patrón ”central” que el umbral 
están cercanos a alguno de los patrones que forman parte del
agrupamiento. Este paso se repite (5) siempre que se incorporen nuevos
patrones al agrupamiento. Cuando no se puedan añadir más, el
agrupamiento está completo.
		 */
		
		/*
		 * Eliminar del conjunto de patrones los que han formado el agrupamiento
anterior y en el paso 7 se vuelve a empezar, seleccionando el agrupamiento
inicial más numeroso.
		 */
		return null;
	}

}
