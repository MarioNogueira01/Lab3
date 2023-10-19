package Lab3;

import java.util.*;

public class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    public static class State {
        private Ilayout layout;
        private State father;
        private double g;

        private int prof = -1;


        /**
         * Construtor for State class
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            prof ++;
            if (father != null)
                g = father.g + l.getG();
            else g = 0;
        }

        /**
         * Returns a string representation of the state..
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Returns the cost associated with the state.
         */
        public double getG() {
            return g;
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
    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    public static int heuristic(Ilayout s, Ilayout goal){
        return Math.abs(s.getNum() - goal.getNum());
    }


    /**
     * Solves a problem using the IDA* algorithm.
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) {

        objective = goal;
        abertos = new PriorityQueue<>(10,
                (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        int cut = 0;
        List<State> sucs;

        while (!abertos.isEmpty()) {
            this.actual = abertos.poll();
            if(abertos.isEmpty()){

            }

            if (this.actual.layout.isGoal(objective)) {// layout.equals(objective)) { //  verificar: usar isgoal()->Board
                List<State> result = new ArrayList<>();
                result.add(this.actual);
                while (this.actual != null) {
                    result.add(0, this.actual.father);
                    this.actual = this.actual.father;
                }
                result.remove(result.remove(0));
                return result.listIterator();
            } else {
                if(actual.prof <= )
                sucs = this.sucessores(actual);
                fechados.put(actual.layout, actual);
                for (State item : sucs) {
                    if (!fechados.containsKey(item.layout)) {
                        abertos.add(item);
                    }
                }
            }
        }
        return null;
    }
}
