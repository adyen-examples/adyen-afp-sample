package com.adyen.util;

import com.adyen.model.TransactionItem;
import com.adyen.model.transfers.Amount;
import com.adyen.model.transfers.Transaction;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to work with the Transaction class
 */
@Service
public class TransactionHandler {

    /**
     * Create a list of TransactionItem from the given list of Transaction
     * @param transactions
     * @return
     */
    public List<TransactionItem> getTransactionItems(List<Transaction> transactions) {
        List<TransactionItem> transactionItems = new ArrayList<>();

        for(Transaction transaction : transactions) {
            transactionItems.add(getTransactionItem(transaction));
        }

        return transactionItems;
    }

    /**
     * Create a TransactionItem from the given Transaction
     * @param transaction
     * @return
     */
    public TransactionItem getTransactionItem(Transaction transaction) {
        return new TransactionItem()
                .id(transaction.getId())
                .status(transaction.getStatus().getValue())
                .type(transaction.getAmount().getValue() > 0 ? "Incoming" : "Outgoing")
                .created(formatDate(transaction.getCreationDate()))
                .amount(formatAmount(transaction.getAmount()))
                .pspReference(transaction.getEventId());
    }

    private String formatAmount(Amount amount) {
        String ret = "";

        if(amount != null) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String formattedAmount = numberFormat.format(amount.getValue());
            ret = amount.getCurrency() + " " + formattedAmount;
        }
        return ret;
    }

    private String formatDate(OffsetDateTime offsetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return offsetDateTime.format(formatter);
    }
}
