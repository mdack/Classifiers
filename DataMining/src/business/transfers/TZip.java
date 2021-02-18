package business.transfers;

import java.io.InputStream;
import java.util.List;

import business.elements.FileData;

public class TZip {
	private String path;
	private boolean areSignals;
	private List<FileData> list;
	private List<InputStream> files;
	private List<String> names;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<InputStream> getFiles() {
		return files;
	}
	public void setFiles(List<InputStream> files) {
		this.files = files;
	}
	public boolean isAreSignals() {
		return areSignals;
	}
	public void setAreSignals(boolean areSignals) {
		this.areSignals = areSignals;
	}
	public void setNames(List<String> list_names) {
		names = list_names;	
	}
	public List<String> getNames(){
		return names;
	}
	public List<FileData> getList() {
		return list;
	}
	public void setList(List<FileData> list) {
		this.list = list;
	}
}
