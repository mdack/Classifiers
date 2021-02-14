package presentation.views.kmeans;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

@SuppressWarnings("serial")
public class JMainKMeansImp extends JMainKMeans {

	public JMainKMeansImp(int evento) {
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
		
		//text
		txK = new JTextField();
		txK.setEditable(true);
	}


	@Override
	public void initGUIKmeans() {
		this.setTitle("KMeans");
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		
		//datos
		datos.setLayout(new GridLayout(1, 2, 50, 50));
		
		datos.add(lK);
		datos.add(txK);
		
		boton.add(btExecute);
		btExecute.addActionListener(new ALKMeans(BusinessEvent.KMEANS, txK));
				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}
	
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.KMeansCorrecto):{
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
