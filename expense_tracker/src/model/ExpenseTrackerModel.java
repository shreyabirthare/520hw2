package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseTrackerModel {

  
  /**
   * Encapsulation of transactions list
   */
  private List<Transaction> transactions;

  public ExpenseTrackerModel() {
    this.transactions = new ArrayList<>(); 
  }

  public void addTransaction(Transaction t) {
    this.transactions.add(t);
  }

  public void removeTransaction(Transaction t) {
    this.transactions.remove(t);
  }

  /**
   * immutability implemented
   * @return
   */
  public List<Transaction> getTransactions() {
    return Collections.unmodifiableList(transactions);
  }

}

