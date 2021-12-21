import java.util.Random;
import java.util.Scanner;

public class Game {

  private static final String[] loserStrings = new String[] { "1", "2", "3", "4", "5" };

  public static float handleBet(User user) {

    Scanner scanner = new Scanner(System.in);

    System.out.println(Color.ANSI_PURPLE + "Ingrese la cantidad que desea apostar: " + Color.ANSI_RESET);
    float amount = scanner.nextFloat();

    while (amount > user.getBalance()) {

      System.out.println(Color.ANSI_RED + "Error: No tienes suficiente dinero" + Color.ANSI_RESET);
      System.out.println("Tu saldo es " + Color.ANSI_GREEN + user.getBalance() + Color.ANSI_RESET
          + " y quisite apostar " + Color.ANSI_GREEN + amount + Color.ANSI_RESET);
      System.out.println(Color.ANSI_BLUE + "Deposita mas dinero para poder continuar: " + Color.ANSI_RESET);
      float depositAmount = scanner.nextFloat();
      user.addBalance(depositAmount);

      System.out.println(
          Color.ANSI_PURPLE + "Ahora si. Ingrese nuevamente la cantidad que desea apostar: " + Color.ANSI_RESET);
      amount = scanner.nextInt();
    }

    user.removeBalance(amount);
    return amount;
  }

  public static void handleWin(User user, float amount) {
    user.addBalance(amount);
    System.out.println(Color.ANSI_GREEN + "Felicidades, has ganado " + Color.ANSI_GREEN + amount + Color.ANSI_RESET);
  }

  public static void handleLose(User user, float amount) {
    user.removeBalance(amount);
    System.out.println(Color.ANSI_RED + "Lo siento, has perdido " + Color.ANSI_RED + amount + Color.ANSI_RESET);
    makeLoserSentence();
  }

  public static void makeLoserSentence() {
    int randomIndex = new Random().nextInt(loserStrings.length);
    System.out.println(Color.ANSI_RED + loserStrings[randomIndex] + Color.ANSI_RESET);
  }
}
