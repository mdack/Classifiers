package presentation.views.batchelorwilkins;

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

import business.transfers.TResult;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;
import presentation.views.mainview.MainView;

@SuppressWarnings("serial")
public class JBatchelorWilkinsImp extends JBatchelorWilkins {

	public JBatchelorWilkinsImp() {
		this.initComponents();
		this.initGUIBatchelorWilkins();
	}
	
	public void initComponents() {
		this.setTitle("Batchelor y Wilkins");
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
		lO = new JLabel("Umbral de distancia: ");
		
		//text
		txO = new JTextField();
		txO.setEditable(true);
		txO.setText("0.5");
	}
	
	@Override
	public void initGUIBatchelorWilkins() {
		//datos
		datos.setLayout(new GridLayout(1, 2, 50, 50));
		
		datos.add(lO);
		datos.add(txO);
		
		btExecute.setText(" Ejecutar ");
		btExecute.addActionListener(new ALBatchelorWilkins(txO));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
		boton.add(btExecute);
				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
		
	}

	@Override
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.BatchelorWilkinsCorrect):{
							JOptionPane.showMessageDialog(null,
						"¡Cluster creados!",
						"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
				TResult transfer = (TResult) c.getDatos();
				System.out.println(transfer.toString());
			}break;
			case(DispatcherResults.BatchelorWilkinsError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
		
	}

}
