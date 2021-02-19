package business.classifiers.adaptative;

import business.transfers.TAdaptative;
import business.transfers.TResult;

public interface Adaptative {
	public TResult executeAlgorithm(TAdaptative transfer);
}
