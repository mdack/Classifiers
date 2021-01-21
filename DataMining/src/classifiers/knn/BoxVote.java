package classifiers.knn;

import java.util.ArrayList;

public class BoxVote {
	private ArrayList<String> classes;
    private ArrayList<Double> votes;

    public BoxVote(){
        classes = new ArrayList<String>();
        votes = new ArrayList<Double>();
    }
    private double totalVotes(){
        double sum = 0;

        for(int i = 0; i < votes.size(); i++){
            sum += votes.get(i);
        }
        return sum;
    }

    /*public String getWinner(){
        double max = Double.MIN_VALUE;
        int index = -1;
        for(int i = 0; i < votes.size(); i++){
            if(votes.get(i) > max){
                index = i;
                max = votes.get(i);
            }
        }
        return classes.get(index);
    }
*/
    public String getWinner(double threshold){
        double max = Double.MIN_VALUE;
        int index = -1;
        double limit = totalVotes() * threshold;
        for(int i = 0; i < votes.size(); i++){
            if(votes.get(i) > max && votes.get(i) >= limit){
                index = i;
                max = votes.get(i);
            }
        }
        return classes.get(index);

    }

    public void add(String iClass, double weight){
        int index = classes.indexOf(iClass);

        if(index == -1){
            classes.add(iClass);
            votes.add(weight);
        }else{
            votes.set(index, votes.get(index) + weight);
        }
    }
}
