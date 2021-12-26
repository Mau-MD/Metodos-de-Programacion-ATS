package src.games;

import java.util.Random;

import src.Game;
import src.User;
import src.util.Color;
import src.util.ConsoleUtil;

public class Connect {

  public Game.GameStatus status = Game.GameStatus.NOT_STARTED;

  public final String NAME = "Conecta 4";

  /**
   * Modos normal (la computadora hace movimientos aleatorios) y dificil (la
   * computadora hace el mejor movimiento)
   */
  public enum ConnectMode {
    NORMAL, HARD
  }

  /**
   * Enum que representa el usuario, la computadora o ninguno.
   * Util para poder saber quien gano o para determinar quien esta haciendo algun
   * movimiento
   */
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

  /**
   * Constructor de Connect4
   * 
   * @param user usuario que juega
   * @param bet  monto apostado
   */
  public Connect(User user, float bet) {
    this.bet = bet;
    this.user = user;
    initBoard();
  }

  /**
   * Contructor de Connect4
   * 
   * @param user usuario que juega
   * @param bet  monto apostado
   */
  public Connect(User user, float bet, ConnectMode mode) {
    this.mode = mode;
    this.bet = bet;
    this.user = user;
    initBoard();
  }

  /**
   * Elige el token de usuario
   * 
   * @param heartToken si el usuario quiere el token de corazones
   */
  public void setToken(boolean heartToken) {
    if (heartToken) {
      userToken = '♥';
      computerToken = '♠';
    } else {
      userToken = '♠';
      computerToken = '♥';
    }
  }

  /**
   * Inicializa el tablero con ' '
   * La computadora realiza su primer movimiento
   * Dibuja el tablero
   */
  public void init() {
    int computerColumn = new Random().nextInt(COLS);
    placePiece(computerColumn, Player.COMPUTER);
    drawBoard();
  }

  /**
   * Pone una pieza en el tablero del usuario y de la computadora.
   * 
   * @param column columna donde se coloca la pieza del usuario
   * 
   */
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

  /**
   * Termina el juego agregando dinero al usuario si es que gano
   * Tambien dibuja el estado final del tablero
   * 
   * @param winner quien gano
   */
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
  /**
   * Pone una pieza en el tablero dado el jugador y la columna
   * 
   * @param column columna donde se quiere poner el token
   * @param player jugador que quiere poner la pieza (usuario o computadora)
   */
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

  /**
   * Dibuja en consola el estado actual del tablero
   */
  public void drawBoard() {
    ConsoleUtil.clearConsole();
    for (int i = 0; i < ROWS; i++) {
      System.out.print("| ");
      for (int j = 0; j < COLS; j++) {
        if (board[i][j] == 'X') {
          System.out.print(Color.GREEN + userToken + Color.RESET);
        } else if (board[i][j] == 'O') {
          System.out.print(Color.RED + computerToken + Color.RESET);
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

  /**
   * Chequea si hay un ganador dentro del estado actual del tablero
   * 
   * @return ganador o NONE
   */
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

  /**
   * Inicializa el tablero con valores vacios (' ')
   */
  private void initBoard() {
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        board[i][j] = ' ';
      }
    }
  }
}
