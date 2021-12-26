import java.util.Random;

public class Blackjack {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  private float bet;
  private User user;

  private int computerPoints = 0;
  private int userPoints = 0;

  public Blackjack(User user, float bet) {
    this.bet = bet;
    this.user = user;
  }

  public void play() {

    if (status == Game.GameStatus.NOT_STARTED) {
      status = Game.GameStatus.PLAYING;
    }

    if (status == Game.GameStatus.COMPLETED) {
      System.out.println("El juego ya ha terminado");
      return;
    }

    computerPoints += throwDices();
    userPoints += throwDices();
    System.out.println("\n" + Color.ANSI_CYAN + "Tu puntaje: " + userPoints + Color.ANSI_RESET);
  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;
    // Show computer points
    System.out.println("\n" + Color.ANSI_RED + "Puntaje de la computadora: " + computerPoints + Color.ANSI_RESET);
    if (userPoints > 21) {
      System.out.println("\n" + Color.ANSI_RED + "Te pasaste de 21" + computerPoints + Color.ANSI_RESET);
      Game.handleLose(user, bet);
    } else if (computerPoints > 21) {
      Game.handleWin(user, bet * 2.0f, bet);
    } else if (userPoints > computerPoints) {
      Game.handleWin(user, bet * 2.0f, bet);
    } else if (userPoints < computerPoints) {
      Game.handleLose(user, bet);
    } else {
      Game.handleDraw();
    }
  }

  private int throwDices() {
    Random random = new Random();
    int dice1 = random.nextInt(6) + 1;
    int dice2 = random.nextInt(6) + 1;
    return dice1 + dice2;
  }
}
