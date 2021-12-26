package src;

import java.util.Random;
import java.util.Scanner;

import src.util.Color;
import src.util.ConsoleUtil;

/**
 * Clase de utilidad que ayuda con toda la logica que comparten todos los juegos
 */
public class Game {

  private static final String[] loserStrings = new String[] { "Jaja, perdiste.", "Loser.", "Te gano un programa...",
      "Hasta mi hermanita de 3 años juega mejor que tú", "Demasiado facil..." };

  public static enum GameStatus {
    NOT_STARTED, PLAYING, COMPLETED
  }

  public enum WinLoseStatus {
    WIN, LOSE, DRAW
  }

  /**
   * Le pide al usuario que ingrese la apuesta que quiere realizar. Si esta
   * apuesta es menor a la que tiene se le pide que ingrese mas dinero
   * 
   * @param user usuario que juega
   * @return monto apostado
   */
  public static float handleBet(User user) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("Tu saldo es de " + Color.GREEN + user.getBalance() + Color.RESET + "$");
    System.out.println(Color.PURPLE + "Ingrese la cantidad que desea apostar: " + Color.RESET);
    float amount = scanner.nextFloat();

    while (amount <= 0) {
      System.out.println(Color.RED + "No puedes apostar 0 o menos" + Color.RESET);
      amount = scanner.nextFloat();
    }

    while (amount > user.getBalance()) {

      ConsoleUtil.clearConsole();

      System.out.println(Color.RED + "Error: No tienes suficiente dinero\n" + Color.RESET);
      System.out.println("Tu saldo es " + Color.GREEN + user.getBalance() + Color.RESET
          + "$ y quisite apostar " + Color.GREEN + amount + Color.RESET + "$");
      System.out.println(Color.BLUE + "Deposita mas dinero para poder continuar: " + Color.RESET);
      float depositAmount = scanner.nextFloat();
      user.addBalance(depositAmount);

      System.out.println("\nTu nuevo saldo es de " + Color.GREEN + user.getBalance() + Color.RESET + "$");
      System.out.println(
          Color.PURPLE + "Ahora si. Ingrese nuevamente la cantidad que desea apostar: " + Color.RESET);
      amount = scanner.nextInt();
    }

    ConsoleUtil.clearConsole();

    System.out.println("Apostaste: " + Color.GREEN + amount + Color.RESET + "$\n");

    return amount;
  }

  /**
   * Muestra en consola que el usuario gano cierta cantidad de dinero. Se le
   * agrega a la cuenta del usuario y se agrega el juego al historial de partidas
   * 
   * @param user     usuario que gano
   * @param bet      cantidad apostada
   * @param ratio    cuanto se multiplica la cantidad apostada
   * @param gameName nombre del juego
   */
  public static void handleWin(User user, float bet, float ratio, String gameName) {
    float oldBalance = user.getBalance();

    float balanceToAdd = bet * ratio - bet;

    user.addBalance(balanceToAdd);

    System.out
        .println(
            Color.GREEN + "\n\nFelicidades, has ganado " + Color.GREEN + balanceToAdd + Color.RESET
                + "$");
    System.out.println(Color.CYAN + "Saldo Anterior: " + Color.GREEN + oldBalance + Color.RESET + "$");
    System.out.println(
        Color.YELLOW + "Saldo Actual: " + Color.GREEN + user.getBalance() + Color.RESET + "$\n");
    user.playHistory.addPlay(gameName, bet, WinLoseStatus.WIN);
    ConsoleUtil.pressAnyKeyToContinue();
  }

  /**
   * Muestra en consola que el usuario perdio cierta cantidad de dinero. Se le
   * resta a la cuenta del usuario y se agrega el juego al historial de partidas
   * 
   * @param user     usuario que perdio
   * @param bet      cantidad apostada
   * @param gameName nombre del juego
   */
  public static void handleLose(User user, float bet, String gameName) {
    float oldBalance = user.getBalance();

    user.removeBalance(bet);

    System.out.println(Color.RED + "\n\nLo siento, has perdido " + bet + Color.RESET + "$");
    System.out.println(Color.CYAN + "Saldo Anterior: " + Color.GREEN + oldBalance + Color.RESET + "$");
    System.out.println(
        Color.YELLOW + "Saldo Actual: " + Color.GREEN + user.getBalance() + Color.RESET + "$\n");
    makeLoserSentence();
    user.playHistory.addPlay(gameName, bet, Game.WinLoseStatus.LOSE);
    ConsoleUtil.pressAnyKeyToContinue();
  }

  /**
   * Muestra en consola que el usuario quedo en empate. No se agrega ninguna
   * cantidad. Se agrega al historial de partidas
   * 
   * @param user     usuario que quedo en empate
   * @param gameName nombre del juego
   */
  public static void handleDraw(User user, String gameName) {

    float oldBalance = user.getBalance();
    System.out.println(Color.YELLOW + "\nEmpate!" + Color.RESET);
    System.out.println(Color.CYAN + "Saldo Anterior: " + Color.GREEN + oldBalance + Color.RESET + "$");
    System.out.println(
        Color.YELLOW + "Saldo Actual: " + Color.GREEN + user.getBalance() + Color.RESET + "$\n");
    user.playHistory.addPlay(gameName, 0, WinLoseStatus.DRAW);
    ConsoleUtil.pressAnyKeyToContinue();
  }

  /**
   * Muestra en consola una frase aleatoria burlandose del usuario
   */
  public static void makeLoserSentence() {
    int randomIndex = new Random().nextInt(loserStrings.length);
    System.out.println(Color.RED + loserStrings[randomIndex] + Color.RESET);
  }

  /**
   * Clase de utilidad que ayuda a mostrar las instrucciones del juego
   * 
   * @param gameTitle        nombre del juego
   * @param gameInstructions instrucciones del juego
   */
  public static void handleInstructions(String gameTitle, String gameInstructions) {
    System.out.println(Color.YELLOW + "Instrucciones de " + gameTitle + Color.RESET);
    System.out.println(Color.CYAN + gameInstructions + Color.RESET);
    ConsoleUtil.pressAnyKeyToContinue();
    ;
  }
}
