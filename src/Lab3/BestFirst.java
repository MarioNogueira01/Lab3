package Lab3;

import java.util.*;

public class BestFirst {
    private State actual;
    private Ilayout objective;

    public static class State {
        private Ilayout layout;
        private State father;
        private int g;
        private int h;

        /**
         * Constructor for State class
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null) {
                g = l.getG() + father.g; // Corrected the calculation of 'g'
            } else {
                g = l.getG();
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

        public int getH() {
            return h;
        }

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

        Stack<State> path = new Stack<State>();
        this.actual = new State(s, null);

        int g = 0;
        objective = goal;

        int bound = actual.getG() - objective.getG();
        path.add(actual);

        while (true) {
            int t = IDASearch(path, g, bound);
            if (t == 0) {
                return path.iterator();
            }
            if (t == Integer.MAX_VALUE) {
                return null;
            }
        }
    }

    private int IDASearch(Stack<State> path, int g, int bound) {
        State last = path.lastElement();

        int f = g + last.getH(); // Changed 'last.h' to 'last.getH()'

        if (f > bound)
            return f;
        if (last.layout.isGoal(objective))
            return 0;
        int min = Integer.MAX_VALUE;
        List<State> successors = sucessores(last, objective); // Corrected the method name
        for (State a : successors) {
            if (!path.contains(a)) {
                path.push(a);
                int t = IDASearch(path, cost(last, a), bound);
                if (t == 0) {
                    return 0;
                }
                if (t < min) {
                    min = t;
                }
                path.pop();
            }
        }
        return min;
    }

    private int cost(State last, State a) {
        return last.getG() + a.getG();
    }
}
