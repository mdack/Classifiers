package presentation.views.agrupamientosecuencial;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public abstract class JAgrupamientoSec extends JFrame{
	private static JAgrupamientoSec instance;
	
	public static JAgrupamientoSec getInstance() {
		
		if(instance==null){
			instance = new JAgrupamientoSecImp();
		}
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lK, lR, lC, lM, lT;
	
	protected JButton btExecute;
	
	protected JTextField txK, txR, txC, txM, txT;
	
	public abstract void initGUIAgrupamientoSec();

	public void update(Object datos) {
		
	}
}

