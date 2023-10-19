package controller;
import java.util.List;

import model.Transaction;
/** 
 * Transaction Filter interface 
 */
public interface TransactionFilter {
    List<Integer> filter(List<Transaction> transactions);
}

