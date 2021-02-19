package business.elements;

import java.io.InputStream;

public class FileData implements Comparable<FileData>{

	private InputStream data;
	private String name;
	
	public FileData(InputStream is, String n) {
		data = is;
		name = n;
	}
	
	public InputStream getData() {
		return data;
	}
	public void setData(InputStream data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(FileData o) {
		return name.compareTo(o.name);
	}
	
	
}
