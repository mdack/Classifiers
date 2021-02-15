package presentation.views.matrizsimilitud;

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
import javax.swing.SwingConstants;

import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

@SuppressWarnings("serial")
public class JMatrizSimilitudImp extends JMatrizSimilitud {
	
	public JMatrizSimilitudImp() {
		this.initComponents();
		this.initGUIMatrizSim();
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
		lO = new JLabel("Umbral de distancia: ");
		
		//text
		txO = new JTextField();
		txO.setEditable(true);
	}
	
	@Override
	public void initGUIMatrizSim() {
		this.setTitle("Matriz de similitud");
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		
		//datos
		datos.setLayout(new GridLayout(1, 2, 50, 50));
		
		datos.add(lO);
		datos.add(txO);
		
		boton.add(btExecute);
		btExecute.addActionListener(new ALMatrizSimilitud(BusinessEvent.AGRUPAMIENTO_SECUENCIAL, txO, transfer_zip));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}

	@Override
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.MatrizCorrect):{
				Integer r = (int) c.getDatos();
							JOptionPane.showMessageDialog(null,
						"Cluster al que pertenece" + r,
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
			}break;
			case(DispatcherResults.MatrizError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador KMeans",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
		
	}

}
