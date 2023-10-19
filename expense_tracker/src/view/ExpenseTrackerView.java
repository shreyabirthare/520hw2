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
    setSize(600, 400); // Make GUI larger

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
      // Clear existing rows
      int rowNum = model.getRowCount();
      //System.out.println(rowNum);
      model.setRowCount(0);
      //System.out.println(model.getRowCount());
      // Get row count
       rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
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

  public String getChosenFilter(){
    String selectedFilterBydd1 = (String) dropdownCat.getSelectedItem();
    String selectedFilterBydd2 = (String) dropdownAmt.getSelectedItem();
    System.out.println(selectedFilterBydd1 + " " + selectedFilterBydd2);
    if (selectedFilterBydd1 != "no selection" && selectedFilterBydd2 != "no selection") {
      JOptionPane.showMessageDialog(this, "Please select only one filter at a time.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        // Continue with your filtering logic here
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

    //     dropdown1.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String selectedFirstOption = (String) firstDropdown.getSelectedItem();
    //             updateSecondDropdown(selectedFirstOption);
    //         }
    //     });

    //     // Create a filter button
    //     JButton filterButton = new JButton("Filter");

    //     // Add an action listener to the filter button
    //     filterButton.addActionListener(new ActionListener() {
    //         @Override
    //         public void actionPerformed(ActionEvent e) {
    //             String selectedFirstOption = (String) firstDropdown.getSelectedItem();
    //             String selectedSecondOption = (String) secondDropdown.getSelectedItem();
    //             controller.filterTransactions(selectedFirstOption, selectedSecondOption);
    //         }
    //     });
    // }

    // public void updateFirstDropdown(List<String> options) {
    //     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(options.toArray(new String[0]));
    //     firstDropdown.setModel(model);
    // }

    // public void updateSecondDropdown(List<String> options) {
    //     DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(options.toArray(new String[0]));
    //     secondDropdown.setModel(model);
    // }





//   public void highlightRows2(List<Transaction> filteredTransactions) {
//     DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//     renderer.setBackground(new Color(173, 255, 168)); // Light green

//     for (Transaction ft : filteredTransactions) {
//         String idOfTrans = ft.getTimestamp();
//         for (int row = 0; row < model.getRowCount(); row++) {
//             Object cellValue = model.getValueAt(row, 3);
//             if (cellValue != null && cellValue.toString().equals(idOfTrans)) {
//                 // Apply the highlighting style to the entire row
//                 for (int col = 0; col < model.getColumnCount(); col++) {
//                     transactionsTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
//                 }
//             }
//         }
//     }

//     // Trigger rendering update for the entire table
//     transactionsTable.repaint();
// }

//   public void highlight2(List<Transaction> filteredTransactions, List<Transaction> all) {
//     // Set the cell renderer for the entire table
//     transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//         @Override
//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

//             // Get the transaction for the current row
//             Transaction t = all.get(table.convertRowIndexToModel(row));

//             // Determine if the transaction should be highlighted
//             boolean shouldHighlight = filteredTransactions.contains(t);

//             if (shouldHighlight) {
//                 c.setBackground(new Color(173, 255, 168)); // Light green
//             } else {
//                 c.setBackground(table.getBackground()); // No background color
//             }

//             return c;
//         }
//     });

//     // Clear existing rows
//     model.setRowCount(0);

//     // Get row count
//     int rowNum = 1; // Initialize with 1 for the header row
//     double totalCost = 0;

//     // Add rows from transactions list
//     for (Transaction t : all) {
//         model.addRow(new Object[]{rowNum, t.getAmount(), t.getCategory(), t.getTimestamp()});
//         totalCost += t.getAmount();
//         rowNum++; // Increment the row number
//     }

//     // Add total row
//     Object[] totalRow = {"Total", null, null, totalCost};
//     model.addRow(totalRow);

//     // Fire table update
//     transactionsTable.updateUI();
// }


// //   public void highlightColourErr(List<Transaction> filteredTransactions, List<Transaction> all) {
// //     // Clear existing rows
// //     model.setRowCount(0);

// //     // Get row count
// //     int rowNum = 1; // Initialize with 1 for the header row
// //     double totalCost = 0;

// //     for (Transaction t : all) {
// //         // Determine if the transaction should be highlighted
// //         boolean shouldHighlight = filteredTransactions.contains(t);

// //         // Add rows to the table model
// //         model.addRow(new Object[]{rowNum, t.getAmount(), t.getCategory(), t.getTimestamp()});
// //         totalCost += t.getAmount();
// //         String add = "NO";
// //         if(shouldHighlight)
// //           add = "Yes";
// //         System.out.println(rowNum + " " + add);
// //         //if (shouldHighlight) {
// //             // Highlight the row
// //             transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
// //                 @Override
// //                 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
// //                     Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
// //                     if (shouldHighlight) {
// //                       c.setBackground(new Color(173, 255, 168)); // Light green
// //                     } else {
// //                       c.setBackground(new Color(4, 255, 168)); // No background color
// //                     }
// //                     return c;
// //                 }
// //             });
// //         // } else {
// //         //     // Remove highlighting for the row
// //         //     transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
// //         // }
        
// //         rowNum++; // Increment the row number
// //     }

// //     // Add total row
// //     Object[] totalRow = {"Total", null, null, totalCost};
// //     model.addRow(totalRow);

// //     // Fire table update
// //     transactionsTable.updateUI();
// // }


  


// // this works - colour proper but indexing error

//   public void highlight(List<Transaction> filteredTransactions, List<Transaction> all) {
//     // Set the cell renderer for the entire table
//     System.out.println("in");
    
//     if(!(filteredTransactions.equals(all))){
//       transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//         @Override
//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

//             // Get the transaction for the current row
//             Transaction t = all.get(row);
//             System.out.println(t.getAmount() + " " + t.getCategory());
//             // Determine if the transaction should be highlighted
//             boolean shouldHighlight = filteredTransactions.contains(t);

//             if (shouldHighlight  ) {
//                 c.setBackground(new Color(173, 255, 168)); // Light green
//             } else {
//                 c.setBackground(table.getBackground()); // No background color
//             }

//             return c;
//         }
//     });
//     }else{
//       transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
//         @Override
//         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//             Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

//             // Get the transaction for the current row
//             Transaction t = all.get(row);
//             System.out.println(t.getAmount() + " " + t.getCategory());
//             // Determine if the transaction should be highlighted
//             //boolean shouldHighlight = filteredTransactions.contains(t);

      
//                 c.setBackground(table.getBackground()); // No background color
            

//             return c;
//         }
//     });
//     }
// System.out.println("out");
//     // Clear existing rows
//     model.setRowCount(0);

//     // Get row count
//     int rowNum = 0;
//     double totalCost = 0;

//     // Calculate total cost
//     for (Transaction t : all) {
//         totalCost += t.getAmount();
//         System.out.println("adding trans");
//         model.addRow(new Object[]{rowNum+=1, t.getAmount(), t.getCategory(), t.getTimestamp()});
//     }

//     //Add total row
//     //rowNum+=1
//    // Object[] totalRow = {"Total", null, null, totalCost};
//    // model.addRow(totalRow);
//   System.out.println("adding trans");
//       // Fire table update
//       transactionsTable.updateUI();
  
// }

// public void highlightRows(List<Transaction> all, String cat) {
//   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//   renderer.setBackground(new Color(173, 255, 168)); // Light green

//   for (int row = 0; row < model.getRowCount(); row++) {
//       Object amountObject = model.getValueAt(row, 1);
//       String category = (String) model.getValueAt(row, 2);
//       System.out.println(category + " " + cat + " " + cat.equalsIgnoreCase(category) + " " + (amountObject != null) + " "+ (category != null));

//       if (amountObject != null && category != null && category.equals(cat)) {
//           double amount = (double) amountObject;
//           System.out.println(amount);

//           for (int col = 0; col < model.getColumnCount(); col++) {
//               transactionsTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
//           }

//           // Trigger rendering update for the entire table
//           transactionsTable.repaint();
//       }
//   }
//   transactionsTable.updateUI();
// }


// public void highlight(List<Transaction> transactions, String categoryToHighlight) {
//   DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//     renderer.setBackground(new Color(173, 255, 168));
//   // Clear existing rows
//   int rowNum = model.getRowCount();
//   model.setRowCount(0);
//   System.out.println(rowNum);
//   // Get row count
//   rowNum = model.getRowCount();
//   System.out.println(rowNum);
//   double totalCost = 0;

//   // Calculate total cost
//   for (Transaction t : transactions) {
//       totalCost += t.getAmount();
//   }

//   // Add rows from transactions list
//   for (Transaction t : transactions) {
//       model.addRow(new Object[]{rowNum += 1, t.getAmount(), t.getCategory(), t.getTimestamp()});
      
//       // Check if the category matches the one you want to highlight
//       if (t.getCategory().equalsIgnoreCase(categoryToHighlight)) {
//         System.out.println("IN");
//           // Highlight the row by setting the background color
//           for (int col = 0; col < model.getColumnCount(); col++) {
//               //System.out.
//               transactionsTable.getColumnModel().getColumn(col).setCellRenderer(renderer);
//           }
//       }
//   }

//   // Add total row
//   Object[] totalRow = {"Total", null, null, totalCost};
//   model.addRow(totalRow);

//   // Fire table update
//   transactionsTable.updateUI();
// }

