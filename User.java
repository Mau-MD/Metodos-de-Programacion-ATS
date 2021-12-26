import java.util.ArrayList;
import java.util.List;

public class User {

  String name;
  float balance;
  PlayHistory playHistory;

  public User(String name, float balance) {
    playHistory = new PlayHistory();
    this.name = name;
    this.balance = balance;
  }

  public String getName() {
    return name;
  }

  public float getBalance() {
    return balance;
  }

  public void addBalance(float amount) {
    balance += amount;
  }

  public void removeBalance(float amount) {
    balance -= amount;
  }

}
