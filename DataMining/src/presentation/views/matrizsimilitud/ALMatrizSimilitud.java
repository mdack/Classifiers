package presentation.views.matrizsimilitud;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import business.transfers.TMatrizSim;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.MainView;

public class ALMatrizSimilitud implements ActionListener{

	private JTextField tO;
	private TZip tZip;
	
	
	public ALMatrizSimilitud(JTextField txO) {
		tO = txO;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		double o = Double.valueOf(tO.getText());
		TMatrizSim transfer = new TMatrizSim(o, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.MATRIZSIMILITUD);
		
		cont.action(context);
		
	}

}
