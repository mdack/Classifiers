package presentation.views.mainview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import business.transfers.TZip;
import presentation.dispatcher.Context;
import presentation.dispatcher.DispatcherResults;

@SuppressWarnings("serial")
public class MainView extends JFrame{
	
	private final static String NAME_TITLE = "Clasificador por clustering - Minería de datos";
	private final String[] CLASSIFIER_OPTIONS = {"Adaptativo", "Batchelor y Wilkins", "Jerárquico","KMeans", "Agrupamiento secuencial", "Matriz de similitud"};	

	private JFileChooser explorerFiles;
		
	private TZip tZip = null;
	private JTextArea taDisplay;
	public String text = "";
	
	private static MainView instance;
	
	public MainView() {
		instance = this;
		initComponents();
	}
	
	private void initComponents(){
		//main window configuration
		this.setTitle(NAME_TITLE);	//Window title
		this.setMinimumSize(new Dimension(700,500));
        this.setResizable(false); //Minimum size of the window
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	//When you close the window, the app stops running
		this.setLocationRelativeTo(null);
		
		//Components
		JPanel window = new JPanel();
		window.setLayout(new BorderLayout());
		addTop(window);	//Header added (North region)
		addCenter(window);	//Body added (Center region)
		addBottom(window);	//Bottom added (South region)
				
		//Panel added to the frame
		this.add(window);
	}
	public static MainView getInstance() {
		if(instance==null){
			instance = new MainView();
		}
		return instance;
	}
	
	/*
	 * ********************************************
	 * 				PANELES
	 * ********************************************
	 */
	
	private void addTop(JPanel window){
		JLabel north = new JLabel ("");		//Label for the header with the app title.
			north.setFont(new Font("Header",Font.PLAIN, 18));	//Header font. Size 18.
			north.setBackground(Color.orange);					//Background color
			north.setForeground(Color.BLACK);					//Font color
			north.setHorizontalAlignment(SwingConstants.CENTER);		//Centered alignment
			north.setOpaque(true);								//Set visible
		
			window.add(north, BorderLayout.NORTH);					//Final label added to the panel
	}
	
	private void addCenter(JPanel window){
		JPanel modules = new JPanel();					//Module panel
		modules.setLayout(new GridLayout(3, 1));
		modules.add(panelExplorer());	
		modules.add(panelClassifiers());
		modules.add(panelResults());		
		window.add(modules, BorderLayout.CENTER);		//Final panel added to the window
	}
	
	private void addBottom(JPanel window){
		JLabel south = new JLabel ("Software v3.6.1");		//Label for the bottom info
		south.setBackground(Color.orange);					//Background color
		south.setFont(new Font("Header",Font.ITALIC, 12));	//Header font. Size 18.
		south.setForeground(Color.BLACK);					//Font color
		south.setHorizontalAlignment(SwingConstants.RIGHT);			//Centered alignment
		south.setOpaque(true);								//Set visible
		
		window.add(south, BorderLayout.SOUTH);					//Final label added to the panel
	}
	
	private JPanel panelExplorer() {
		JPanel result = new JPanel();
		result.setBorder(BorderFactory.createEmptyBorder(50,10,40,10));
		this.explorerFiles = new JFileChooser();
		
		JButton go = new JButton(" Escoge archivo .zip ");
		go.setPreferredSize(new Dimension(250,30));
		go.setHorizontalAlignment(SwingConstants.CENTER);
		go.addActionListener(new ALExplorer(this.explorerFiles));
		
		result.add(go);
		
		return result;
	}
	
	private JPanel panelClassifiers() {
		JPanel result = new JPanel();
		result.setBorder(BorderFactory.createTitledBorder("Parámetros"));
		result.setLayout(new GridLayout(1,4));
		
		JLabel title = new JLabel("Escoge clasificador:  ");			//Title of the module
		title.setHorizontalAlignment(SwingConstants.CENTER);	//Text placed in center
		title.setFont(new Font("Ja", Font.PLAIN, 12));	//text font. Big and bold
		title.setPreferredSize(new Dimension(100,30));
		result.add(title);							//Label added to result
		
		JComboBox<String> comboBox = new JComboBox<String>(this.CLASSIFIER_OPTIONS);	//Option pane for the options
		comboBox.setBorder(BorderFactory.createEmptyBorder(40,5,40,5));
		result.add(comboBox);							//Combobox added to the result
		
		JPanel panelFiles = new JPanel();
		panelFiles.setBorder(BorderFactory.createEmptyBorder(40,10,40,10));
		panelFiles.setLayout(new FlowLayout());
		JLabel label = new JLabel("Tipo de archivo: ");			//Title of the module
		label.setHorizontalAlignment(SwingConstants.LEFT);	//Text placed in center
		label.setFont(new Font("Ja", Font.PLAIN, 12));	//text font. Big and bold
		panelFiles.add(label);
		
		 JCheckBox opcion1 = new JCheckBox("Señal");
		 
		 JCheckBox opcion2 = new JCheckBox("Imagen");
		 panelFiles.add(opcion1);
		 panelFiles.add(opcion2);
		 
		 result.add(panelFiles);
	
		JButton go = new JButton(" Ejecutar ");	//Execute button
		go.addActionListener(new ALMainView(comboBox, opcion1));
		result.add(go);
				
		return result;
	}
	
	private JScrollPane panelResults() {
		JScrollPane scroll;
				
		taDisplay = new JTextArea();
		taDisplay.setLineWrap(true);
		taDisplay.setEditable(false);
		taDisplay.setFocusable(true);
		DefaultCaret caret = (DefaultCaret) taDisplay.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		taDisplay.setText("Consola de resultados: \n");
		
		scroll = new JScrollPane(taDisplay);
		
		return scroll;
	}
	
	/*
	 * ********************************************
	 * 				DISPATCHER
	 * ********************************************
	 */

	public void update(Object datos) {
		Context c = (Context) datos;
		Integer cas = (Integer) c.getEvento();
		
		switch(cas) {
		case DispatcherResults.readZipOK:
			tZip = (TZip) c.getDatos();
			taDisplay.append(tZip.toString());
			taDisplay.append("\nSe han obtenido los archivos del zip correctamente.\n");
			break;
		case DispatcherResults.readZipError:
			taDisplay.append("No se ha podido leer el archivo zip.\n");
			break;	
		}
		
	}
	
	/*
	 * ********************************************
	 * 				OTROS
	 * ********************************************
	 */
	
	public TZip gettZip() {
		return tZip;
	}

	public void settZip(TZip tZip) {
		this.tZip = tZip;
	}
	
	public void UpdateArea(String text) {
		taDisplay.append(text);
	}
	
	
}
