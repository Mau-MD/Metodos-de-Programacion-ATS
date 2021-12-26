import java.util.Random;

public class Guess {

  private final int MAX_NUMBER = 100;

  public final String NAME = "Adivina Adivinador";

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  private int numberToGuess;
  private int tries = 0;

  private float bet;
  private User user;

  public Guess(User user, float bet) {
    this.bet = bet;
    this.user = user;
    numberToGuess = new Random().nextInt(MAX_NUMBER) + 1;
  }

  public void play(int number) {

    if (status == Game.GameStatus.NOT_STARTED) {
      status = Game.GameStatus.PLAYING;
    }

    if (status == Game.GameStatus.COMPLETED) {
      System.out.println("El juego ya ha terminado");
      return;
    }

    tries++;

    if (number == numberToGuess) {
      // Print tries
      System.out.println("\n" + Color.ANSI_GREEN + "Adivinaste en " + tries + " intentos" + Color.ANSI_RESET);
      finishGame();
    } else if (number > numberToGuess) {
      System.out.println(Color.ANSI_YELLOW + "El numero es mas pequeño" + Color.ANSI_RESET);
    } else {
      System.out.println(Color.ANSI_YELLOW + "El numero es mas grande" + Color.ANSI_RESET);
    }
  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;
    if (tries == 1) {
      Game.handleWin(user, bet, 3, NAME);
    } else if (tries == 2) {
      Game.handleWin(user, bet, 2.5f, NAME);
    } else if (tries == 3) {
      Game.handleWin(user, bet, 2, NAME);
    } else if (tries >= 4 && tries <= 6) {
      Game.handleWin(user, bet, 1.5f, NAME);
    } else {
      Game.handleLose(user, bet, NAME);
    }
  }

}
