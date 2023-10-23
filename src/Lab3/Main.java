package Lab3;


import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
            Scanner sc = new Scanner(System.in);
            BestFirst s = new BestFirst();
            int number = sc.nextInt();
            Iterator<BestFirst.State> it = s.solve(new sequence(number,0 , Math.abs(number - number*3)),
                    new sequence(number * 3, 0,0));
           try {
               if (it == null) System.out.println("no solution found");
               else {
                   while (it.hasNext()) {
                       BestFirst.State i = it.next();
                       System.out.println(i);
                       if (!it.hasNext())
                           System.out.println("\n" + (int) i.getG());

                   }
                   System.out.println("nos expandidos = " + s.exp);
                   System.out.println("nos fechados = " + s.fech);
                   System.out.println("nos abertos = " + s.count);
               }
           }catch (Exception e) {
               throw new RuntimeException();
           }
            sc.close();
        }

}

