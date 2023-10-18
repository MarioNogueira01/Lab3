package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class sequence implements Ilayout{

    private int num;
    private int g;

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public int getNum(){return num;}

    public sequence(int number, int g){
        num = number;
        this.g = g;
    }

    @Override
    public List<Ilayout> children() {
        List<Ilayout> resultChildren = new ArrayList<>();

        resultChildren.add(new sequence(num+1,1));

        resultChildren.add(new sequence(num - 1,2));

        resultChildren.add(new sequence(num*2,3));

        return resultChildren;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        sequence sequence = (sequence) o;
        return num == sequence.num;
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
