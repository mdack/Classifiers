package presentation.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ALMainView implements ActionListener{
	
	private JComboBox<String> c;
	private File file;
	
	public ALMainView(JComboBox<String> com, File f){
		this.c=com;
		this.file = f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(file.exists()) {
			
		}else {
			
		}
	}

}
