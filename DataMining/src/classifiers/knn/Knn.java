package classifiers.knn;

public class Knn {
	 private static final int KNN = 3;
	    private static  final double DECISION_RULE = 0.5d;

	    private int[] weights;
	    private int threshold;

	    public KnnQueue queue;
	    private Dataset dataset;

	    public Knn(Dataset dataset) {
	        this.threshold = -1;
	        this.weights = new int[dataset.getAttributes().size()];
	        this.queue = new KnnQueue(KNN);
	        this.dataset = dataset;
	    }

	    public String runClassifier(Attribute att){

	        for(int i = 0; i < dataset.getTotalAttributes(); i++){
	            double distance = this.calculateEuclideanDistance(dataset.getElement(i), att.getColor());
	            queue.insert(new KnnQueueElement(i,distance));
	        }

	        applyWeight(this.queue);

	        return predictClass();
	    }

	    public double calculateEuclideanDistance(Attribute attribute, int color){
	        double distance = 0;

	        distance = distance + Math.pow(attribute.getColor() - color, 2.0);

	        return Math.sqrt(distance);
	    }


	    public void setWeights(int[] weights){
	        this.weights = weights;
	    }

	    public void applyWeight(KnnQueue queue){
	        Iterator<KnnQueueElement> it = queue.iterator();
	        while (it.hasNext()){
	            KnnQueueElement element = (KnnQueueElement) it.next();
	            try{
	                element.setWeight(1d / element.getDistance());
	            }catch (ArithmeticException e) {
	                element.setWeight(Double.MAX_VALUE);
	            }
	        }

	    }

	    private String predictClass(){
	        BoxVote box = new BoxVote();

	        while(!queue.isEmpty()){
	            KnnQueueElement e = queue.poll();
	            box.add(dataset.getElement(e.getIndex()).getAliment(), e.getWeight());
	        }
	        if(threshold == -1){
	            return box.getWinner(this.threshold);
	        }else{
	            return box.getWinner((double)threshold/100d);
	        }


	    }
}
