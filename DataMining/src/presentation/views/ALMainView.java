package presentation.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import business.transfers.TZip;
import presentation.views.agrupamientosecuencial.JAgrupamientoSec;
import presentation.views.batchelorwilkins.JBatchelorWilkins;
import presentation.views.hierarchical.JHierarchical;
import presentation.views.kmeans.JKMeans;
import presentation.views.matrizsimilitud.JMatrizSimilitud;

public class ALMainView implements ActionListener{
	
	private JComboBox<String> c = null;
	private TZip tZip = new TZip();
	private JCheckBox file;
	
	public ALMainView(JComboBox<String> com, TZip transfer){
		this.c=com;
		this.tZip = transfer;
	}
	
	public ALMainView(JComboBox<String> com, JCheckBox opcion1) {
		
		this.c=com;
		file = opcion1;
	}

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		if(file.isSelected())
			tZip.setAreSignals(true);
		else
			tZip.setAreSignals(false);
		
		
		if(!tZip.getList().isEmpty()) {
			switch(c.getSelectedIndex()) {
			case 0:
				JHierarchical ventana0 = JHierarchical.getInstance();
				JHierarchical.setTransfer_zip(tZip);
				ventana0.setVisible(true);
				break;
			case 1:
				JKMeans ventana1 = JKMeans.getInstance();
				ventana1.setTransfer_zip(tZip);
				ventana1.setVisible(true);
				break;
			case 2:
				JAgrupamientoSec ventana2 = JAgrupamientoSec.getInstance();
				JAgrupamientoSec.setTransfer_zip(tZip);
				ventana2.setVisible(true);
				break;
			case 3:
				JMatrizSimilitud ventana3 = JMatrizSimilitud.getInstance();
				JMatrizSimilitud.setTransfer_zip(tZip);
				ventana3.setVisible(true);
				break;
			case 4:
				JBatchelorWilkins ventana4 = JBatchelorWilkins.getInstance();
				JBatchelorWilkins.setTransfer_zip(tZip);
				ventana4.setVisible(true);
				break;
			}
		}else {
			
		}
	}

}
