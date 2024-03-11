    import java.util.Scanner;


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
            System.out.print("Ingresa la cantidad que deseas apostar: $");
            double apuesta = scanner.nextDouble();

            if (apuesta > saldo) {
                System.out.println("No tienes suficiente saldo para realizar esa apuesta.");
                return;
        }

        public static void slot() {
        }

        public static void ruleta() {
        }
    }
