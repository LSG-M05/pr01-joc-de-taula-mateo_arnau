import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static double saldo = 1000;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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
                    // Agregar funcionalidad para consultar saldo
                    break;
                case 2:
                    blackjack();
                    break;
                case 3:
                    // Agregar funcionalidad para Slot
                    break;
                case 4:
                    // Agregar funcionalidad para Ruleta
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 5.");
            }
        } while (opcion != 5);
    }

    public static void blackjack() {
        System.out.println("--- Blackjack ---");
        System.out.println("Tu saldo actual es: $" + saldo);
        double apuesta = solicitarApuesta();

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

        mostrarManoConValores(manoJugador);
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
                mostrarManoConValores(manoJugador);
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

        mostrarManoConValores(manoCrupier);
        while (calcularValorMano(manoCrupier) < 17) {
            repartirCarta(manoCrupier, deck);
            mostrarManoConValores(manoCrupier);
        }

        int valorJugador = calcularValorMano(manoJugador);
        int valorCrupier = calcularValorMano(manoCrupier);

        if (valorJugador > valorCrupier || valorCrupier > 21) {
            System.out.println("¡Felicidades! Has ganado $" + apuesta + ".");
            saldo += apuesta;
        } else if (valorJugador == valorCrupier) {
            System.out.println("Es un empate. No ganas ni pierdes dinero.");
        } else {
            System.out.println("El crupier gana. Has perdido $" + apuesta + ".");
            saldo -= apuesta;
        }
    }

    // Métodos auxiliares
    public static double solicitarApuesta() {
        System.out.print("Ingresa la cantidad que deseas apostar: ");
        return scanner.nextDouble();
    }

    public static List<String> crearMazo() {
        List<String> mazo = new ArrayList<>();
        String[] palos = {"Corazones", "Diamantes", "Tréboles", "Picas"};
        String[] valores = {"As", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String palo : palos) {
            for (String valor : valores) {
                mazo.add(valor + " de " + palo);
            }
        }
        return mazo;
    }

    public static void barajarMazo(List<String> mazo) {
        Random random = new Random();
        for (int i = mazo.size() - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            String temp = mazo.get(index);
            mazo.set(index, mazo.get(i));
            mazo.set(i, temp);
        }
    }

    public static void repartirCarta(List<String> mano, List<String> mazo) {
        mano.add(mazo.remove(0));
    }

    public static int calcularValorMano(List<String> mano) {
        int valor = 0;
        int ases = 0;

        for (String carta : mano) {
            String valorCarta = carta.split(" ")[0];
            if (valorCarta.equals("As")) {
                ases++;
                valor += 11;
            } else if (valorCarta.equals("J") || valorCarta.equals("Q") || valorCarta.equals("K")) {
                valor += 10;
            } else {
                valor += Integer.parseInt(valorCarta);
            }
        }

        while (valor > 21 && ases > 0) {
            valor -= 10;
            ases--;
        }

        return valor;
    }

    public static void mostrarManoConValores(List<String> mano) {
        System.out.print("Tu mano: [");
        for (int i = 0; i < mano.size(); i++) {
            String carta = mano.get(i);
            String valorCarta = carta.split(" ")[0];
            if (i > 0) {
                System.out.print(", ");
            }
            if (valorCarta.equals("J") || valorCarta.equals("Q") || valorCarta.equals("K")) {
                System.out.print(carta + " (10)");
            } else if (valorCarta.equals("As")) {
                System.out.print(carta + " (11/1)");
            } else {
                System.out.print(carta + " (" + valorCarta + ")");
            }
        }
        System.out.println("]");
    }
}

