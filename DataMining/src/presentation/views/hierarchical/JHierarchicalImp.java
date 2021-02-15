package presentation.views.hierarchical;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import presentation.controller.BusinessEvent;

@SuppressWarnings("serial")
public class JHierarchicalImp extends JHierarchical {
	
	public JHierarchicalImp() {
		this.initComponents();
		this.initGUIHierarchical();
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
		lLink = new JLabel("Tipo de linkado: ");
		
		this.cbLinked = new JComboBox<String>();
	}

	@Override
	public void initGUIHierarchical() {
		this.setTitle("Jerárquico");
		this.setSize(250,200);
		this.setLocationRelativeTo(null);
		
		//datos
		datos.setLayout(new GridLayout(1, 2, 50, 50));
		
		datos.add(lLink);
		cbLinked.addItem("Single Link");
		cbLinked.addItem("Complete Link");
		datos.add(cbLinked);
		
		boton.add(btExecute);
		btExecute.addActionListener(new ALHierarchical(BusinessEvent.HIERARCHICAL, cbLinked, transfer_zip));
		btExecute.setHorizontalAlignment(SwingConstants.CENTER);
				
		contenedor.add(datos, BorderLayout.CENTER);
		contenedor.add(boton, BorderLayout.SOUTH);
		add(contenedor);
		
	}

	@Override
	public void update(Object context) {
		// TODO Auto-generated method stub
		
	}

}
