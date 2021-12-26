import java.util.Random;

public class Blackjack {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  public final String NAME = "Blackjack";

  private float bet;
  private User user;

  private int computerPoints = 0;
  private int userPoints = 0;

  /**
   * Constructor de Blackjack
   * 
   * @param user Usuario que juega
   * @param bet  Monto apostado
   */
  public Blackjack(User user, float bet) {
    this.bet = bet;
    this.user = user;
  }

  /**
   * Juega un turno de blackjack. Tira dados tanto para el usuario como para la
   * computadora.
   */
  public void play() {

    Util.clearConsole();

    if (status == Game.GameStatus.NOT_STARTED) {
      status = Game.GameStatus.PLAYING;
    }

    if (status == Game.GameStatus.COMPLETED) {
      System.out.println("El juego ya ha terminado");
      return;
    }

    computerPoints += throwDices();
    userPoints += throwDices();
    System.out.println("\n" + Color.CYAN + "Tu puntaje: " + userPoints + Color.RESET);
  }

  /**
   * Finaliza el juego y checa quien es el ganador. Agrega/Quita puntos
   * dependiendo de quien gano.
   */
  public void finishGame() {
    status = Game.GameStatus.COMPLETED;
    // Show computer points
    System.out.println("\n" + Color.RED + "Puntaje de la computadora: " + computerPoints + Color.RESET);
    if (userPoints > 21) {
      System.out.println("\n" + Color.RED + "Te pasaste de 21" + computerPoints + Color.RESET);
      Game.handleLose(user, bet, NAME);
    } else if (computerPoints > 21) {
      Game.handleWin(user, bet, 2, NAME);
    } else if (userPoints > computerPoints) {
      Game.handleWin(user, bet, 2, NAME);
    } else if (userPoints < computerPoints) {
      Game.handleLose(user, bet, NAME);
    } else {
      Game.handleDraw(user, NAME);
    }
  }

  /**
   * Tira dos dados y devuelve la suma de ambos
   * 
   * @return suma de los dados
   */
  private int throwDices() {
    Random random = new Random();
    int dice1 = random.nextInt(6) + 1;
    int dice2 = random.nextInt(6) + 1;
    return dice1 + dice2;
  }
}
