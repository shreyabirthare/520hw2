package controller;
 import java.util.List;
import java.util.ArrayList;
import model.Transaction;

public class CategoryFilter implements TransactionFilter {
    private String categoryToFilter;

    public CategoryFilter(String category) {
        this.categoryToFilter = category;
    }

    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        // Filter transactions based on the categoryToFilter
        // You can reuse your category validation logic here
        List<Integer> filteredTransactions = new ArrayList<>();
        int iter = 0;
        for (Transaction transaction : transactions) {
            //System.out.println(transaction.getTimestamp());
            if (transaction.getCategory().equals(categoryToFilter)) {
                filteredTransactions.add(iter);
            }
            iter += 1;
        }
        return filteredTransactions;
    }
}