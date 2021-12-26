package src.util;

/**
 * Clase de utilidad de stopwatch
 */
public class Stopwatch {
  private long startTime;
  private long stopTime;
  private boolean running;

  public Stopwatch() {
    startTime = 0;
    stopTime = 0;
    running = false;
  }

  /**
   * Incia el stopwatch
   */
  public void start() {
    this.startTime = System.currentTimeMillis();
    this.running = true;
  }

  /**
   * Para el stopwatch
   */
  public void stop() {
    this.stopTime = System.currentTimeMillis();
    this.running = false;
  }

  /**
   * Reinicia el stopwatch
   */
  public void reset() {
    this.startTime = 0;
    this.stopTime = 0;
    this.running = false;
  }

  /**
   * Regresa el tiempo transcurrido en milisegundos
   * 
   * @return tiempo transcurrido en milisegundos
   */
  public long getElapsedTime() {
    if (running) {
      return (System.currentTimeMillis() - startTime);
    } else {
      return (stopTime - startTime);
    }
  }

  /**
   * Regresa si el stopwatch esta corriendo
   * 
   * @return true si el stopwatch esta corriendo
   */
  public boolean isRunning() {
    return running;
  }
}
