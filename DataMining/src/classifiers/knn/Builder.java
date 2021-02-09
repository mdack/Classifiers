package classifiers.knn;

import java.util.Comparator;

public final class Builder {
	 /*
     * TODO(kevinb): when the dust settles, see if we still need this or can
     * just default to DEFAULT_CAPACITY.
     */
    private static final int UNSET_EXPECTED_SIZE = -1;

    private final Comparator<B> comparator;
    private int expectedSize = UNSET_EXPECTED_SIZE;
    private int maximumSize = Integer.MAX_VALUE;

    private Builder(Comparator<B> comparator) {
      this.comparator = checkNotNull(comparator);
    }

    /**
     * Configures this builder to build min-max priority queues with an initial expected size of
     * {@code expectedSize}.
     */
    @CanIgnoreReturnValue
    public Builder<B> expectedSize(int expectedSize) {
      checkArgument(expectedSize >= 0);
      this.expectedSize = expectedSize;
      return this;
    }

    /**
     * Configures this builder to build {@code MinMaxPriorityQueue} instances that are limited to
     * {@code maximumSize} elements. Each time a queue grows beyond this bound, it immediately
     * removes its greatest element (according to its comparator), which might be the element that
     * was just added.
     */
    @CanIgnoreReturnValue
    public Builder<B> maximumSize(int maximumSize) {
      checkArgument(maximumSize > 0);
      this.maximumSize = maximumSize;
      return this;
    }

    /**
     * Builds a new min-max priority queue using the previously specified options, and having no
     * initial contents.
     */
    public <T extends B> MinMaxPriorityQueue<T> create() {
      return create(Collections.<T>emptySet());
    }

    /**
     * Builds a new min-max priority queue using the previously specified options, and having the
     * given initial elements.
     */
    public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> initialContents) {
      MinMaxPriorityQueue<T> queue =
          new MinMaxPriorityQueue<T>(
              this, initialQueueSize(expectedSize, maximumSize, initialContents));
      for (T element : initialContents) {
        queue.offer(element);
      }
      return queue;
    }

    @SuppressWarnings("unchecked") // safe "contravariant cast"
    private <T extends B> Ordering<T> ordering() {
      return Ordering.from((Comparator<T>) comparator);
    }
}
