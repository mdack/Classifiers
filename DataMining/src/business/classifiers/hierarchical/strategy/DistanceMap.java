package business.classifiers.hierarchical.strategy;

import java.util.*;

import business.classifiers.cluster.Cluster;
import business.classifiers.cluster.ClusterPair;

public class DistanceMap {

    private Map<String, Item> pairHash;
    private PriorityQueue<Item> data;

    private class Item implements Comparable<Item> {
        final ClusterPair pair;
        final String hash;
        boolean removed = false;

        Item(ClusterPair p) {
            pair = p;
            hash = hashCodePair(p);
        }

        @Override
        public int compareTo(Item o) {
            return pair.compareTo(o.pair);
        }

        @Override
        public String toString() {
            return hash;
        }
    }

    public DistanceMap() {
        data = new PriorityQueue<Item>();
        pairHash = new HashMap<String, Item>();
    }

    public List<ClusterPair> list() {
        List<ClusterPair> l = new ArrayList<ClusterPair>(data.size());
        for (Item clusterPair : data) {
            l.add(clusterPair.pair);
        }
        return l;
    }

	public ClusterPair findByCodePair(Cluster c1, Cluster c2) {
        String inCode = hashCodePair(c1, c2);
        ClusterPair cl = null;
        
        if(inCode != "") {
        	if(pairHash.containsKey(inCode))
        		cl =  pairHash.get(inCode).pair;
        }
        
        return cl;

    }

    public ClusterPair removeFirst() {
        Item poll = data.poll();
        
        while (poll != null && poll.removed) {
            poll = data.poll();
        }
        if (poll == null) {
            return null;
        }
        ClusterPair link = poll.pair;
        pairHash.remove(poll.hash);
        
        return link;
    }

    public boolean remove(ClusterPair link) {
        Item remove = pairHash.remove(hashCodePair(link));
        if (remove == null) {
            return false;
        }
        remove.removed = true;
        // data.remove(remove);  // bottleneck
        return true;
    }


    public boolean add(ClusterPair link) {
        Item e = new Item(link);
        Item existingItem = pairHash.get(e.hash);
        
        if (existingItem != null) {
            return false;
        } else {
            pairHash.put(e.hash, e);
            data.add(e);
            return true;
        }
    }

    /**
     * Peak into the minimum distance
     * @return
     */
    public Double minDist()
    {
        Item peek = data.peek();
        if (peek != null)
            return peek.pair.getLinkageDistance();
        else
            return null;
    }

    /**
     * Compute some kind of unique ID for a given cluster pair.
     * @return The ID
     */
    private String hashCodePair(ClusterPair link) {
        return hashCodePair(link.getlCluster(), link.getrCluster());
    }

    private String hashCodePair(Cluster lCluster, Cluster rCluster) {
        String text= "";
        if(lCluster != null && rCluster != null)
        	text = hashCodePairNames(lCluster.getId_cluster(), rCluster.getId_cluster());
        return text;
    }

    private String hashCodePairNames(int lId, int rId) {
        if (lId > rId) {
            return lId + "-" + rId;
        } else {
            return rId + "-" + lId;
        }
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
