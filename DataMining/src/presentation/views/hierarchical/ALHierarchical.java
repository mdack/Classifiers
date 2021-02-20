package presentation.views.hierarchical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import business.transfers.THierarchical;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;
import presentation.views.mainview.MainView;

public class ALHierarchical implements ActionListener{

	private JComboBox<String> cb;
	private TZip tZip;
	
	public ALHierarchical(JComboBox<String> cbLinked) {
		cb = cbLinked;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		
		if(tZip != null) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		int tLink = cb.getSelectedIndex();
		THierarchical transfer = new THierarchical(tLink, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.HIERARCHICAL);
		
		cont.action(context);
		}
		else {
			JOptionPane.showMessageDialog(null,
					"Error: No se ha elegido fichero.",
					"Error crítico", JOptionPane.ERROR_MESSAGE);
		}
	}

}
