package presentation.views.hierarchical;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.transfers.TZip;


@SuppressWarnings("serial")
public abstract class JHierarchical extends JFrame{
	protected static TZip transfer_zip;
	protected static JHierarchical instance;
	
	public static JHierarchical getInstance() {
		if(instance == null)
			instance = new JHierarchicalImp();
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;
	protected JLabel lLink;
	protected JButton btExecute;
	protected JComboBox<String> cbLinked;
	
	public static TZip getTransfer_zip() {
		return transfer_zip;
	}

	public static void setTransfer_zip(TZip tz) {
		transfer_zip = tz;
	}
	
	public abstract void initGUIHierarchical();

	public abstract void update(Object context);
}
