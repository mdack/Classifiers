package business.elements;

import java.util.List;

public class Image {

	List<List<Integer>> image;
	int rows;
	int cols;
    int id_cluster = -1;
	
	public int getId_cluster() {
		return id_cluster;
	}
	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}
	public List<List<Integer>> getImage() {
		return image;
	}
	public void setImage(List<List<Integer>> image) {
		this.image = image;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	public int getPixel(int r, int c) {
		return image.get(r).get(c);
	}
	
	public float calculateValue() {
		int sum = 0;
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < cols; j++) {
				sum += image.get(i).get(j);
			}
		}
		
		return sum / (rows*cols);
	}
	
}
