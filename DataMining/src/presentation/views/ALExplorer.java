package presentation.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import presentation.controller.BusinessEvent;
import presentation.controller.Controller;
import presentation.dispatcher.Context;

public class ALExplorer implements ActionListener{

	private JFileChooser explorerFiles;
	private File selectedFile;
		
	public ALExplorer(JFileChooser explorer) {
		this.explorerFiles = explorer;
		this.explorerFiles.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Zip", "zip");
		explorerFiles.setFileFilter(filter);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		explorerFiles.showOpenDialog(MainView.getInstance());
		selectedFile = this.explorerFiles.getSelectedFile();
		
		if ((selectedFile == null) || (selectedFile.getName().equals(""))) {
		 JOptionPane.showMessageDialog(MainView.getInstance(), "Nombre de archivo inválido", "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
		} else {
			Controller cont = Controller.getInstance();
			
			Context context = new Context();
			context.setDatos(this.selectedFile.getAbsolutePath());
			context.setEvento(BusinessEvent.READ_ZIP);
			
			cont.action(context);
		}
	}

}
