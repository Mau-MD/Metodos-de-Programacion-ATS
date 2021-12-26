import java.util.Random;

public class Connect {

  Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  public final String NAME = "Conecta 4";

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

  private char userToken = '♥';
  private char computerToken = '♠';

  private ConnectMode mode = ConnectMode.NORMAL;

  private char[][] board = new char[ROWS][COLS];

  public Connect(User user, float bet) {
    this.bet = bet;
    this.user = user;
    initBoard();
  }

  public Connect(User user, float bet, ConnectMode mode) {
    this.mode = mode;
    this.bet = bet;
    this.user = user;
    initBoard();
  }

  public void setToken(boolean heartToken) {
    if (heartToken) {
      userToken = '♥';
      computerToken = '♠';
    } else {
      userToken = '♠';
      computerToken = '♥';
    }
  }

  public void init() {
    int computerColumn = new Random().nextInt(COLS);
    placePiece(computerColumn, Player.COMPUTER);
    drawBoard();
  }

  public void play(int column) {
    Random random = new Random();

    column -= 1; // Add 1 to column to match the array index

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

    placePiece(column, Player.USER);

    if (status == Game.GameStatus.COMPLETED)
      return;

    int computerColumn = random.nextInt(COLS);
    while (board[0][computerColumn] != ' ') {
      computerColumn = random.nextInt(COLS);
    }

    placePiece(computerColumn, Player.COMPUTER);

    drawBoard();
  }

  public void finishGame(Player winner) {
    // Show final state
    drawBoard();
    status = Game.GameStatus.COMPLETED;
    if (winner == Player.USER) {
      Game.handleWin(user, bet, 2, NAME);
    } else if (winner == Player.COMPUTER) {
      Game.handleLose(user, bet, NAME);
    } else {
      Game.handleDraw(user, NAME);
    }

  }

  // We can have an array storing the number of pieces in each column to avoid the
  // for loop
  private void placePiece(int column, Player player) {
    for (int i = ROWS - 1; i >= 0; i--) {
      if (board[i][column] == ' ') {
        board[i][column] = player == Player.USER ? 'X' : 'O';
        break;
      }
    }
    Player winner = checkWinner();
    if (winner != Player.NONE) {
      finishGame(winner);
    }
  }

  public void drawBoard() {
    Util.clearConsole();
    for (int i = 0; i < ROWS; i++) {
      System.out.print("| ");
      for (int j = 0; j < COLS; j++) {
        if (board[i][j] == 'X') {
          System.out.print(Color.ANSI_GREEN + userToken + Color.ANSI_RESET);
        } else if (board[i][j] == 'O') {
          System.out.print(Color.ANSI_RED + computerToken + Color.ANSI_RESET);
        } else {
          System.out.print(board[i][j]);
        }
        System.out.print(" | ");
      }
      System.out.print('\n');
    }
    System.out.print('\n');

    // Print guide col numbers
    System.out.print("| ");
    for (int i = 0; i < COLS; i++) {
      System.out.print(i + 1);
      System.out.print(" | ");
    }
    System.out.print('\n');

  }

  // We can use a simple for to check if there is 4 pieces in a row horizontally
  // We use arrays to store previous positions. This way we can check for a winner
  // in O(nm) time. This will be in handy when we use the minimax algorithm

  private Player checkWinner() {

    int[] upperPiecesCount = new int[COLS];
    int[] leftDiagonalPiecesCount = new int[COLS];
    int[] rightDiagonalPiecesCount = new int[COLS];

    for (int i = 0; i < COLS; i++) {
      upperPiecesCount[i] = 0;
      leftDiagonalPiecesCount[i] = 0;
      rightDiagonalPiecesCount[i] = 0;
    }

    int consecutivePieces = 1;

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {

        // If the piece is empty, we need to reset everything
        if (board[i][j] == ' ') {
          consecutivePieces = 0;
          upperPiecesCount[j] = 0;
          if (j > 0)
            leftDiagonalPiecesCount[j - 1] = 0;
          if (j < COLS - 1)
            rightDiagonalPiecesCount[j + 1] = 0;
          continue;
        }

        // We already checked if the piece is not empty
        if (i == 0) {
          upperPiecesCount[j] = 1;
          if (j > 0)
            leftDiagonalPiecesCount[j - 1] = 1;
          if (j < COLS - 1)
            rightDiagonalPiecesCount[j + 1] = 1;
        }

        if (j == 0) {
          consecutivePieces = 1;
        }

        // Check horizontal
        if (j > 0 && board[i][j - 1] == board[i][j]) {
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
        if (i > 0 && j > 0) {
          if (board[i - 1][j - 1] == board[i][j]) {
            leftDiagonalPiecesCount[j - 1]++;
          } else {
            leftDiagonalPiecesCount[j - 1] = 1; // Aqui
          }

        }

        // Check right diagonal
        if (i > 0 && j < COLS - 1) {
          if (board[i - 1][j + 1] == board[i][j]) {
            rightDiagonalPiecesCount[j + 1]++;
          } else {
            rightDiagonalPiecesCount[j + 1] = 1; // Aqui
          }

        }

        // Any 4 are consecutives
        if (consecutivePieces == 4 || upperPiecesCount[j] == 4 || (j > 0 && leftDiagonalPiecesCount[j - 1] == 4)
            || (j < COLS - 1 && rightDiagonalPiecesCount[j + 1] == 4)) {
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
