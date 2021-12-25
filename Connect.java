import java.util.Random;

// TODO: change matrix to binary

public class Connect {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  public enum ConnectMode {
    NORMAL, HARD
  }

  private enum Player {
    USER, COMPUTER, NONE
  }

  private final int ROWS = 7;
  private final int COLS = 7;

  private float bet;
  private User user;

  private ConnectMode mode = ConnectMode.NORMAL;

  private char[][] board = new char[ROWS][COLS];

  public Connect(User user, float bet) {
    this.bet = bet;
    this.user = user;
    initBoard();
  }

  public Connect(User user, float bet, ConnectMode mode) {
    this.bet = bet;
    this.user = user;
    this.mode = mode;
    initBoard();
  }

  public void play(int column) {
    Random random = new Random();

    if (column > COLS || column < 0) {
      System.out.println("Columna invalida");
      return;
    }

    if (status == Game.GameStatus.NOT_STARTED) {
      status = Game.GameStatus.PLAYING;
    }

    if (status == Game.GameStatus.COMPLETED) {
      System.out.println("El juego ya ha terminado");
      return;
    }

    if (board[0][column] != ' ') {
      System.out.println("Columna llena");
      return;
    }

  }

  public void finishGame() {
    status = Game.GameStatus.COMPLETED;

  }

  // We can have an array storing the number of pieces in each column to avoid the
  // for loop
  private void drawPiece(int column, Player player) {
    for (int i = ROWS - 1; i >= 0; i--) {
      if (board[i][column] == ' ') {
        board[i][column] = player == Player.USER ? 'X' : 'O';
        break;
      }
    }
  }

  // We can use a simple for to check if there is 4 pieces in a row horizontally
  // We use arrays to store previous positions. This way we can check for a winner
  // in O(n) time. This will be in handy when we use the minimax algorithm

  private Player checkWinner() {

    int[] upperPiecesCount = new int[COLS];
    int[] leftDiagonalPiecesCount = new int[COLS];
    int[] rightDiagonalPiecesCount = new int[COLS];

    for (int i = 0; i < COLS; i++) {
      upperPiecesCount[i] = 1;
      leftDiagonalPiecesCount[i] = 1;
      rightDiagonalPiecesCount[i] = 1;
    }

    int consecutivePieces = 1;

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        if (i == 0 && j == 0)
          continue;

        // Check horizontal
        if (board[i][j - 1] == board[i][j]) {
          consecutivePieces++;
        } else {
          consecutivePieces = 1;
        }

        // Check vertical
        if (i > 0 && board[i - 1][j] == board[i][j]) {
          upperPiecesCount[j]++;
        } else {
          upperPiecesCount[j] = 1;
        }

        // Check left diagonal
        if (i > 0 && j > 0 && board[i - 1][j - 1] == board[i][j]) {
          leftDiagonalPiecesCount[j - 1]++;
        } else {
          leftDiagonalPiecesCount[j - 1] = 1;
        }

        // Check right diagonal
        if (i > 0 && j < COLS - 1 && board[i - 1][j + 1] == board[i][j]) {
          rightDiagonalPiecesCount[j + 1]++;
        } else {
          rightDiagonalPiecesCount[j + 1] = 1;
        }

        // Any 4 are consecutives
        if (consecutivePieces == 4 || upperPiecesCount[j] == 4 || leftDiagonalPiecesCount[j - 1] == 4
            || rightDiagonalPiecesCount[j + 1] == 4) {
          return board[i][j] == 'X' ? Player.USER : Player.COMPUTER;
        }
      }
    }

    return Player.NONE;
  }

  private void initBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        board[i][j] = ' ';
      }
    }
  }
}
