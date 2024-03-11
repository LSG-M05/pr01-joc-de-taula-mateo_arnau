import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;


    public class Main {
        public Main() {
        }

        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            int opcion;
            do {
                System.out.println("1. Consultar saldo");
                System.out.println("2. Blackjack");
                System.out.println("3. Slot");
                System.out.println("4. Ruleta");
                System.out.println("5. Salir");
                System.out.print("Elige una opción: ");
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        break;
                    case 2:
                        blackjack();
                        break;
                    case 3:
                        slot();
                        break;
                    case 4:
                        ruleta();
                        break;
                    case 5:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, elige una opción del 1 al 5.");
                }
            } while(opcion != 5);

        }

        public static void blackjack() {
            System.out.println("--- Blackjack ---");
            System.out.println("Tu saldo actual es: $" + saldo);
            double apuesta = solicitarApuesta(scanner);

            if (apuesta > saldo) {
                System.out.println("No tienes suficiente saldo para realizar esa apuesta.");
                return;
        }
            List<String> deck = crearMazo();
            barajarMazo(deck);

            List<String> manoJugador = new ArrayList<>();
            List<String> manoCrupier = new ArrayList<>();
            repartirCarta(manoJugador, deck);
            repartirCarta(manoCrupier, deck);
            repartirCarta(manoJugador, deck);
            repartirCarta(manoCrupier, deck);

            System.out.println("Tu mano: " + manoJugador);
            System.out.println("Mano del crupier: [" + manoCrupier.get(0) + ", ***]");

            if (calcularValorMano(manoJugador) == 21) {
                System.out.println("¡Blackjack! ¡Felicidades! Has ganado $" + (apuesta * 2.5) + ".");
                saldo += apuesta * 2.5;
                return;
            }
            while (true) {
                System.out.print("¿Quieres pedir otra carta o plantarte? (pedir/plantar): ");
                String decision = scanner.next();
                if (decision.equalsIgnoreCase("pedir")) {
                    repartirCarta(manoJugador, deck);
                    System.out.println("Tu mano: " + manoJugador);
                    if (calcularValorMano(manoJugador) > 21) {
                        System.out.println("Te has pasado de 21. Has perdido $" + apuesta + ".");
                        saldo -= apuesta;
                        return;
                    }
                } else if (decision.equalsIgnoreCase("plantar")) {
                    break;
                } else {
                    System.out.println("Opción inválida. Por favor, ingresa 'pedir' o 'plantar'.");
                }
            }




            public static void slot() {
        }

        public static void ruleta() {
        }
    }

        public static List<String> crearMazo() {

        }

        public static void barajarMazo(List<String> mazo) {

        }

        public static void repartirCarta(List<String> mano, List<String> mazo) {

        }
        public static int calcularValorMano(List<String> mano) {

        }
