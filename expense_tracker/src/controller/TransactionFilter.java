package controller;
import java.util.List;

import model.Transaction;

public interface TransactionFilter {
    List<Integer> filter(List<Transaction> transactions);
}

