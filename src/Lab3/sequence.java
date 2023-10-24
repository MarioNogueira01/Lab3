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
        return Math.abs(goalNum - i);
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
    public int getH() {
        return h;
    }

    @Override
    public int getG() {
        return g;
    }
}
