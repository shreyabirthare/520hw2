# hw1- Manual Review

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTracker
```

You should be able to view the GUI of the project upon successful compilation. 

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.

## Updates
Below changes are made: Added encapsulation and immutability in ExpenseTrackerModel.java and Transactions.java to ensure data once set cant be modified 

## Functionality Update

Added filtering for amount and category. 
Implemented the filter feature by attribute type amount and category. This allows the encapsulation of each filter algorithm into reusable classes. New filters can be easily added by implementing new strategies. 
Users can filter by either “amount“ or “category“ at a time. Added an error message for this.
Created a TransactionFilter Interface, added two strategies CategoryFilter and AmountFilter, the applyFilter() method in controller will call one of the strategies and highlight appropriately by calling the highlightRows function in view. 
Two dropdowns added in button panel to allow users to appropriately select the filter but wont allow them to choose both. 
Reset function to get rid of any filtering applied i.e., reset the background colour. 