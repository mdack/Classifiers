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

import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

@SuppressWarnings("serial")
public class JKMeansImp extends JKMeans {

	private final static String[] inits = {"Arbitraria", "Inversa", "Directa"};
	
	public JKMeansImp(int evento) {
		this.initComponents();
		
		switch(evento) {
		case BusinessEvent.KMEANS:
			initGUIKmeans();
			break;
		}
	}


	public void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
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
		this.setTitle("KMeans");
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		
		//datos
		datos.setLayout(new GridLayout(2, 2, 50, 50));
		
		datos.add(linit);
		cbInitializion.addItem(inits[0]);
		cbInitializion.addItem(inits[1]);
		cbInitializion.addItem(inits[2]);
		datos.add(cbInitializion);
		
		datos.add(lK);
		datos.add(txK);
		
		boton.add(btExecute);
		btExecute.addActionListener(new ALKMeans(BusinessEvent.KMEANS, txK, cbInitializion, transfer_zip));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);;
				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}
	
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.KMeansCorrect):{
				Integer r = (int) c.getDatos();
							JOptionPane.showMessageDialog(null,
						"Cluster al que pertenece" + r,
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
			}break;
			case(DispatcherResults.KMeansError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador KMeans",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
	}

}
