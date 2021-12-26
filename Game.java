import java.util.Random;
import java.util.Scanner;

public class Game {

  private static final String[] loserStrings = new String[] { "Jaja, perdiste.", "Loser.", "Te gano un programa...",
      "Hasta mi hermanita de 3 años juega mejor que tú", "Demasiado facil..." };

  public static enum GameStatus {
    NOT_STARTED, PLAYING, COMPLETED
  }

  public static float handleBet(User user) {

    Scanner scanner = new Scanner(System.in);

    System.out.println("Tu saldo es de " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET + "$");
    System.out.println(Color.ANSI_PURPLE + "Ingrese la cantidad que desea apostar: " + Color.ANSI_RESET);
    float amount = scanner.nextFloat();

    while (amount <= 0) {
      System.out.println(Color.ANSI_RED + "No puedes apostar 0 o menos" + Color.ANSI_RESET);
      amount = scanner.nextFloat();
    }

    while (amount > user.getBalance())

    {

      Util.clearConsole();

      System.out.println(Color.ANSI_RED + "Error: No tienes suficiente dinero\n" + Color.ANSI_RESET);
      System.out.println("Tu saldo es " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET
          + "$ y quisite apostar " + Color.ANSI_GREEN + amount + Color.ANSI_RESET + "$");
      System.out.println(Color.ANSI_BLUE + "Deposita mas dinero para poder continuar: " + Color.ANSI_RESET);
      float depositAmount = scanner.nextFloat();
      user.addBalance(depositAmount);

      System.out.println("\nTu nuevo saldo es de " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET + "$");
      System.out.println(
          Color.ANSI_PURPLE + "Ahora si. Ingrese nuevamente la cantidad que desea apostar: " + Color.ANSI_RESET);
      amount = scanner.nextInt();
    }

    Util.clearConsole();

    System.out.println("Apostaste: " + Color.ANSI_GREEN + amount + Color.ANSI_RESET + "$\n");

    return amount;
  }

  // Old balance should be BEFORE the bet
  public static void handleWin(User user, float bet, float ratio, String gameName) {
    // This doesnt work because we cant have 3 times the amount
    float oldBalance = user.getBalance();

    float balanceToAdd = bet * ratio - bet;

    user.addBalance(balanceToAdd);

    System.out
        .println(
            Color.ANSI_GREEN + "\n\nFelicidades, has ganado " + Color.ANSI_GREEN + balanceToAdd + Color.ANSI_RESET
                + "$");
    System.out.println(Color.ANSI_CYAN + "Saldo Anterior: " + Color.ANSI_GREEN + oldBalance + Color.ANSI_RESET + "$");
    System.out.println(
        Color.ANSI_YELLOW + "Saldo Actual: " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET + "$\n");
    user.playHistory.addPlay(gameName, bet, PlayInfo.Status.WIN);
    Util.pressAnyKeyToContinue();
  }

  public static void handleLose(User user, float bet, String gameName) {
    float oldBalance = user.getBalance();

    user.removeBalance(bet);

    System.out.println(Color.ANSI_RED + "\n\nLo siento, has perdido " + bet + Color.ANSI_RESET + "$");
    System.out.println(Color.ANSI_CYAN + "Saldo Anterior: " + Color.ANSI_GREEN + oldBalance + Color.ANSI_RESET + "$");
    System.out.println(
        Color.ANSI_YELLOW + "Saldo Actual: " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET + "$\n");
    makeLoserSentence();
    user.playHistory.addPlay(gameName, bet, PlayInfo.Status.LOSE);
    Util.pressAnyKeyToContinue();
  }

  public static void handleDraw(User user, String gameName) {

    float oldBalance = user.getBalance();
    System.out.println(Color.ANSI_YELLOW + "\nEmpate!" + Color.ANSI_RESET);
    System.out.println(Color.ANSI_CYAN + "Saldo Anterior: " + Color.ANSI_GREEN + oldBalance + Color.ANSI_RESET + "$");
    System.out.println(
        Color.ANSI_YELLOW + "Saldo Actual: " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET + "$\n");
    user.playHistory.addPlay(gameName, 0, PlayInfo.Status.DRAW);
    Util.pressAnyKeyToContinue();
  }

  public static void makeLoserSentence() {
    int randomIndex = new Random().nextInt(loserStrings.length);
    System.out.println(Color.ANSI_RED + loserStrings[randomIndex] + Color.ANSI_RESET);
  }

  public static void handleInstructions(String gameTitle, String gameInstructions) {
    System.out.println(Color.ANSI_YELLOW + "Instrucciones de " + gameTitle + Color.ANSI_RESET);
    System.out.println(Color.ANSI_CYAN + gameInstructions + Color.ANSI_RESET);
    Util.pressAnyKeyToContinue();
    ;
  }
}
