package src.games;

import java.util.Random;

import src.Game;
import src.User;
import src.util.Color;

public class Guess {

  private final int MAX_NUMBER = 100;

  public final String NAME = "Adivina Adivinador";

  public Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  private int numberToGuess;
  private int tries = 0;

  private float bet;
  private User user;

  /**
   * Constructor de AdivinaAdivinador. Genera el numero a adivinar.
   * 
   * @param user usuario que juega
   * @param bet  monto apostado
   */
  public Guess(User user, float bet) {
    this.bet = bet;
    this.user = user;
    numberToGuess = new Random().nextInt(MAX_NUMBER) + 1;
  }

  /**
   * Turno de AdivinaAdivinador. Recibe un numero y checa si es mayor, menor o
   * igual al numero que se tiene que adivinar
   * 
   * @param number numero que se recibe por parte del usuario
   */
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
      System.out.println("\n" + Color.GREEN + "Adivinaste en " + tries + " intentos" + Color.RESET);
      finishGame();
    } else if (number > numberToGuess) {
      System.out.println(Color.YELLOW + "El numero es mas pequeño" + Color.RESET);
    } else {
      System.out.println(Color.YELLOW + "El numero es mas grande" + Color.RESET);
    }
  }

  /**
   * Finaliza el juego y checa cuantos turnos se hicieron para adivinar el numero.
   * Se asigna una cantidad dependiendo de ello
   */
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
