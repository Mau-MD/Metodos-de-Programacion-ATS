package src;

import src.util.PlayHistory;

/**
 * Clase que contiene la informacion del usuario
 */
public class User {

  String name;
  float balance;
  PlayHistory playHistory;

  /**
   * Constructor de la clase
   * 
   * @param name    nombre del usuario
   * @param balance balance inicial
   */
  public User(String name, float balance) {
    playHistory = new PlayHistory();
    this.name = name;
    this.balance = balance;
  }

  /**
   * Regresa el nombre del usuario
   * 
   * @return nombre del usuario
   */
  public String getName() {
    return name;
  }

  /**
   * Regresa el balance del usuario
   * 
   * @return
   */
  public float getBalance() {
    return balance;
  }

  /**
   * Agrega una cantidad al balance del usuario
   * 
   * @param amount
   */
  public void addBalance(float amount) {
    balance += amount;
  }

  /**
   * Resta una cantidad al balance del usuario
   * 
   * @param amount
   */
  public void removeBalance(float amount) {
    balance -= amount;
  }

}
