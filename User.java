import java.util.ArrayList;
import java.util.List;

public class User {

  String name;
  float balance;
  List<Float> balanceHistory;

  public User(String name, float balance) {
    balanceHistory = new ArrayList<Float>();
    this.name = name;
    this.balance = balance;
    balanceHistory.add(balance);
  }

  public String getName() {
    return name;
  }

  public float getBalance() {
    return balance;
  }

  public void addBalance(float amount) {
    balance += amount;
    balanceHistory.add(balance);
  }

  public void removeBalance(float amount) {
    balance -= amount;
    balanceHistory.add(balance);
  }

  public List<Float> getBalanceHistory() {
    return balanceHistory;
  }

  public float getLastBalance() {
    return balanceHistory.get(balanceHistory.size() - 1);
  }

  public void clearBalanceHistory() {
    balanceHistory.clear();
  }
}
