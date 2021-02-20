package presentation.views.kmeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.transfers.TKMeans;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.mainview.MainView;

public class ALKMeans implements ActionListener{

	private JTextField tK;
	private JComboBox<String> cb;
	private TZip tZip;
	
	public ALKMeans(JTextField txK, JComboBox<String> cbInitializion) {
		tK = txK;
		cb = cbInitializion;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		
		if(tZip != null) {
			Controller cont = Controller.getInstance();
		
			Context context = new Context();
			int k = Integer.parseInt(tK.getText());
			int option = cb.getSelectedIndex();
			TKMeans transfer = new TKMeans(k,option,tZip);
			context.setDatos(transfer);
			context.setEvento(BusinessEvent.KMEANS);
			
			cont.action(context);
		}
		else {
			JOptionPane.showMessageDialog(null,
					"Error: No se ha elegido fichero.",
					"Error crítico", JOptionPane.ERROR_MESSAGE);
		}
	}

}
