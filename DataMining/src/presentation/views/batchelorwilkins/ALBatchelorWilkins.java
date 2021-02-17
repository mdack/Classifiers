package presentation.views.batchelorwilkins;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import business.transfers.TBatchelorWilkins;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALBatchelorWilkins implements ActionListener{
	private double O;
	private TZip tZip;
	
	public ALBatchelorWilkins(int batchelorWilkins, JTextField txO, TZip transfer_zip) {
		O = Double.valueOf(txO.getText());
		tZip = transfer_zip;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		TBatchelorWilkins transfer = new TBatchelorWilkins(O, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.BATCHELOR_WILKINS);
		
		cont.action(context);
		
	}

}
