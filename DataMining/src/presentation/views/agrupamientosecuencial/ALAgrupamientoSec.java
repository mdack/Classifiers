package presentation.views.agrupamientosecuencial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import business.transfers.TAgrupamientoSec;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALAgrupamientoSec implements ActionListener{

	private int K, R, C, M, T;
	private int tMezcla;
	private TZip tZip;
	
	public ALAgrupamientoSec(int agrupamientoSecuencial, JTextField txK, JTextField txM, JTextField txR, JTextField txC,
			JTextField txT, JComboBox<String> cbMezcla, TZip transfer_zip) {
		K = Integer.getInteger(txK.getText());
		R = Integer.getInteger(txR.getText());
		C = Integer.getInteger(txC.getText());
		M = Integer.getInteger(txM.getText());
		T = Integer.getInteger(txR.getText());
		tZip = transfer_zip;
		tMezcla = cbMezcla.getSelectedIndex();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		TAgrupamientoSec transfer = new TAgrupamientoSec(K, R, C, M, T, tMezcla ,tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.AGRUPAMIENTO_SECUENCIAL);
		
		cont.action(context);	
	}

}
