package business.classifiers.agrupamientosecuencial;

import business.transfers.TAgrupamientoSec;
import business.transfers.TResult;

public interface AgrupamientoSec {
	public TResult executeAlgorithm(TAgrupamientoSec transfer);
}
