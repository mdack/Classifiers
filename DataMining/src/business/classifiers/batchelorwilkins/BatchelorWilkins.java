package business.classifiers.batchelorwilkins;

import business.transfers.TBatchelorWilkins;
import business.transfers.TResult;

public interface BatchelorWilkins {
	public TResult executeAlgorithm(TBatchelorWilkins transfer);
}
