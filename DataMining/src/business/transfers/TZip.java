package business.transfers;

import java.util.Collections;
import java.util.List;

import business.elements.FileData;

public class TZip {
	private String path;
	private boolean areSignals;
	private List<FileData> list;

	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	public boolean isAreSignals() {
		return areSignals;
	}
	public void setAreSignals(boolean areSignals) {
		this.areSignals = areSignals;
	}

	public List<FileData> getList() {
		return list;
	}
	public void setList(List<FileData> l) {
		this.list = l;
		Collections.sort(list);
	}
	
	public String toString() {
		String text = "Archivos del zip: " + this.path + "\n";
		
		text += "\n****************************************************************\n";
		
		for(int i = 0; i < list.size(); i++) {
			text += "Archivo " + i + ": " + list.get(i).toString() + "\n";
		}
		
		text += "\n****************************************************************\n";
		
		text += "\nUn total de " + list.size() + " archivos obtenidos\n";
		
		return text;
	}
	
	}
