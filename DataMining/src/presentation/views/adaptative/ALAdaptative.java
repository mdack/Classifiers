package presentation.views.adaptative;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.transfers.TAdaptative;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.mainview.MainView;

public class ALAdaptative implements ActionListener{

	private JTextField tT, tO;
	private TZip tZip;
	
	public ALAdaptative(JTextField txO, JTextField txT) {
		tT=txT;
		tO=txO;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		
		if(tZip != null) {
		
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		double t = Double.valueOf(tT.getText());
		double o = Double.valueOf(tO.getText());
		TAdaptative transfer = new TAdaptative(t, o, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.ADAPTATIVE);
		
		cont.action(context);
		}else {
			JOptionPane.showMessageDialog(null,
					"Error: No se ha elegido fichero.",
					"Error cr�tico", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
