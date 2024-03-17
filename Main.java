import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static double saldo = 500.0;
    private static final char[] SIMBOLOS = {'0', 'B', 'A', '&', '#', '@', '$'};
    private static final double[] GANANCIAS = {0.5, 0.8, 1.0, 1.5, 2.0, 2.5, 4.0, 1.0, 1.5, 2.0, 3.0, 5.0, 10.0, 50.0};
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("Bienvenido a nuestro casino!!!");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Blackjack");
            System.out.println("3. Slot");
            System.out.println("4. Ruleta");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    consultarSaldo();
                    break;
                case 2:
                    blackjack();
                    break;
                case 3:
                    slot();
                    break;
                case 4:
                    rizzRoulette();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del 1 al 5.");
            }
        } while (opcion != 5);
    }

    public static void consultarSaldo() {
        System.out.println("Saldo actual: $" + saldo);
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

    public static void slot() {
        if (saldo <= 0) {
            System.out.println("No tienes suficiente saldo para apostar. ¡Recarga tu cuenta!");
            return;
        }

        double apuesta = solicitarApuesta();

        if (apuesta > saldo) {
            System.out.println("No puedes apostar más de lo que tienes en tu saldo.");
            return;
        }

        int vecesGirar = solicitarVecesGirar();

        for (int i = 0; i < vecesGirar; i++) {
            char[] simbolos = generarSimbolos();
            mostrarSimbolos(simbolos);

            double ganancia = calcularGanancia(simbolos, apuesta);
            saldo += ganancia;

            if (ganancia > 0) {
                System.out.println("¡GANASTE! Ganancia: $" + ganancia);
            } else {
                System.out.println("Perdiste. ¡Suerte la próxima vez!");
            }

            System.out.println("Saldo actual: $" + saldo);

            if (saldo <= 0) {
                System.out.println("Te has quedado sin saldo. ¡Recarga tu cuenta!");
                break;
            }
        }
    }

    public static void rizzRoulette() {
        if (saldo <= 0) {
            System.out.println("No tienes suficiente saldo para apostar. ¡Recarga tu cuenta!");
            return;
        }

        double apuesta = solicitarApuesta();
        if (apuesta > saldo) {
            System.out.println("No puedes apostar más de lo que tienes en tu saldo.");
            return;
        }

        System.out.println("Seleccione el tipo de apuesta:");
        System.out.println("1. Color (Rojo/Negro)");
        System.out.println("2. Par o Impar");
        System.out.println("3. Número");
        System.out.println("4. Cuadrante");
        int tipoApuesta = scanner.nextInt();

        switch (tipoApuesta) {
            case 1:
                apostarColor(apuesta);
                break;
            case 2:
                apostarParImpar(apuesta);
                break;
            case 3:
                apostarNumero(apuesta);
                break;
            case 4:
                apostarCuadrante(apuesta);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void apostarColor(double apuesta) {
        System.out.println("Seleccione el color:");
        System.out.println("1. Rojo");
        System.out.println("2. Negro");
        int color = scanner.nextInt();

        Color resultado = generarColor();

        if ((resultado == Color.ROJO && color == 1) || (resultado == Color.NEGRO && color == 2))
            ganar(apuesta * 2);
        else
            perder(apuesta);
    }

    private static void apostarParImpar(double apuesta) {
        System.out.println("Seleccione par o impar:");
        System.out.println("1. Par");
        System.out.println("2. Impar");
        int parImpar = scanner.nextInt();

        Random random = new Random();
        int resultado = random.nextInt(37); // Simulamos el giro de la ruleta

        if (resultado % 2 == 0) {
            if (parImpar == 1)
                ganar(apuesta * 2);
            else
                perder(apuesta);
        } else {
            if (parImpar == 2)
                ganar(apuesta * 2);
            else
                perder(apuesta);
        }
    }

    private static void apostarNumero(double apuesta) {
        System.out.println("Seleccione el número (0-36):");
        int numero = scanner.nextInt();

        Random random = new Random();
        int resultado = random.nextInt(37); // Simulamos el giro de la ruleta

        if (resultado == numero)
            ganar(apuesta * 30);
        else
            perder(apuesta);
    }

    private static void apostarCuadrante(double apuesta) {
        System.out.println("Seleccione el cuadrante:");
        System.out.println("1. Cuadrante 1 (1-9)");
        System.out.println("2. Cuadrante 2 (10-18)");
        System.out.println("3. Cuadrante 3 (19-27)");
        System.out.println("4. Cuadrante 4 (28-36)");
        int cuadrante = scanner.nextInt();

        Random random = new Random();
        int resultado = random.nextInt(37); // Simulamos el giro de la ruleta

        switch (cuadrante) {
            case 1:
                if (resultado > 0 && resultado < 10)
                    ganar(apuesta * 3);
                else
                    perder(apuesta);
                break;
            case 2:
                if (resultado > 9 && resultado < 19)
                    ganar(apuesta * 3);
                else
                    perder(apuesta);
                break;
            case 3:
                if (resultado > 18 && resultado < 28)
                    ganar(apuesta * 3);
                else
                    perder(apuesta);
                break;
            case 4:
                if (resultado > 27 && resultado < 37)
                    ganar(apuesta * 3);
                else
                    perder(apuesta);
                break;
            default:
                System.out.println("Cuadrante no válido.");
        }
    }

    enum Color {
        ROJO,
        NEGRO,
        VERDE
    }

    private static void ganar(double monto) {
        saldo += monto;
        System.out.println("¡GANASTE! Ganancia: $" + monto);
        System.out.println("Saldo actual: $" +saldo);
    }

    private static void perder(double monto) {
        saldo -= monto;
        System.out.println("Perdiste. ¡Suerte la próxima vez!");
        System.out.println("Saldo actual: $" +saldo);
    }

    private static Color generarColor() {
        Random random = new Random();
        int resultado = random.nextInt(37);
        if (resultado == 0)
            return Color.VERDE;
        else if (resultado >= 1 && resultado <= 18)
            return Color.ROJO;
        else
            return Color.NEGRO;
    }

    private static double solicitarApuesta() {
        System.out.println("Seleccione la cantidad de apuesta:");
        System.out.println("1. $0.2");
        System.out.println("2. $0.4");
        System.out.println("3. $0.8");
        System.out.println("4. $1");
        System.out.println("5. $2");
        System.out.println("6. $5");
        System.out.println("7. $10");
        System.out.println("8. $50");
        System.out.println("9. $100");

        int opcionApuesta = scanner.nextInt();
        return obtenerApuesta(opcionApuesta);
    }

    private static double obtenerApuesta(int opcion) {
        switch (opcion) {
            case 1:
                return 0.2;
            case 2:
                return 0.4;
            case 3:
                return 0.8;
            case 4:
                return 1.0;
            case 5:
                return 2.0;
            case 6:
                return 5.0;
            case 7:
                return 10.0;
            case 8:
                return 50.0;
            case 9:
                return 100.0;
            default:
                System.out.println("Opción no válida. Apuesta establecida a $0.0");
                return 0.0;
        }
    }

    private static int solicitarVecesGirar() {
        System.out.println("¿Cuántas veces deseas girar (1 o 5)?");
        return scanner.nextInt();
    }

    private static char[] generarSimbolos() {
        Random random = new Random();
        char[] simbolos = new char[3];
        for (int i = 0; i < 3; i++) {
            simbolos[i] = SIMBOLOS[random.nextInt(SIMBOLOS.length)];
        }
        return simbolos;
    }

    private static void mostrarSimbolos(char[] simbolos) {
        System.out.println("[" + simbolos[0] + "] [" + simbolos[1] + "] [" + simbolos[2] + "]");
    }

    private static double calcularGanancia(char[] simbolos, double apuesta) {
        String combinacion = "" + simbolos[0] + simbolos[1] + simbolos[2];
        for (int i = 0; i < SIMBOLOS.length; i++) {
            if (combinacion.equals("" + SIMBOLOS[i] + SIMBOLOS[i] + SIMBOLOS[i]))
                return apuesta * GANANCIAS[i + SIMBOLOS.length];
            else if (combinacion.substring(0, 2).equals("" + SIMBOLOS[i] + SIMBOLOS[i]) ||
                    combinacion.substring(1, 3).equals("" + SIMBOLOS[i] + SIMBOLOS[i]))
                return apuesta * GANANCIAS[i];
        }
        return -apuesta;
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
