package presentation.views.kmeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import business.transfers.TKMeans;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALKMeans implements ActionListener{

	private int evento;
	private int K;
	private int tInit;
	private TZip tZip;
	
	@SuppressWarnings("rawtypes")
	public ALKMeans(int kmeans, JTextField txK, JComboBox cbInitializion, TZip tz) {
		evento = kmeans;
		K = Integer.getInteger(txK.getText());
		tInit = cbInitializion.getSelectedIndex();
		tZip = tz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(evento) {
		case(BusinessEvent.KMEANS):
			Controller cont = Controller.getInstance();
		
			Context context = new Context();
			TKMeans transfer = new TKMeans(K,tInit,tZip);
			context.setDatos(transfer);
			context.setEvento(BusinessEvent.KMEANS);
			
			cont.action(context);
			break;
		}
		
	}

}
