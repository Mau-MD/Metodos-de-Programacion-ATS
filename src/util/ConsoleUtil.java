package src.util;

/**
 * Clase de utilidad con metodos de consola
 */
public class ConsoleUtil {

  /**
   * Limpia la consola
   */
  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  /**
   * Imprime en consola una linea que pide al usuario que presione enter para
   * continuar
   */
  public static void pressAnyKeyToContinue() {
    System.out.println("\nPresiona [enter] para continuar...");
    try {
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
