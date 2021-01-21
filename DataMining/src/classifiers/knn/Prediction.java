package classifiers.knn;

import java.io.Serializable;
import java.util.ArrayList;

public class Prediction implements Serializable {
	 	ArrayList<String> predictions;
	    int pixels;

	    public Prediction(int p){
	        this.pixels = p;
	        this.predictions = new ArrayList<>();
	    }

	    public ArrayList<String> getPredictions() {
	        return predictions;
	    }

	    public void setPredictions(ArrayList<String> predictions) {
	        this.predictions = predictions;
	    }

	    public int getPixels() {
	        return pixels;
	    }

	    public void setPixels(int pixels) {
	        this.pixels = pixels;
	    }
}
