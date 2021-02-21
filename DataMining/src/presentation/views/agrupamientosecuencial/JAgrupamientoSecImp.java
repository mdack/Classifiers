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

import business.factory.FactoryAS;
import business.files.Data;
import business.transfers.TResult;
import presentation.controller.BusinessEvent;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;
import presentation.views.mainview.MainView;

@SuppressWarnings("serial")
public class JAgrupamientoSecImp extends JAgrupamientoSec {
	
	public JAgrupamientoSecImp() {
		this.initComponents();
		this.initGUIAgrupamientoSec();
	}

	public void initComponents() {
		this.setTitle("Agrupamiento Secuencial");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
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
		txK.setText("5");
		txR = new JTextField();
		txR.setEditable(true);
		txR.setText("15000");
		txC = new JTextField();
		txC.setEditable(true);
		txC.setText("5000");
		txM = new JTextField();
		txM.setEditable(true);
		txM.setText("15");
		txT = new JTextField();
		txT.setEditable(true);
		txT.setText("30");
		
	}
	
	@Override
	public void initGUIAgrupamientoSec() {
		
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
		
		btExecute.addActionListener(new ALAgrupamientoSec(BusinessEvent.AGRUPAMIENTO_SECUENCIAL, txK, txM, txR, txC, txT));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
		btExecute.setText(" Ejecutar ");
		boton.add(btExecute);

		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
	}
	
	public void update(Object context) {
		Context c = (Context) context;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
			case(DispatcherResults.Agrupamientocorrect):{
				JOptionPane.showMessageDialog(null,
			"Cluster creados!",
			"Correcto", JOptionPane.PLAIN_MESSAGE);
				this.dispose();
				TResult transfer = (TResult) c.getDatos();
				
				Data data = FactoryAS.getInstance().writeResult();
				data.writeCluster(transfer);
				
				MainView.getInstance().UpdateArea(transfer.toString());	
				
			}break;
			case(DispatcherResults.AgrupamientoError):{
				JOptionPane.showMessageDialog(null,
						"Error al intentar ejecutar clasificador",
						"Error crítico", JOptionPane.ERROR_MESSAGE);
			}break;
		}
	}

}
