import java.util.Scanner;
import java.util.Random;

public class Main {
    private static double saldo = 500.0;
    private static final char[] SIMBOLOS = {'0', 'B', 'A', '&', '#', '@', '$'};
    private static final double[] GANANCIAS = {0.5, 0.8, 1.0, 1.5, 2.0, 2.5, 4.0, 1.0, 1.5, 2.0, 3.0, 5.0, 10.0, 50.0};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
                    slot(scanner);
                    break;
                case 4:
                    rizzRoulette(scanner);
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

    }

    public static void slot(Scanner scanner) {
        if (saldo <= 0) {
            System.out.println("No tienes suficiente saldo para apostar. ¡Recarga tu cuenta!");
            return;
        }

        double apuesta = solicitarApuesta(scanner);

        if (apuesta > saldo) {
            System.out.println("No puedes apostar más de lo que tienes en tu saldo.");
            return;
        }

        int vecesGirar = solicitarVecesGirar(scanner);

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


    public static void rizzRoulette(Scanner scanner) {
        if (saldo <= 0) {
            System.out.println("No tienes suficiente saldo para apostar. ¡Recarga tu cuenta!");
            return;
        }

        double apuesta = solicitarApuesta(scanner);
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
                apostarColor(scanner, apuesta);
                break;
            case 2:
                apostarParImpar(scanner, apuesta);
                break;
            case 3:
                apostarNumero(scanner, apuesta);
                break;
            case 4:
                apostarCuadrante(scanner, apuesta);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }


    private static void apostarColor(Scanner scanner, double apuesta) {
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

    private static double solicitarApuesta(Scanner scanner) {
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

    private static int solicitarVecesGirar(Scanner scanner) {
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


    }


