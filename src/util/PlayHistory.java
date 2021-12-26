package src.util;

import java.util.ArrayList;
import java.util.List;
import src.Game;

/**
 * Clase de utilidad que permite tener un historial de juegos junto con los
 * montos perdidos/ganados
 */
public class PlayHistory {

  List<PlayInfo> playHistory;

  public PlayHistory() {
    playHistory = new ArrayList<PlayInfo>();
  }

  /**
   * Agrega una nueva jugada al historial
   * 
   * @param game   nombre del juego
   * @param bet    cantidad apostada
   * @param status resultado de la jugada (se gano, perdio o quedo empate)
   */
  public void addPlay(String game, float bet, Game.WinLoseStatus status) {
    playHistory.add(new PlayInfo(game, bet, status));
  }

  /**
   * Retorna el historial de juegos
   * 
   * @return historial de juegos
   */
  public List<PlayInfo> getPlayHistory() {
    return playHistory;
  }

  /**
   * Limpia el historial de juegos
   */
  public void clearPlayHistory() {
    playHistory.clear();
  }

  /**
   * Imprime en consola el historial de juegos dado un limite
   * 
   * @param limit
   */
  public void printPlayHistory(int limit) {

    if (playHistory.size() == 0) {
      System.out.println("No hay juegos en el historial");
      return;
    }

    if (limit > playHistory.size()) {
      limit = playHistory.size();
    }

    for (int i = playHistory.size() - limit; i < playHistory.size(); i++) {
      PlayInfo play = playHistory.get(i);
      if (play.status == Game.WinLoseStatus.WIN) {
        System.out.println(Color.GREEN + play.game + " - Ganó: " + play.bet + "$" + Color.RESET);
      } else if (play.status == Game.WinLoseStatus.LOSE) {
        System.out.println(Color.RED + play.game + " - Perdió: " + play.bet + "$" + Color.RESET);
      } else {
        System.out.println(Color.YELLOW + play.game + " - Empató." + Color.RESET);
      }
    }
  }

}

/**
 * Clase de utilidad para poder hacer una lista de juegos y sus montos
 */
class PlayInfo {

  String game;
  float bet;
  Game.WinLoseStatus status;

  public PlayInfo(String game, float bet, Game.WinLoseStatus status) {
    this.game = game;
    this.bet = bet;
    this.status = status;
  }

}