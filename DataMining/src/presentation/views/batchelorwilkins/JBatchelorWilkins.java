package presentation.views.batchelorwilkins;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public abstract class JBatchelorWilkins extends JFrame{
	protected static JBatchelorWilkins instance;
	
	public static JBatchelorWilkins getInstance() {
		if(instance == null)
			instance = new JBatchelorWilkinsImp();
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lO;
	
	protected JButton btExecute;
	
	protected JTextField txO;
		
	public abstract void initGUIBatchelorWilkins();

	public abstract void update(Object context);
}
