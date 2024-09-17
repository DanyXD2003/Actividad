import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Actividad_Practica {
    public double PreciousInitial = 5;
    public double IngSoft = PreciousInitial;
    public double Minim = 0.1;
    public static int Timer = 5;
    public double wallet = 50;
    public int actions = 0;

    public void Buy(){
        if (wallet >= IngSoft){
            wallet = wallet - IngSoft;
            System.out.println("Se ha comprado una acción de IngSoft");
            actions += 1;
        }else{
            System.out.println("No tienes suficiente dinero para comprar una acción de IngSoft");
        }
    }

    public void Sell(){
        if (actions > 0){
            wallet = wallet + IngSoft;
            System.out.println("Se ha vendido una acción de IngSoft");
            actions -= 1;
        }else{
            System.out.println("No tienes acciones de IngSoft para vender");
        }
    }

    public void uptrend(){
        int repetitions = (int) (Math.random() * 10);
        for (int i = 0; i < repetitions; i++) {
            try {
                int election = (int) (Math.random() * 99);

                if (election >= 95) {
                    System.out.println("\nEl precio se mantiene en: " + IngSoft);
                    Action();
                } else if (election >= 40) {
                    priceUp();
                } else if (election >= 0) {
                    priceDown();
                }

                Thread.sleep(Timer);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public void downtrend(){
        int repetitions = (int) (Math.random() * 10);
        for (int i = 0; i < repetitions; i++) {
            try {
                int election = (int) (Math.random() * 99);

                if (election >= 95) {
                    System.out.println("\nEl precio se mantiene en: " + IngSoft);
                    Action();
                }else if (election >=60 ) {
                    priceUp();
                } else if (election >= 0) {
                    priceDown();
                }

                Thread.sleep(Timer);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public void NormalTrend() {
        int repetitions = (int) (Math.random() * 10);
        for (int i = 0; i < repetitions; i++) {
            try {
                int election = (int) (Math.random() * 99);

                if (election >= 90) {
                    System.out.println("\nEl precio se mantiene en: " + IngSoft);
                    Action();
                } else if (election >= 45) {
                    priceUp();
                } else if (election >= 0) {
                    priceDown();
                }

                Thread.sleep(Timer);
            } catch (InterruptedException e) {
                System.out.println(e);
            }

        }
    }

    public void UpDownStay() {
        int trend = (int) (Math.random() * 3);
        if (trend == 0) {
            System.out.println("\n\nLa tendencia es normal\n");
            NormalTrend();
        } else if (trend == 1) {
            System.out.println("\n\nLa tendencia es alcista\n");
            uptrend();
        } else if (trend == 2) {
            System.out.println("\n\nLa tendencia es bajista\n");
            downtrend();
        }

    }

    public void priceUp() {
        float Percentage = (float) (Math.random() * 50)/1000;
        IngSoft = IngSoft + (PreciousInitial * Percentage) ;
        System.out.println("\nEl precio sube a: " + IngSoft);
        Action();
    }

    public void priceDown() {
        float Percentage = (float) (Math.random() * 50)/1000;
        IngSoft = IngSoft - (PreciousInitial * Percentage) ;
        if (IngSoft <= Minim){
            IngSoft = Minim;
        }
        System.out.println("\nEl precio baja a: " + IngSoft);
        Action();
    }

    public void Action(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Scanner in = new Scanner(System.in);
        int option = 0;
        String input = "";
        Callable<String> tareaEntrada = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("\nIngresa una opción: ");
                System.out.println("1. Comprar acción de IngSoft");
                System.out.println("2. Vender acción de IngSoft");
                System.out.println("3. Balance de wallet");

                return in.nextLine();
            }
        };

        Future<String> future = executor.submit(tareaEntrada);
        try {

            input = future.get(5, TimeUnit.SECONDS);
            option = Integer.parseInt(input);
            if (option == 1){
                Buy();
            } else if (option == 2){
                Sell();
            }else if (option == 3){
                System.out.println("Tu balance es: " + wallet);
            } else {
                System.out.println("Opción no válida");
            }
            input = "4";

        } catch (TimeoutException e) {


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        Actividad_Practica ap = new Actividad_Practica();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                ap.UpDownStay();

            }
        };
        timer.schedule(task, 0, Timer);

    }
}
