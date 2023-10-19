package controller;
 import java.util.List;
import java.util.ArrayList;
import model.Transaction;

public class AmountFilter implements TransactionFilter {
    private double minimumAmount;
    private double maximumAmount;

    public AmountFilter(double minimumAmount, double maximumAmount) {
        this.minimumAmount = minimumAmount;
        this.maximumAmount = maximumAmount;
    }

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        // Filter transactions based on the minimum and maximum amount
        List<Integer> filteredTransactions = new ArrayList<>();
        int iter = 0;
        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount >= minimumAmount && amount <= maximumAmount) {
                filteredTransactions.add(iter);
            }
            iter += 1;
        }
        return filteredTransactions;
    }
}