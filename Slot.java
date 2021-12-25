import java.util.Random;

public class Slot {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  private float bet;
  private User user;

  private int index1;
  private int index2;
  private int index3;

  private final String[] words = new String[] {
      "cerezas", "naranjas", "ciruelas", "campanas", "melones", "barras" };

  public Slot(User user, float bet) {
    this.bet = bet;
    this.user = user;
  }

  public void play() {
    Random random = new Random();

    index1 = random.nextInt(words.length);
    index2 = random.nextInt(words.length);
    index3 = random.nextInt(words.length);

    // Show words
    System.out.println(Color.ANSI_CYAN + words[index1] + " " + words[index2] + " " + words[index3] + Color.ANSI_RESET);

    finishGame();
  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;

    if (index1 == index2 && index2 == index3) {
      Game.handleWin(user, bet * 3.0f);
    } else if (index1 == index2 || index2 == index3) {
      Game.handleWin(user, bet * 2.0f);
    } else {
      Game.handleLose(user, bet);
    }
  }

}
