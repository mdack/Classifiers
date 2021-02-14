package presentation.views.kmeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALKMeans implements ActionListener{

	private int evento;
	private int K;
	
	
	public ALKMeans(int kmeans, JTextField txK) {
		evento = kmeans;
		K = Integer.getInteger(txK.getText());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		switch(evento) {
		case(BusinessEvent.KMEANS):
			Controller cont = Controller.getInstance();
		
			Context context = new Context();
			context.setDatos(K);
			context.setEvento(BusinessEvent.KMEANS);
			
			cont.action(context);
			break;
		}
		
	}

}
