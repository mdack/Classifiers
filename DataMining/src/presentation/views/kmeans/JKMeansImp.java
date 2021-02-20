package presentation.views.kmeans;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import business.transfers.TResult;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;
import presentation.views.mainview.MainView;

@SuppressWarnings("serial")
public class JKMeansImp extends JKMeans{

	private final static String[] inits = {"Arbitraria", "Inversa", "Directa"};
	
	private TResult transfer;
	
	public JKMeansImp() {
		this.initComponents();
		initGUIKmeans();
	}


	public void initComponents() {
		this.setTitle("KMeans");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,400);
        this.setResizable(false); 
        this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		contenedor =new JPanel();
		contenedor.setLayout(new BorderLayout());
		contenedor.setBorder(BorderFactory.createTitledBorder("Parámetros"));
		
		datos =new JPanel();
		datos.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		boton =new JPanel();
		boton.setLayout(new FlowLayout());
		
		btExecute = new JButton();
		
		//label
		lK = new JLabel("K centros: ");
		linit = new JLabel("Tipo de inicialización: ");
		
		//text
		txK = new JTextField();
		txK.setEditable(true);
		
		this.cbInitializion = new JComboBox<String>();
	}


	@Override
	public void initGUIKmeans() {
		
		//datos
		datos.setLayout(new GridLayout(2, 2, 75, 75));
		
		datos.add(linit);
		cbInitializion.addItem(inits[0]);
		cbInitializion.addItem(inits[1]);
		cbInitializion.addItem(inits[2]);
		datos.add(cbInitializion);
		
		datos.add(lK);
		datos.add(txK);
		
		btExecute.setText(" Ejecutar ");
		btExecute.addActionListener(new ALKMeans(txK, cbInitializion));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
		boton.add(btExecute);

				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}
	
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.KMeansCorrect):{
							JOptionPane.showMessageDialog(null,
						"Cluster creados!",
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
				transfer = (TResult) c.getDatos();
				MainView.getInstance().getTaDisplay().append(transfer.toString());
				System.out.println(transfer.toString());
			}break;
			case(DispatcherResults.KMeansError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador KMeans",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
	}

}
