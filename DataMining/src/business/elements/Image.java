package business.elements;

import java.util.ArrayList;
import java.util.List;

import business.classifiers.adaptative.AdaptativeElement;

public class Image implements AdaptativeElement{

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
		if(!image.isEmpty()) {
			int sum = 0;
			for(int i = 0; i < rows;i++) {
				for(int j = 0; j < cols; j++) {
					sum += image.get(i).get(j);
				}
			}
			
			return sum / (rows*cols);
		}
		else {
			return 0;
		}
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
	
	// -----------------------------------------------------------------------------------
	// PARA M�TODO ADAPTATIVO
	// -----------------------------------------------------------------------------------
    private MyState state;
    
	@Override
	public int getCurrentState() {
		int id;
		
		switch(state) {
		case ASIGNED:
			id = this.id_cluster;
			break;
		case INDETERMINATE:
			id = -1;
			break;
		default:
			id = -2;
			break;
		}
		
		return id;
	}
	
	public MyState getState() {
		return state;
	}
	public void setState(MyState state) {
		this.state = state;
	}
	
	@Override
	public void changeState(MyState newState, int id_c) {	
		
		if(newState == MyState.INDETERMINATE) {
			this.id_cluster = 0;
		}else if(newState == MyState.ASIGNED) {
			if(this.id_cluster != id_c) {
				this.id_cluster = id_c;
			}
		}
		
		state = newState;
	}
}
