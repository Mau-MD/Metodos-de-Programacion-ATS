public class Util {

  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void pressAnyKeyToContinue() {
    System.out.println("\nPresiona cualquier tecla para continuar...");
    try {
      System.in.read();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
