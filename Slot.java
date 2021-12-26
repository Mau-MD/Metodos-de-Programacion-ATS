import java.util.Random;

public class Slot {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  public final String NAME = "Tragamonedas";

  private float bet;
  private User user;

  private int[] indexes = new int[3];

  private final String[] words = new String[] {
      "cerezas", "naranjas", "ciruelas", "campanas", "melones", "barras" };

  public Slot(User user, float bet) {
    this.bet = bet;
    this.user = user;
  }

  private final int DELAY_BETWEEN_WORDS_MS = 1000;
  private final int DELAY_BETWEEN_SLOTS_MS = 100;

  public void play() {

    Util.clearConsole();

    Random random = new Random();

    indexes[0] = random.nextInt(words.length);
    indexes[1] = random.nextInt(words.length);
    indexes[2] = random.nextInt(words.length);

    int wordsShown = 0;

    Stopwatch showWordStopwatch = new Stopwatch();
    Stopwatch showRandomSloStopwatch = new Stopwatch();

    showWordStopwatch.start();
    showRandomSloStopwatch.start();

    while (wordsShown <= 3) {

      if (showWordStopwatch.getElapsedTime() > DELAY_BETWEEN_WORDS_MS) {
        wordsShown++;
        showWordStopwatch.reset();
        showWordStopwatch.start();
      }

      if (showRandomSloStopwatch.getElapsedTime() > DELAY_BETWEEN_SLOTS_MS) {
        Util.clearConsole();
        System.out.print("| " + Color.ANSI_BLUE);
        for (int i = 0; i < 3; i++) {
          int index = random.nextInt(words.length);
          // If we have already shown this word, don't show it randomly
          if (i < wordsShown) {
            index = indexes[i];
          }
          System.out.print(words[index] + Color.ANSI_RESET + " | " + Color.ANSI_BLUE);
        }
        showRandomSloStopwatch.reset();
        showRandomSloStopwatch.start();
      }
    }

    finishGame();
  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;

    if (indexes[0] == indexes[1] && indexes[1] == indexes[2]) {
      Game.handleWin(user, bet, 3, NAME);
    } else if (indexes[0] == indexes[1] || indexes[1] == indexes[2]) {
      Game.handleWin(user, bet, 2, NAME);
    } else {
      Game.handleLose(user, bet, NAME);
    }
  }

}
