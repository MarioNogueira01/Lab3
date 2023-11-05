package Lab3.Lab3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class BestFirst {

    Stack<State> path = new Stack<State>();
    private State actual;
    private Ilayout objective;

    public int gera = 0;//nós abertos
    public int fech = 0;//nós fechados
    public int exp = 0;//nós expandidos

    public int prof = 0;//profundidade da arvore

    public static class State {
        private Ilayout layout;
        private State father;
        private int g;

        private int h;

        /**
         * Constructor for State class
         * l nó que estou
         * n nó pai
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null) {
                g = l.getG() + father.g;// Corrected the calculation of 'g'
                h = l.getH();
            } else {
                g = 0;
                h = l.getH();
            }
        }

        /**
         * Returns a string representation of the state.
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Returns the cost associated with the state.
         */
        public int getG() {
            return g;
        }

        public int getH(){return h;}

        /**
         * Calculates the hash code for the state.
         */
        public int hashCode() {
            return toString().hashCode();
        }

        /**
         * Checks if two states are equal.
         */
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }

        public double getF() {
            return this.getG() + this.getH();
        }
    }

    /**
     * Generates a list of successor states for a given state.
     */
    final private List<State> sucessores(State n, Ilayout goal) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children(goal);
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    /**
     * Solves a problem using the IDA* algorithm.
     */

    final public Iterator<State> solve(Ilayout s, Ilayout goal) {

        State root = new State(s, null);

        this.actual = root;

        objective = goal;

        double bound = actual.getF();

        path.add(actual);


        double min = Double.MAX_VALUE;
        List<State> sucs;

        while (true) {
            if (path.isEmpty()){
                path.add(root);
            }
            min = Double.MAX_VALUE;
            while(!path.isEmpty()) {
                State current = path.pop();
                fech++;//nos fechados

                double F = current.getF();

                if (F > bound && F < min) {
                    min = F;
                }
                if (current.layout.isGoal(objective)){
                    List<State> result = new ArrayList<>();
                    result.add(current);
                    while (current != null) {
                        prof++;// profundidade da arvore
                        result.add(0, current.father);
                        current = current.father;
                    }
                    result.remove(result.remove(0));
                    return result.listIterator();
                } else if (F <= bound) {
                    sucs = this.sucessores(current, objective);
                    exp++;
                    for (State a : sucs) {

                        if (!path.contains(a)) {
                            gera++;
                            path.add(a);
                            //nos expandidos
                        }
                    }
                }
            }
            bound = min;
        }
    }
}
