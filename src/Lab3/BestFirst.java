package Lab3;

import java.util.*;

public class BestFirst {

    protected Queue<State> abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getF() - s2.getF()));

    private Map<Ilayout, State> fechados = new HashMap<>();;
    private State actual;
    private Ilayout objective;

    public int count = 0;//nós abertos
    public int fech = 0;//nós fechados
    public int exp = 0;//nós expandidos

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

        Stack<State> path = new Stack<State>();
        this.actual = new State(s, null);

        abertos.add(actual);

        objective = goal;

        int bound = actual.getH();
        path.add(actual);

        while (true) {
            int t = IDASearch(path, this.actual.getG(), bound);
            if (t == 0) {
                return path.iterator();
            }
            if (t == Integer.MAX_VALUE) {
                return null;
            }
            bound = t;
        }
    }

    private int IDASearch(Stack<State> path, int g, int bound) {

        List<State> sucs;

            State current = abertos.poll();

            int f = g + current.getH(); // Changed 'last.h' to 'last.getH()'

            if (f > bound){
                return f;
            }
            if (current.layout.isGoal(objective))
                return 0;
            int min = Integer.MAX_VALUE;

            sucs = this.sucessores(current, objective);

            exp++;
            count += sucs.size();
            for (State a : sucs) {
                if (!path.contains(a)) {
                    path.push(a);
                    abertos.add(a);
                    int t = IDASearch(path, cost(current, a), bound);
                    if (t == 0) {
                        return 0;
                    }
                    if (t < min)
                        min = t;
                    path.pop();
                    fechados.put(a.layout,a);
                    fech += 1;
                }
            }
            return min;
    }

    private int cost(State last, State a) {
        return last.getG() + a.getG();
    }
}
