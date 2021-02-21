package presentation.views.adaptative;

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

import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TResult;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;
import presentation.views.mainview.MainView;

@SuppressWarnings("serial")
public class JAdaptativeImp extends JAdaptative {

	private TResult transfer;
	
	public JAdaptativeImp() {
		this.initComponents();
		this.initGUIAAdaptative();
	}
	
	public void initComponents() {
		this.setTitle("Método adaptativo");	
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
		lT = new JLabel("Umbral de distancia: ");
		lO = new JLabel("Total confianza: ");
		
		//text
		txT = new JTextField();
		txT.setEditable(true);
		txT.setText("25000");
		
		txO = new JTextField();
		txO.setEditable(true);
		txO.setText("0.6");
		
	}
	
	@Override
	public void initGUIAAdaptative() {
		//datos
		datos.setLayout(new GridLayout(2, 2, 75, 75));
		
		datos.add(lT);
		datos.add(txT);
		
		datos.add(lO);
		datos.add(txO);
		
		btExecute.setText(" Ejecutar ");
		btExecute.addActionListener(new ALAdaptative(txO,txT));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
		boton.add(btExecute);

				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
		
	}	
	

	@Override
	public void update(Object datos) {
		Context c = (Context) datos;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.AdaptativeCorrect):{
							JOptionPane.showMessageDialog(null,
						"Cluster creados!",
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				transfer = (TResult) c.getDatos();
				
				Data data = FactoryAS.getInstance().writeResult();
				data.writeCluster(transfer);
				
				MainView.getInstance().UpdateArea(transfer.toString());	
				this.dispose();
			}break;
			case(DispatcherResults.AdaptativeError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador.",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
	}



}
