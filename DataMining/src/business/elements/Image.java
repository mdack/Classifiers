package business.elements;

import java.util.ArrayList;
import java.util.List;

public class Image {

	List<List<Double>> image;
	int rows;
	int cols;
    int id_cluster = -1;
    private String name;
	
	public int getId_cluster() {
		return id_cluster;
	}
	public void setId_cluster(int id_cluster) {
		this.id_cluster = id_cluster;
	}
	public List<List<Double>> getImage() {
		return image;
	}
	public void setImage(List<List<Double>> image) {
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
	
	public double getPixel(int r, int c) {
		return image.get(r).get(c);
	}
	
	
	
	public double calculateValue() {
		int sum = 0;
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < cols; j++) {
				sum += image.get(i).get(j);
			}
		}
		
		return sum / (rows*cols);
	}

	public void convertMatrixToList(double[][] list_aux) {
		for(int r = 0; r < rows; r++) {
			List<Double> col = new ArrayList<>();
			for(int c = 0; c < cols; c++) {
				col.add(list_aux[r][c]);
			}
			image.remove(r);
			image.add(r, col);
		}
		
	}
		
	public double calculateDistanceTo(Image img) {
        double sum = 0;
        
        for(int i = 0; i < img.getRows(); i++) {
        	for(int j = 0; j < img.getCols(); j++) {
        		sum += Math.pow(this.getPixel(i, j) - img.getPixel(i, j), 2);
        	}
        }
        
		return sum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
