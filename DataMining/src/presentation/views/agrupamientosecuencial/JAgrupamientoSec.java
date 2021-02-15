package presentation.views.agrupamientosecuencial;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.transfers.TZip;

@SuppressWarnings("serial")
public abstract class JAgrupamientoSec extends JFrame{
	private static JAgrupamientoSec instance;
	protected static TZip transfer_zip;
	
	public static JAgrupamientoSec getInstance() {
		
		if(instance==null){
			instance = new JAgrupamientoSecImp();
		}
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lK, lR, lC, lM, lT, lMezcla;
	
	protected JButton btExecute;
	
	protected JTextField txK, txR, txC, txM, txT;
	
	protected JComboBox<String> cbMezcla;
	
	public static TZip getTransfer_zip() {
		return transfer_zip;
	}

	public static void setTransfer_zip(TZip tz) {
		transfer_zip = tz;
	}
	
	public abstract void initGUIAgrupamientoSec();

	public void update(Object datos) {
		
	}
}

