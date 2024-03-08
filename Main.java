import java.util.Scanner;
import java.util.Random;


public class Main {
    private static double saldo = 500.0;

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
                    consultarSaldo();
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
        } while (opcion != 5);

    }


    public static void consultarSaldo() {
        System.out.println("Saldo actual: $" + saldo);
    }

    public static void blackjack() {
    }

    public static void slot(Scanner scanner) {
        double apuesta = solicitarApuesta(scanner);
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
        }


    }

    public static void ruleta() {
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

}