package presentation.views.matrizsimilitud;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.transfers.TZip;

@SuppressWarnings("serial")
public abstract class JMatrizSimilitud extends JFrame{
	
	protected static JMatrizSimilitud instance;
	protected static TZip transfer_zip;
	
	public static JMatrizSimilitud getInstance() {
		if(instance == null)
			instance = new JMatrizSimilitudImp();
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lO;
	
	protected JButton btExecute;
	
	protected JTextField txO;
	
	public static TZip getTransfer_zip() {
		return transfer_zip;
	}

	public static void setTransfer_zip(TZip tz) {
		transfer_zip = tz;
	}
	
	public abstract void initGUIMatrizSim();

	public abstract void update(Object context);
}
