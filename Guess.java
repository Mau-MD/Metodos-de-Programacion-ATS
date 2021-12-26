import java.util.Random;

public class Guess {

  private final int MAX_NUMBER = 100;

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
      finishGame();
    } else if (number > numberToGuess) {
      System.out.println("El numero es mas pequeño");
    } else {
      System.out.println("El numero es mas grande");
    }
  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;
    if (tries == 1) {
      Game.handleWin(user, bet * 3.0f, bet);
    } else if (tries == 2) {
      Game.handleWin(user, bet * 2.5f, bet);
    } else if (tries == 3) {
      Game.handleWin(user, bet * 2.0f, bet);
    } else if (tries >= 4 && tries <= 6) {
      Game.handleWin(user, bet * 1.5f, bet);
    } else {
      Game.handleLose(user, bet);
    }
  }

}
