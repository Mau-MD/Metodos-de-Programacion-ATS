package src.util;

/**
 * Clase de utilidad que permite imprimir en colores en entornos UNIX. Si
 * detecta que el sistema es Windows, no imprime nada, asi evitando strings
 * rotas
 * 
 * Nota: No se ha probado en Windows
 */
public class Color {
  public static final String RESET = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[0m";
  public static final String BLACK = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[30m";
  public static final String RED = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[31m";
  public static final String GREEN = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[32m";
  public static final String YELLOW = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[33m";
  public static final String BLUE = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[34m";
  public static final String PURPLE = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[35m";
  public static final String CYAN = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[36m";
  public static final String WHITE = System.getProperty("os.name").startsWith("Windows") ? "" : "\u001B[37m";
}
