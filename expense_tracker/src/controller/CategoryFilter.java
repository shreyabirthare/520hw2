package controller;
 import java.util.List;
import java.util.ArrayList;
import model.Transaction;
/**
 * Category Filter class which implenents the TransactionFilter
 */
public class CategoryFilter implements TransactionFilter {
    private String categoryToFilter;

    public CategoryFilter(String category) {
        this.categoryToFilter = category;
    }
/**
 * filter function which will filter the transactions based on the category
 */
    @Override
    public List<Integer> filter(List<Transaction> transactions) {
        // Filter transactions based on the categoryToFilter
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