package classifiers.knn;

public class KnnQueueElement implements Comparable<KnnQueueElement> {
	
	  private int index;
	    private double distance;
	    private double weight;

	    public KnnQueueElement(int index, double distance, double weight) {
	        this.index = index;
	        this.distance = distance;
	        this.weight = weight;
	    }

	    public  KnnQueueElement(int index, double distance){
	        this(index, distance, 1);
	    }

	    public int getIndex() {
	            return index;
	    }

	    public void setIndex(int index) {
	        this.index = index;
	    }


	    public double getDistance() {
	        return distance;
	    }

	    public void setDistance(double distance) {
	        this.distance = distance;
	    }

	    public double getWeight() {
	        return weight;
	    }

	    public void setWeight(double weight) {
	        this.weight = weight;
	    }

	    @Override
	    public int compareTo(KnnQueueElement o) {
	        return (this.distance < o.distance) ? -1 : (this.distance > o.distance) ? 1 : 0;
	    }
}
