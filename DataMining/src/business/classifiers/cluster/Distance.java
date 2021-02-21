package business.classifiers.cluster;

public class Distance implements Comparable<Distance>, Cloneable {

    private double distance;
    private double weight;

    public Distance() {
		this.distance = 0;
		this.weight = 1;
    }

    public Distance(double distance) {
		this.distance = distance;
		this.weight = 1;
    }

    public Distance(double distance, double weight) {
        this.distance = distance;
        this.weight = weight;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isNaN() {
        return distance == 0;
    }

    @Override
    public int compareTo(Distance distance) {
    	if(distance == null) {
    		return 1;
    	}
    	else {
    		return 0;
    	}
    }
    
    @Override
    public String toString() {
		return " distancia: " + distance + ", peso: " + weight + "\n";
	}
}
