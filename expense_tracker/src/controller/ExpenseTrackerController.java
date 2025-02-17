package controller;

import view.ExpenseTrackerView;

import java.util.List;
import java.util.stream.Collectors;

import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    view.reset(model.getTransactions());
    return true;
  }
  /**
   * applies filter based on the strategy
   * strategy decided based on the dropdown.
   * @param filterT
   */
  public void applyFilter(TransactionFilter filterT) {
    List<Transaction> transactions =  model.getTransactions();
    List<Integer> filteredTransactions = filterT.filter(transactions);

    

    view.highlightRows(filteredTransactions);
    refresh();
}
  }