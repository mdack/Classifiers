package classifiers.knn;

public class KnnQueue implements Iterable<KnnQueueElement> {
	
	private static MinMaxPriorityQueue<KnnQueueElement> queue;
    private int size;

    public KnnQueue(int size){
        this.size = size;
        queue = MinMaxPriorityQueue.maximumSize(size).create();
    }

    public boolean insert(KnnQueueElement element) {

        return queue.add(element);
    }

    // TODO: implement this function
    @Override
    public Iterator<KnnQueueElement> iterator() {
        return queue.iterator();
    }

    public KnnQueueElement get(int index) {
        Iterator<KnnQueueElement> it = this.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i == index)
                return it.next();
            i++;
        }
        return null;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public KnnQueueElement poll() {
        return queue.poll();
    }
}
