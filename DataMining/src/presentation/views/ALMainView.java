package presentation.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import business.transfers.TZip;
import presentation.views.kmeans.JKMeans;

public class ALMainView implements ActionListener{
	
	private JComboBox<String> c;
	private TZip tZip;
	
	public ALMainView(JComboBox<String> com, TZip transfer){
		this.c=com;
		this.tZip = transfer;
	}
	
	public ALMainView(JComboBox<String> com, JCheckBox opcion1,TZip transfer) {
		
		this.c=com;
		this.tZip = transfer;
		if(opcion1.isSelected())
			tZip.setAreSignals(true);
		else
			tZip.setAreSignals(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!tZip.getFiles().isEmpty()) {
			switch(c.getSelectedIndex()) {
			case 0:
				break;
			case 1:
				JKMeans ventana = JKMeans.getInstance(100+c.getSelectedIndex());
				JKMeans.setTransfer_zip(tZip);
				ventana.setVisible(true);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			}
		}else {
			
		}
	}

}
