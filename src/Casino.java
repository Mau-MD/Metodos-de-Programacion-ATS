package src;

import src.util.Color;
import src.util.ConsoleUtil;

import java.util.Scanner;

import src.games.Blackjack;
import src.games.Connect;
import src.games.Guess;
import src.games.Slot;

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
      System.out.println("1. Máquina tragamonedas");
      System.out.println("2. Blackjack");
      System.out.println("3. Adivina adivinador");
      System.out.println("4. Conecta 4");
      System.out.println("5. Ir al menú principal");

      System.out.print(Color.YELLOW + "\nIngresa tu opción: " + Color.RESET);

      choice = scanner.nextInt();

      ConsoleUtil.clearConsole();

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
                "El programa mostrará 3 palabras aleatorias. Si 3 coinciden, ganas el triple, si dos coinciden, ganas el doble. Si ninguna coincide, pierdes.");

            while (slot.status != Game.GameStatus.COMPLETED) {
              slot.play();
            }
            ConsoleUtil.clearConsole();
            break;
          case 2:
            Blackjack blackjack = new Blackjack(user, bet);

            Game.handleInstructions(blackjack.NAME,
                "Cada turno la computadora y tú tirarán de dos dados, tu puntajé sera visible mientras que el de la computadora no. Cuando lo desees, puedes finalizar el juego y si tienes mayor puntaje que la computadora sin pasar de 21, ¡ganas!");

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
            ConsoleUtil.clearConsole();
            break;
          case 3:
            Guess guess = new Guess(user, bet);
            // Show Instructions
            Game.handleInstructions(guess.NAME,
                "Tienes que adivinar el número en el que estoy pensando. Entre menos intentos, ¡más recompensa!");

            while (guess.status != Game.GameStatus.COMPLETED) {
              // Ask for a number
              System.out.println("\n" + Color.CYAN + "Escribe un número entre 1 y 100" + Color.RESET);
              int number = scanner.nextInt();
              guess.play(number);
            }
            ConsoleUtil.clearConsole();
            break;
          case 4:

            Connect connect4 = new Connect(user, bet);
            // Show Instructions
            Game.handleInstructions(connect4.NAME,
                "El juego consiste en un tablero de 7 columnas y 7 filas. El objetivo es conectar 4 fichas en una línea horizontal, vertical o diagonal. ¡Gana el que consiga 4 en el primer turno!");

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
            ConsoleUtil.clearConsole();
            break;
        }

        System.out.println("\n" + Color.CYAN + "Que deseas hacer ahora?" + Color.RESET);
        System.out.println("1. Repetir el juego");
        System.out.println("2. Cambiar de juego");
        System.out.println("3. Ir al menú principal");

        int repeatChoice = scanner.nextInt();
        switch (repeatChoice) {
          case 1:
            repeatGame = true;
            ConsoleUtil.clearConsole();
            break;
          case 2:
            repeatGame = false;
            ConsoleUtil.clearConsole();
            break;
          case 3:
            repeatGame = false;
            ConsoleUtil.clearConsole();
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

    ConsoleUtil.clearConsole();

    int choice = 0;

    while (true) {

      System.out.println("\n" + Color.CYAN + "Elige la opción que desees\n" + Color.RESET);
      System.out.println("1. Jugar");
      System.out.println("2. Consultar saldo");
      System.out.println("3. Ver historial de juegos");
      System.out.println("4. Salir");
      System.out.print(Color.YELLOW + "\nIngresa tu opción: " + Color.RESET);
      choice = scanner.nextInt();

      ConsoleUtil.clearConsole();

      switch (choice) {
        case 1:
          handleGame(user);
          break;
        case 2:
          System.out.println("\nTu saldo es: " + Color.GREEN + user.getBalance() + Color.RESET);
          ConsoleUtil.pressAnyKeyToContinue();
          ConsoleUtil.clearConsole();
          break;
        case 3:
          user.playHistory.printPlayHistory(10);
          ConsoleUtil.pressAnyKeyToContinue();
          ConsoleUtil.clearConsole();
          break;
        case 4:
          System.out.println("\nGracias por jugar!");
          scanner.close();
          System.exit(0);
          break;
        default:
          System.out.println(Color.RED + "La opción que elegiste es inválida" + Color.RESET);
          break;
      }
    }
  }

  public static void main(String[] args) {

    ConsoleUtil.clearConsole();

    System.out.println(Color.BLUE + "Ingresa tu nombre de usuario:" + Color.RESET);
    String username = scanner.nextLine();

    ConsoleUtil.clearConsole();

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
