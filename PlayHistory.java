import java.util.ArrayList;
import java.util.List;

public class PlayHistory {
  List<PlayInfo> playHistory;

  public PlayHistory() {
    playHistory = new ArrayList<PlayInfo>();
  }

  public void addPlay(String game, float bet, PlayInfo.Status status) {
    playHistory.add(new PlayInfo(game, bet, status));
  }

  public List<PlayInfo> getPlayHistory() {
    return playHistory;
  }

  public void clearPlayHistory() {
    playHistory.clear();
  }

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
      if (play.status == PlayInfo.Status.WIN) {
        System.out.println(Color.ANSI_GREEN + play.game + " - Ganó: " + play.bet + "$" + Color.ANSI_RESET);
      } else if (play.status == PlayInfo.Status.LOSE) {
        System.out.println(Color.ANSI_RED + play.game + " - Perdió: " + play.bet + "$" + Color.ANSI_RESET);
      } else {
        System.out.println(Color.ANSI_YELLOW + play.game + " - Empató." + Color.ANSI_RESET);
      }
    }
  }

}

class PlayInfo {

  enum Status {
    WIN, LOSE, DRAW
  }

  String game;
  float bet;
  Status status;

  public PlayInfo(String game, float bet, Status status) {
    this.game = game;
    this.bet = bet;
    this.status = status;
  }

}