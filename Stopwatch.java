public class Stopwatch {
  private long startTime;
  private long stopTime;
  private boolean running;

  public Stopwatch() {
    startTime = 0;
    stopTime = 0;
    running = false;
  }

  public void start() {
    this.startTime = System.currentTimeMillis();
    this.running = true;
  }

  public void stop() {
    this.stopTime = System.currentTimeMillis();
    this.running = false;
  }

  public void reset() {
    this.startTime = 0;
    this.stopTime = 0;
    this.running = false;
  }

  public long getElapsedTime() {
    if (running) {
      return (System.currentTimeMillis() - startTime);
    } else {
      return (stopTime - startTime);
    }
  }

  public boolean isRunning() {
    return running;
  }
}
