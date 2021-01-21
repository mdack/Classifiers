package classifiers.kmeans;

import java.util.ArrayList;

public class Cluster {
	private int id_cluster;
    private ArrayList<Double> central_values;
    private ArrayList<Pixel> pixels;

    public Cluster(int id, Pixel p) {
        this.id_cluster = id;
        this.central_values = new ArrayList<>();
        this.pixels = new ArrayList<>();
        this.pixels.add(p);
        int v = (int) p.getValue();
        central_values.add((double) (v>>16&0x000000FF));
        central_values.add((double) (v>> 8&0x000000FF));
        central_values.add((double) (v>> 0&0x000000FF));
    }

    public int getId_cluster() {
        return id_cluster;
    }

    public ArrayList<Double> getCentral_values() {
        return central_values;
    }

    public ArrayList<Pixel> getPixels() {
        return pixels;
    }

    public double getCentralValue(int i) {
        return this.central_values.get(i);
    }

    public int getTotalPixels() {
        return pixels.size();
    }

    public double calculateValue(int pos){
        double sum_values = 0;

        for(Pixel p: pixels)
            sum_values += p.getColorValue(pos);

        return sum_values / pixels.size();
    }
}
