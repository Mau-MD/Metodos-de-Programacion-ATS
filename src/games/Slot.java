package src.games;

import java.util.Random;

import src.util.Stopwatch;
import src.util.Color;
import src.util.ConsoleUtil;
import src.Game;
import src.User;

public class Slot {

  public final String NAME = "Tragamonedas";

  public Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  private float bet;
  private User user;

  private int[] indexes = new int[3];

  private final String[] words = new String[] {
      "cerezas", "naranjas", "ciruelas", "campanas", "melones", "barras" };

  /**
   * Constructor de Tragamonedas
   * 
   * @param user usuario que juega
   * @param bet  monto apostado
   */
  public Slot(User user, float bet) {
    this.bet = bet;
    this.user = user;
  }

  private final int DELAY_BETWEEN_WORDS_MS = 1000;
  private final int DELAY_BETWEEN_SLOTS_MS = 100;

  /**
   * Turno de Tragamonedas. Selecciona 3 palabras aleatorias y las muestra en
   * pantalla
   */
  public void play() {

    ConsoleUtil.clearConsole();

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
        ConsoleUtil.clearConsole();
        System.out.print("| " + Color.BLUE);
        for (int i = 0; i < 3; i++) {
          int index = random.nextInt(words.length);
          // If we have already shown this word, don't show it randomly
          if (i < wordsShown) {
            index = indexes[i];
          }
          System.out.print(words[index] + Color.RESET + " | " + Color.BLUE);
        }
        showRandomSloStopwatch.reset();
        showRandomSloStopwatch.start();
      }
    }

    finishGame();
  }

  /**
   * Finaliza el juego y checa cuantas palabras coincidieron. Dependiendo de ello
   * el usuario gana cierta cantiad de dinero
   */
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
