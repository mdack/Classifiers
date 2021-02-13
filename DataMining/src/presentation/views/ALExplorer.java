package presentation.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ALExplorer implements ActionListener{

	private int fileInt;
	private JFileChooser explorerFiles;
	private File selectedFile;
	
	private MainView instance;
	
	public ALExplorer() {
		this.explorerFiles = new JFileChooser();
	}
	
	public ALExplorer(MainView mainView) {
		instance = mainView;
		this.explorerFiles = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.explorerFiles.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Zip", "zip");
		explorerFiles.setFileFilter(filter);
		
		fileInt = this.explorerFiles.showOpenDialog(instance);

		selectedFile = this.explorerFiles.getSelectedFile();

		if ((selectedFile == null) || (selectedFile.getName().equals(""))) {
		 JOptionPane.showMessageDialog(instance, "Nombre de archivo inválido", "Nombre de archivo inválido", JOptionPane.ERROR_MESSAGE);
		} 
	}
	
	public int getFileInt() {
		return fileInt;
	}


	public void setFileInt(int fileInt) {
		this.fileInt = fileInt;
	}


	public File getSelectedFile() {
		return selectedFile;
	}


	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

}
