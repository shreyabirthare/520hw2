import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controller.AmountFilter;
import controller.CategoryFilter;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;
import controller.InputValidation;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });
/**
 * reset button to remove filtering 
 */
    view.getRestBtn().addActionListener(e -> {
              List<Transaction> all =  model.getTransactions();
        view.reset(all);
        controller.refresh();
    });
/**
 * listen to actions on filter button 
 */
    view.applyFilter().addActionListener(e -> {
      // Get transaction data from view
      List<Transaction> all =  model.getTransactions();
      view.reset(all);
      String choosen = view.getChosenFilter();
      boolean added = true;
      String categories = "food travel bills entertainment other";
      if(categories.contains(choosen)){

        CategoryFilter cf = new CategoryFilter(choosen);
        controller.applyFilter(cf);
      }else{
        String[] filterByAmt = {"Greater than 0 & less than 400", "Greater than 401 & less than 800", "Greater than 801 & less than 1000"};
        int minimum = 0;
        int maximum = 0;
        if(choosen == filterByAmt[0]){
          minimum = 0;
          maximum = 400;
        }else if(choosen == filterByAmt[1]){
          minimum = 400;
          maximum = 800;
        }else{
          minimum =800;
          maximum = 1000;
        }
        
        AmountFilter af = new AmountFilter(minimum, maximum);
        controller.applyFilter(af);
      }

 
      
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Cannot be filtered");
        view.toFront();
      }
    });

  }

}