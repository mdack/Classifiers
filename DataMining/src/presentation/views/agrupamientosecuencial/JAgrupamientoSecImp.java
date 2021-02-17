package presentation.views.agrupamientosecuencial;

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
public class JAgrupamientoSecImp extends JAgrupamientoSec {

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
		lR = new JLabel("Umbral R: ");
		lC = new JLabel("Umbral C: ");
		lM = new JLabel("Longitud del lote: ");
		lT = new JLabel("Umbral T: ");
		
		//text
		txK = new JTextField();
		txK.setEditable(true);
		txR = new JTextField();
		txR.setEditable(true);
		txC = new JTextField();
		txC.setEditable(true);
		txM = new JTextField();
		txM.setEditable(true);
		txT = new JTextField();
		txT.setEditable(true);
		
	}
	
	@Override
	public void initGUIAgrupamientoSec() {
		this.setTitle("Agrupamiento Secuencial");
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		
		//datos
		datos.setLayout(new GridLayout(5, 2, 50, 50));
				
		datos.add(lK);
		datos.add(txK);
		
		datos.add(lR);
		datos.add(txR);
		
		datos.add(lC);
		datos.add(txC);
		
		datos.add(lM);
		datos.add(txM);
		
		datos.add(lT);
		datos.add(txT);
		
		boton.add(btExecute);
		btExecute.addActionListener(new ALAgrupamientoSec(BusinessEvent.AGRUPAMIENTO_SECUENCIAL, txK, txM, txR, txC, txT, transfer_zip));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
		
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}
	
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.Agrupamientocorrect):{
				Integer r = (int) c.getDatos();
							JOptionPane.showMessageDialog(null,
						"Cluster al que pertenece" + r,
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
			}break;
			case(DispatcherResults.AgrupamientoError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador KMeans",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
	}

}
