package view;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.InputValidation;

import java.awt.*;
import java.text.NumberFormat;

import model.ExpenseTrackerModel;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JButton resetBtn;
  private JButton filterButton;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;
  private JComboBox<String> dropdownCat; 
  private JComboBox<String> dropdownAmt; 

  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(1000, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");
    resetBtn = new JButton("Reset");
    filterButton = new JButton("Filter");

    String[] filterByCategories = {"no selection","food", "travel", "bills", "entertainment", "other"};
    dropdownCat = new JComboBox<>(filterByCategories);  

    String[] filterByAmt = {"no selection", "Greater than 0 & less than 400", "Greater than 401 & less than 800", "Greater than 801 & less than 1000"};
    dropdownAmt = new JComboBox<>(filterByAmt);
    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    inputPanel.add(resetBtn);
    inputPanel.add(filterButton);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
    buttonPanel.add(resetBtn);
    buttonPanel.add(dropdownCat);
    buttonPanel.add(dropdownAmt);
    buttonPanel.add(filterButton);
    //buttonPanel.add(selectedItemLabel);
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  
  }

/**
 * to remove the filtering 
 * @param all
 */
    public void reset(List<Transaction> all){
   // System.out.println("Here in reset");
    for(Transaction ft: all){
      
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          
          
              c.setBackground(table.getBackground());
          return c;
      }
    });
    }
    transactionsTable.updateUI();
  }
/**
 * Highlight the filtered rows
 * @param rowsToColour
 */
  public void highlightRows(List<Integer> rowsToColour){
   // System.out.println("Here");
      transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
          Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
          //System.out.println(row);
          if (rowsToColour.contains(row)) {
              c.setBackground(new Color(173, 255, 168)); // Light green
          } else {
              c.setBackground(table.getBackground());
          }
          return c;
      }
    });
  }







  public void refreshTable(List<Transaction> transactions) {

      int rowNum = model.getRowCount();
      //System.out.println(rowNum);
      model.setRowCount(0);
      //System.out.println(model.getRowCount());

       rowNum = model.getRowCount();
      double totalCost=0;
 
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public JButton getRestBtn() {
    return resetBtn;
  }
  /**
   * apply the filter
   * @return filter button
   */
  public JButton applyFilter() {
    return filterButton;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }
/**
 * get the filtering strategy based on the selected filter
 * @return one of the strategies
 */
  public String getChosenFilter(){
    String selectedFilterBydd1 = (String) dropdownCat.getSelectedItem();
    String selectedFilterBydd2 = (String) dropdownAmt.getSelectedItem();
    System.out.println(selectedFilterBydd1 + " " + selectedFilterBydd2);
    if (selectedFilterBydd1 != "no selection" && selectedFilterBydd2 != "no selection") {
      JOptionPane.showMessageDialog(this, "Please select only one filter at a time.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        if (selectedFilterBydd1 != "no selection") {
            return selectedFilterBydd1;
        } else if (selectedFilterBydd2 != "no selection") {
            return selectedFilterBydd2;
        } 
            JOptionPane.showMessageDialog(this, "Please select a filter!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    return "error";
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
}
