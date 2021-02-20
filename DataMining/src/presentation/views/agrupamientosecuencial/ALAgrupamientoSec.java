package presentation.views.agrupamientosecuencial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.transfers.TAgrupamientoSec;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.mainview.MainView;

public class ALAgrupamientoSec implements ActionListener{

	private JTextField tK, tM, tR, tC, tT;

	public ALAgrupamientoSec(int agrupamientoSecuencial, JTextField txK, JTextField txM, JTextField txR, JTextField txC, JTextField txT) {
		tK = txK;
		tR = txR;
		tC = txC;
		tM = txM;
		tT = txT;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TZip tzip = MainView.getInstance().gettZip();
		
		if(tzip != null) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		
		int K = Integer.parseInt(tK.getText());
		double R = Double.valueOf(tR.getText());
		double C = Double.valueOf(tC.getText());
		int M = Integer.parseInt(tM.getText());
		double T = Double.valueOf(tT.getText());
		
		TAgrupamientoSec transfer = new TAgrupamientoSec(K, R, C, M, T,tzip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.AGRUPAMIENTO_SECUENCIAL);
		
		cont.action(context);	
		}else {
			JOptionPane.showMessageDialog(null,
					"Error: No se ha elegido fichero.",
					"Error crítico", JOptionPane.ERROR_MESSAGE);
		}
	}

}
