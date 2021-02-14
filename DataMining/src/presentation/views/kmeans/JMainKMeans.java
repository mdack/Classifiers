package presentation.views.kmeans;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public abstract class JMainKMeans extends JFrame{
	
	private static JMainKMeans instance;
	protected static int event;
	
	public static JMainKMeans getInstance(int evento) {
		
		if((instance==null)||(event!=evento)){
			instance = new JMainKMeansImp(evento);
			event=evento;
		}
		return instance;
	}
	
	protected JPanel contenedor;

	protected JPanel datos;

	protected JPanel boton;

	protected JLabel lK;
	
	protected JButton btExecute;
	
	protected JTextField txK;
	
	public abstract void initGUIKmeans();
}
