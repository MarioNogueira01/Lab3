package Lab3;


import java.util.*;

import static Lab3.sequence.Log2;

public class Main {

    public static void main(String[] args) throws Exception {
            Scanner sc = new Scanner(System.in);
            BestFirst s = new BestFirst();
            int number = sc.nextInt();
        long startTimeMillis = System.currentTimeMillis();//temporizador
            Iterator<BestFirst.State> it = s.solve(new sequence(number,0 , heuristic(number, number*3)),
                    new sequence(number * 3, 0,0));
        long endTimeMillis = System.currentTimeMillis();//temporizador
        long executionTimeMillis = endTimeMillis - startTimeMillis;//temporizador
        long executionTimeSecs = executionTimeMillis/1000;//temporizador
           try {
               if (it == null) System.out.println("no solution found");
               else {
                   while (it.hasNext()) {
                       BestFirst.State i = it.next();
                       System.out.println(i);
                       if (!it.hasNext())
                           System.out.println("\n" + i.getG());

                   }
                   System.out.println("nos expandidos = " + s.exp);
                   System.out.println("nos fechados = " + s.fech);
                   System.out.println("nos abertos = " + s.gera);
                   System.out.println("profundidade= " + s.prof);

                   System.out.println("tempo de execucao : " + executionTimeSecs + " segundos e " + executionTimeMillis+" milisegundos");

               }
           }catch (Exception e) {
               throw new RuntimeException();
           }
            sc.close();
        }

    private static int heuristic(int i, int goalNum) {

        if(i == 0) return Integer.MAX_VALUE; // temp
        int result = 0;

        double exp = 0;
        if(i < 0){
            exp = Math.round(Log2(goalNum/(i-0.5)));
        }
        else{
            exp = Math.round(Log2(goalNum/(i+0.5)));
        }
        if(exp <= 0){
            double dist = goalNum - i;
            if (i < 0){
                result = (int) (Math.abs(dist)*2);
            }
            else{
                result = (int) Math.abs(dist);
            }
        }
        else{
            double y = (goalNum/Math.pow(2,exp) - i);
            double y_2 = (goalNum/Math.pow(2,exp-1) - i);

            y = y < 0 ? Math.abs(y *2) : Math.abs(y);
            y_2 = y_2 < 0 ? Math.abs(y_2 *2) : Math.abs(y_2);

            result = (int) Math.min(exp*3 + y, (exp-1)*3 + y_2);
        }
        return result;
    }

}

