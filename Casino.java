import java.util.Scanner;

// TODO: Siempre preguntar si quiere volver a jugar

public class Casino {

  static Scanner scanner = new Scanner(System.in);

  public static void handleGame(User user) {

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
      System.out.println("5. Ir al menu principal");

      System.out.print(Color.ANSI_YELLOW + "\nIngresa tu opcion: " + Color.ANSI_RESET);

      choice = scanner.nextInt();

      Util.clearConsole();

      notAvailable = false;

      if (choice == 5)
        return;
      if (choice >= 6) {
        notAvailable = true;
        continue;
      }

      boolean repeatGame = true;

      while (repeatGame) {
        float bet = Game.handleBet(user);

        switch (choice) {
          case 1:
            Slot slot = new Slot(user, bet);

            Game.handleInstructions("Maquina tragamonedas",
                "El programa mostrara 3 palabras aleatorias. Si 3 coinciden, ganas el triple, si dos coinciden, ganas el doble. Si ninguna coincide, pierdes.");

            while (slot.status != Game.GameStatus.COMPLETED) {
              slot.play();
            }
            Util.clearConsole();
            break;
          case 2:
            Blackjack blackjack = new Blackjack(user, bet);

            Game.handleInstructions("Blackjack",
                "Cada turno la computadora y tu tiraran de dos dados, tu puntaje sera visible mientras que el de la computadora no. Cuando lo desees, puedes finalizar el juego y si tienes mayor puntaje que la computadora sin pasar de 21, ¡ganas!");

            while (blackjack.status != Game.GameStatus.COMPLETED) {
              blackjack.play();
              // Ask if the user wants to play another round
              System.out.println("\n" + Color.ANSI_CYAN + "¿Deseas jugar otra ronda? (y/n)\n" + Color.ANSI_RESET);
              String ans = scanner.next();
              if (ans.equals("n")) {
                blackjack.finishGame();
                break;
              }
            }
            Util.clearConsole();
            break;
          case 3:
            Guess guess = new Guess(user, bet);
            // Show Instructions
            Game.handleInstructions("Adivina adivinador",
                "Tienes que adivinar el numero en el que estoy pensando. Entre menos intentos, ¡mas recompensa!");

            while (guess.status != Game.GameStatus.COMPLETED) {
              // Ask for a number
              System.out.println("\n" + Color.ANSI_CYAN + "Escribe un numero entre 1 y 100" + Color.ANSI_RESET);
              int number = scanner.nextInt();
              guess.play(number);
            }
            Util.clearConsole();
            break;
          case 4:

            Connect connect4 = new Connect(user, bet);
            // Show Instructions
            Game.handleInstructions("Conecta 4",
                "El juego consiste en un tablero de 7 columnas y 7 filas. El objetivo es conectar 4 fichas en una linea horizontal, vertical o diagonal. ¡Gana el que consiga 4 en el primer turno!");

            // Ask the user to select its token
            System.out.println("\n" + Color.ANSI_CYAN + "Elige tu ficha 1)♥ 2)♠\n" + Color.ANSI_RESET);
            int token = scanner.nextInt();
            connect4.setToken(token == 1);

            connect4.init();
            while (connect4.status != Game.GameStatus.COMPLETED) {
              System.out
                  .println(Color.ANSI_CYAN + "\nIngresa la columna donde quieres colocar tu ficha" + Color.ANSI_RESET);
              int column = scanner.nextInt();
              connect4.play(column);
            }
            Util.clearConsole();
            break;
        }

        System.out.println("\n" + Color.ANSI_CYAN + "Que deseas hacer ahora?" + Color.ANSI_RESET);
        System.out.println("1. Repetir el juego");
        System.out.println("2. Cambiar de juego");
        System.out.println("3. Ir al menu principal");

        int repeatChoice = scanner.nextInt();
        switch (repeatChoice) {
          case 1:
            repeatGame = true;
            Util.clearConsole();
            break;
          case 2:
            repeatGame = false;
            Util.clearConsole();
            break;
          case 3:
            repeatGame = false;
            Util.clearConsole();
            return;
        }
      }
    }

  }

  public static void handleMenu(User user) {

    Util.clearConsole();

    int choice = 0;
    boolean invalidChoice = false;

    while (true) {

      if (invalidChoice)
        System.out.println(Color.ANSI_RED + "La opcion que elegiste es invalida" + Color.ANSI_RESET);
      System.out.println("\n" + Color.ANSI_CYAN + "Elige la opcion que desees\n" + Color.ANSI_RESET);
      System.out.println("1. Jugar");
      System.out.println("2. Consultar saldo");
      System.out.println("3. Ver historial de juegos");
      System.out.println("4. Salir");
      System.out.print(Color.ANSI_YELLOW + "\nIngresa tu opcion: " + Color.ANSI_RESET);
      choice = scanner.nextInt();

      Util.clearConsole();
      invalidChoice = false;

      switch (choice) {
        case 1:
          handleGame(user);
          break;
        case 2:
          System.out.println("\nTu saldo es: " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET);
          Util.pressAnyKeyToContinue();
          Util.clearConsole();
          break;
        case 3:
          user.playHistory.printPlayHistory(10);
          Util.pressAnyKeyToContinue();
          Util.clearConsole();
          break;
        case 4:
          System.out.println("\nGracias por jugar!");
          System.exit(0);
          scanner.close();
          break;
        default:
          invalidChoice = true;
          break;
      }
    }
  }

  public static void main(String[] args) {

    Util.clearConsole();

    System.out.println(Color.ANSI_BLUE + "Ingresa tu nombre de usuario:" + Color.ANSI_RESET);
    String username = scanner.nextLine();

    Util.clearConsole();

    System.out.println("----------------------------------------");
    System.out.println(Color.ANSI_RED + "Bienvenido, " + username + " al Casino ATS!" + Color.ANSI_RESET);
    System.out.println("----------------------------------------");

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