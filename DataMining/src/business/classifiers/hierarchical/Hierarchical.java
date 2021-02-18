package business.classifiers.hierarchical;

import business.transfers.THierarchical;
import business.transfers.TResult;

public interface Hierarchical {

	public TResult executeHierarchical(THierarchical transfer);
}
