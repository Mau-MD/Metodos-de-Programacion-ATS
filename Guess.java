import java.util.Random;
import java.util.Scanner;

public class Guess extends GameLogic {

  private static final int upperBound = 30;

  @Override
  public void play(User user, float amount) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Dentro de este juego tendras que adivinar un numero entre 1 y " + upperBound);
    int answer = new Random().nextInt(upperBound) + 1;

    boolean isGuessed = false;
    int attempts = 0;

    while (!isGuessed) {

      Util.clearConsole();

      System.out.println(Color.ANSI_CYAN + "Ingresa tu numero: " + Color.ANSI_RESET);
      int num = scanner.nextInt();

      attempts++;

      if (num == answer) {
        isGuessed = true;
      } else if (num > answer) {
        System.out.println(Color.ANSI_YELLOW + "El numero que ingresaste es mayor que el numero que estaba pensando"
            + Color.ANSI_RESET);
      } else {
        System.out.println(Color.ANSI_YELLOW + "El numero que ingresaste es menor que el numero que estaba pensando"
            + Color.ANSI_RESET);
      }
    }

    System.out.println("Felicidades, adivinaste el numero en " + attempts + " intentos");

    if (attempts == 1)
      Game.handleWin(user, amount * 3.0f);
    else if (attempts == 2)
      Game.handleWin(user, amount * 2.5f);
    else if (attempts == 3)
      Game.handleWin(user, amount * 2.0f);
    else if (attempts >= 4 && attempts <= 6)
      Game.handleWin(user, amount * 1.5f);
    else
      Game.handleLose(user, amount);

    Util.clearConsole();
  }
}
