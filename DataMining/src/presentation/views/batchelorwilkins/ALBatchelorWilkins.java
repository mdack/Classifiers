package presentation.views.batchelorwilkins;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.transfers.TBatchelorWilkins;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.mainview.MainView;

public class ALBatchelorWilkins implements ActionListener{
	private JTextField tO;
	private TZip tZip;
	
	public ALBatchelorWilkins(JTextField txO) {
		tO = txO;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		
		if(tZip != null) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		double o = Double.valueOf(tO.getText());
		TBatchelorWilkins transfer = new TBatchelorWilkins(o, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.BATCHELOR_WILKINS);
		
		cont.action(context);
		}
		else {
			JOptionPane.showMessageDialog(null,
					"Error: No se ha elegido fichero.",
					"Error crítico", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
