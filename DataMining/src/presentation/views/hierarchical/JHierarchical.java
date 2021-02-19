package presentation.views.hierarchical;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class JHierarchical extends JFrame{

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
		
	public abstract void initGUIHierarchical();

	public abstract void update(Object context);
}
