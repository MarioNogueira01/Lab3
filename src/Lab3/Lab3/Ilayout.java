package Lab3.Lab3;

import java.util.List;

public interface Ilayout {
    int getNum();

    /**
     * @return the children of the receiver.
     */
    List<Ilayout> children(Ilayout goal);


    /**
     * @return true if the receiver equals the argument l;
     * return false otherwise.
     */
    boolean isGoal(Ilayout l);

    int getH();

    /**
     * @return the cost for moving from the input config to the receiver.
     */
    int getG();

}
