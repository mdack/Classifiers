package presentation.views.hierarchical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import business.transfers.THierarchical;
import business.transfers.TZip;
import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALHierarchical implements ActionListener{

	private int tLink;
	private TZip tZip;
	
	public ALHierarchical(int hierarchical, JComboBox<String> cbLinked, TZip transfer_zip) {
		tLink = cbLinked.getSelectedIndex();
		tZip = transfer_zip;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Controller cont = Controller.getInstance();
		
		Context context = new Context();
		THierarchical transfer = new THierarchical(tLink, tZip);
		context.setDatos(transfer);
		context.setEvento(BusinessEvent.MATRIZSIMILITUD);
		
		cont.action(context);
		
	}

}
