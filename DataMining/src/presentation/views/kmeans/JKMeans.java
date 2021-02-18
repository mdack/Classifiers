package presentation.views.kmeans;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.transfers.TZip;


@SuppressWarnings("serial")
public abstract class JKMeans extends JFrame{
	
	private static JKMeans instance;
	protected static int event;
	protected static TZip transfer_zip;
	
	public static JKMeans getInstance() {
		
		if(instance==null){
			instance = new JKMeansImp();
		}
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lK, linit;
	
	protected JButton btExecute;
	
	protected JTextField txK;
	
	protected JComboBox<String> cbInitializion;
	
	public static TZip getTransfer_zip() {
		return transfer_zip;
	}

	public static void setTransfer_zip(TZip tz) {
		transfer_zip = tz;
	}
	
	public abstract void initGUIKmeans();

	public abstract void update(Object context);
}
