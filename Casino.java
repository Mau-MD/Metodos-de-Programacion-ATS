import java.util.Scanner;

public class Casino {

  public static void handleGame(User user) {

    Scanner scanner = new Scanner(System.in);

    int choice = 0;
    boolean notAvailable = false;

    while (true) {

      if (notAvailable)
        System.out.println(Color.ANSI_RED + "Error: Juego no disponible" + Color.ANSI_RESET);

      System.out.println("\n" + Color.ANSI_CYAN + "Elige el juego que desees\n" + Color.ANSI_RESET);
      System.out.println("1. Maquina tragamonedas");
      System.out.println("2. Blackjack");
      System.out.println("3. Adivina adivinador");
      System.out.println("4. Conecta 4");
      System.out.println("5. Salir");

      choice = scanner.nextInt();

      Util.clearConsole();

      if (choice == 5)
        return;
      if (choice >= 6) {
        notAvailable = true;
        continue;
      }

      float bet = Game.handleBet(user);

      switch (choice) {
        case 3:
          Guess game = new Guess();
          game.play(user, bet);
          break;
      }

    }
  }

  public static void handleMenu(User user) {

    Scanner scanner = new Scanner(System.in);

    int choice = 0;
    boolean invalidChoice = false;

    while (true) {

      if (invalidChoice)
        System.out.println(Color.ANSI_RED + "Opcion invalida" + Color.ANSI_RESET);
      System.out.println("\n" + Color.ANSI_CYAN + "Elige la opcion que desees\n" + Color.ANSI_RESET);
      System.out.println("1. Jugar");
      System.out.println("2. Checar saldo");
      System.out.println("3. Salir");
      System.out.print("\nIngresa tu opcion: ");
      choice = scanner.nextInt();

      Util.clearConsole();

      switch (choice) {
        case 1:
          handleGame(user);
          break;
        case 2:
          System.out.println("\nTu saldo es: " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET);
          break;
        case 3:
          System.out.println("\nGracias por jugar!");
          System.exit(0);
          break;
        default:

          invalidChoice = true;
          break;
      }
    }

  }

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.println(Color.ANSI_BLUE + "Ingresa tu nombre de usuario:" + Color.ANSI_RESET);
    String username = scanner.nextLine();

    Util.clearConsole();

    System.out.println("--------------------");
    System.out.println(Color.ANSI_RED + "Bienvenido, " + username + "!" + Color.ANSI_RESET);
    System.out.println("--------------------");

    float balance;

    do {
      System.out.println(Color.ANSI_BLUE + "Ingresa tu saldo inicial." + Color.ANSI_RESET
          + " (Debe ser mayor a $50.00 y menor a $1000.00):");
      balance = scanner.nextFloat();
    } while (balance < 50.0 || balance > 1000.0);

    User user = new User(username, balance);

    handleMenu(user);
  }
}