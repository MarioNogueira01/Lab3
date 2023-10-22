package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class sequence implements Ilayout {

    private int num;
    private int g;

    public sequence(int number, int g) {
        num = number;
        this.g = g;
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

        resultChildren.add(new sequence(num + 1, heuristic(1, num + 1, goalNum)));
        resultChildren.add(new sequence(num - 1, heuristic(2, num - 1, goalNum)));
        resultChildren.add(new sequence(num * 2, heuristic(3, num * 2, goalNum)));

        return resultChildren;
    }

    private int heuristic(int g, int i, int goalNum) {
        return g + Math.abs(goalNum - i);
    }

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
    public int getG() {
        return g;
    }
}
