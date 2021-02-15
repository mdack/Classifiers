package presentation.views.matrizsimilitud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import business.transfers.TMatrizSim;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALMatrizSimilitud implements ActionListener{
	@SuppressWarnings("unused")
	private int evento;
	private int O;
	private TZip tZip;
	
	
	public ALMatrizSimilitud(int agrupamientoSecuencial, JTextField txO, TZip transfer_zip) {
		evento = agrupamientoSecuencial;
		O = Integer.getInteger(txO.getText());
		tZip = transfer_zip;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		TMatrizSim transfer = new TMatrizSim(O, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.MATRIZSIMILITUD);
		
		cont.action(context);
		
	}

}
