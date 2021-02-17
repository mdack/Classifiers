package presentation.views.agrupamientosecuencial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import business.transfers.TAgrupamientoSec;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALAgrupamientoSec implements ActionListener{

	private int K, M;
	private double R, C, T;
	private TZip tZip;

	public ALAgrupamientoSec(int agrupamientoSecuencial, JTextField txK, JTextField txM, JTextField txR, JTextField txC,
			JTextField txT, TZip transfer_zip) {
		K = Integer.getInteger(txK.getText());
		R = Double.valueOf(txR.getText());
		C = Double.valueOf(txC.getText());
		M = Integer.getInteger(txM.getText());
		T = Double.valueOf(txR.getText());
		tZip = transfer_zip;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		TAgrupamientoSec transfer = new TAgrupamientoSec(K, R, C, M, T,tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.AGRUPAMIENTO_SECUENCIAL);
		
		cont.action(context);	
	}

}
