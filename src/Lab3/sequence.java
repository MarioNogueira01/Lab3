package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class sequence implements Ilayout {

    private int num;
    private int g;

    private int h;

    public sequence(int number, int g, int h) {
        num = number;
        this.g = g;
        this.h = h;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public List<Ilayout> children(Ilayout goal) {
        List<Ilayout> resultChildren = new ArrayList<>();

        int goalNum = goal.getNum();

        resultChildren.add(new sequence(num + 1, 1, heuristic(num + 1,goalNum)));
        resultChildren.add(new sequence(num - 1, 2, heuristic(num - 1,goalNum)));
        resultChildren.add(new sequence(num * 2, 3, heuristic(num * 2,goalNum)));

        return resultChildren;
    }

    private int heuristic(int i, int goalNum) {
        TripleA g = (TripleA) goal;
        int num = this.num;
        if(num == 0) return Integer.MAX_VALUE; // temp
        int result = 0;

        double exp = 0;
        if(num < 0){
            exp = Math.round(TripleA.log2((double)goalNum/(num-0.5)));
        }
        else{
            exp = Math.round(TripleA.log2((double)goalNum/(num+0.5)));
        }
        if(exp <= 0){
            double dist = goalNum - num;
            if (num < 0){
                result = (int) (Math.abs(dist)*2);
            }
            else{
                result = (int) Math.abs(dist);
            }
        }
        else{
            double y = (goalNum/Math.pow(2,exp) - num);
            double y_2 = (goalNum/Math.pow(2,exp-1) - num);

            y = y < 0 ? Math.abs(y *2) : Math.abs(y);
            y_2 = y_2 < 0 ? Math.abs(y_2 *2) : Math.abs(y_2);

            result = (int) Math.min(exp*3 + y, (exp-1)*3 + y_2);
        }
        return result;
    }
    //TODO: acabar a heuristica
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        sequence otherSequence = (sequence) o;
        return num == otherSequence.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }

    @Override
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    @Override
    public int getH() {
        return h;
    }

    @Override
    public int getG() {
        return g;
    }
}
