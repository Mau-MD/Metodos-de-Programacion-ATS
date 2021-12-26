import java.util.Scanner;

// TODO: Siempre preguntar si quiere volver a jugar

public class Casino {

  static Scanner scanner = new Scanner(System.in);

  /**
   * El menu de los juegos. Tambien contiene la logica de turnos de cada juego
   * 
   * @param user El usuario que esta jugando
   */
  public static void handleGame(User user) {

    int choice = 0;

    while (true) {

      System.out.println("\n" + Color.CYAN + "Elige el juego que desees\n" + Color.RESET);
      System.out.println("1. Maquina tragamonedas");
      System.out.println("2. Blackjack");
      System.out.println("3. Adivina adivinador");
      System.out.println("4. Conecta 4");
      System.out.println("5. Ir al menu principal");

      System.out.print(Color.YELLOW + "\nIngresa tu opcion: " + Color.RESET);

      choice = scanner.nextInt();

      Util.clearConsole();

      if (choice == 5)
        return;
      if (choice >= 6) {
        System.out.println(Color.RED + "Error: Juego no disponible" + Color.RESET);
        continue;
      }

      boolean repeatGame = true;

      // Necesitamos esto para que el usuario pueda volver a jugar el mismo juego si
      // lo desea
      while (repeatGame) {
        float bet = Game.handleBet(user);

        switch (choice) {
          case 1:
            Slot slot = new Slot(user, bet);

            Game.handleInstructions(slot.NAME,
                "El programa mostrara 3 palabras aleatorias. Si 3 coinciden, ganas el triple, si dos coinciden, ganas el doble. Si ninguna coincide, pierdes.");

            while (slot.status != Game.GameStatus.COMPLETED) {
              slot.play();
            }
            Util.clearConsole();
            break;
          case 2:
            Blackjack blackjack = new Blackjack(user, bet);

            Game.handleInstructions(blackjack.NAME,
                "Cada turno la computadora y tu tiraran de dos dados, tu puntaje sera visible mientras que el de la computadora no. Cuando lo desees, puedes finalizar el juego y si tienes mayor puntaje que la computadora sin pasar de 21, ¡ganas!");

            while (blackjack.status != Game.GameStatus.COMPLETED) {
              blackjack.play();
              // Ask if the user wants to play another round
              System.out.println("\n" + Color.CYAN + "¿Deseas jugar otra ronda? (y/n)\n" + Color.RESET);
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
            Game.handleInstructions(guess.NAME,
                "Tienes que adivinar el numero en el que estoy pensando. Entre menos intentos, ¡mas recompensa!");

            while (guess.status != Game.GameStatus.COMPLETED) {
              // Ask for a number
              System.out.println("\n" + Color.CYAN + "Escribe un numero entre 1 y 100" + Color.RESET);
              int number = scanner.nextInt();
              guess.play(number);
            }
            Util.clearConsole();
            break;
          case 4:

            Connect connect4 = new Connect(user, bet);
            // Show Instructions
            Game.handleInstructions(connect4.NAME,
                "El juego consiste en un tablero de 7 columnas y 7 filas. El objetivo es conectar 4 fichas en una linea horizontal, vertical o diagonal. ¡Gana el que consiga 4 en el primer turno!");

            // Ask the user to select its token
            System.out.println("\n" + Color.CYAN + "Elige tu ficha 1)♥ 2)♠\n" + Color.RESET);
            int token = scanner.nextInt();
            connect4.setToken(token == 1);
            connect4.init();

            while (connect4.status != Game.GameStatus.COMPLETED) {
              System.out
                  .println(Color.CYAN + "\nIngresa la columna donde quieres colocar tu ficha" + Color.RESET);
              int column = scanner.nextInt();
              connect4.play(column);
            }
            Util.clearConsole();
            break;
        }

        System.out.println("\n" + Color.CYAN + "Que deseas hacer ahora?" + Color.RESET);
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

  /**
   * El menu principal del casino. Aqui se puede elegir entre jugar, ver tu saldo,
   * ver el historial de partidas, o salir del casino
   * 
   * @param user El usuario que esta jugando
   */
  public static void handleMenu(User user) {

    Util.clearConsole();

    int choice = 0;

    while (true) {

      System.out.println("\n" + Color.CYAN + "Elige la opcion que desees\n" + Color.RESET);
      System.out.println("1. Jugar");
      System.out.println("2. Consultar saldo");
      System.out.println("3. Ver historial de juegos");
      System.out.println("4. Salir");
      System.out.print(Color.YELLOW + "\nIngresa tu opcion: " + Color.RESET);
      choice = scanner.nextInt();

      Util.clearConsole();

      switch (choice) {
        case 1:
          handleGame(user);
          break;
        case 2:
          System.out.println("\nTu saldo es: " + Color.GREEN + user.getBalance() + Color.RESET);
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
          System.out.println(Color.RED + "La opcion que elegiste es invalida" + Color.RESET);
          break;
      }
    }
  }

  public static void main(String[] args) {

    Util.clearConsole();

    System.out.println(Color.BLUE + "Ingresa tu nombre de usuario:" + Color.RESET);
    String username = scanner.nextLine();

    Util.clearConsole();

    System.out.println("----------------------------------------");
    System.out.println(Color.RED + "Bienvenido, " + username + " al Casino ATS!" + Color.RESET);
    System.out.println("----------------------------------------");

    float balance;

    do {
      System.out.println(Color.BLUE + "Ingresa tu saldo inicial." + Color.RESET
          + " (Debe ser mayor a $50.00 y menor a $1000.00):");
      balance = scanner.nextFloat();
    } while (balance < 50.0 || balance > 1000.0);

    User user = new User(username, balance);

    handleMenu(user);

  }
}