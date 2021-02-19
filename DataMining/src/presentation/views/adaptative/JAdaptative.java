package presentation.views.adaptative;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public abstract class JAdaptative extends JFrame {
	
	private static JAdaptative instance = null;
	
	public static JAdaptative getInstance() {
		if(instance == null)
			instance = new JAdaptativeImp();
		return instance;
	}

	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lT, lO;
	
	protected JButton btExecute;
	
	protected JTextField txT, txO;
	
	public abstract void initGUIAAdaptative();

	public abstract void update(Object datos);
}
