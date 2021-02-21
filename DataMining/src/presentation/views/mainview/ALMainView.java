package presentation.views.mainview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import business.transfers.TZip;
import presentation.views.adaptative.JAdaptative;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		tZip = MainView.getInstance().gettZip();
		
		if(tZip != null) {
		if(file.isSelected())
			tZip.setAreSignals(true);
		else
			tZip.setAreSignals(false);
		
		
		if(!tZip.getList().isEmpty()) {
				switch(c.getSelectedIndex()) {
				case 0:
					JAdaptative ventana5 = JAdaptative.getInstance();
					ventana5.setVisible(true);
					break;
				case 1:
					JBatchelorWilkins ventana4 = JBatchelorWilkins.getInstance();
					ventana4.setVisible(true);
					break;
				case 2:
					JHierarchical ventana0 = JHierarchical.getInstance();
					ventana0.setVisible(true);
					break;
				case 3:
					JKMeans ventana1 = JKMeans.getInstance();;
					ventana1.setVisible(true);
					break;
				case 4:
					JAgrupamientoSec ventana2 = JAgrupamientoSec.getInstance();
					ventana2.setVisible(true);
					break;
				case 5:
					JMatrizSimilitud ventana3 = JMatrizSimilitud.getInstance();
					ventana3.setVisible(true);
					break;
				}
			}else {
				JOptionPane.showMessageDialog(null,
						"Error al intentar cargar ventana de parámetros",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(null,
					"¡Tienes que escoger un fichero .zip!",
					"Error crítico", JOptionPane.ERROR_MESSAGE);
		}
	}

}
