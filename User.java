public class User {

  String name;
  float balance;

  public User(String name, float balance) {
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
