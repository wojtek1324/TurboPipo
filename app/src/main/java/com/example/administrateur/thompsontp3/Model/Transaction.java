package com.example.administrateur.thompsontp3.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-05-12.
 */
public class Transaction {

    public List<TransactionItem> Transactions;

    public double PrixApresRabaisEtTaxes;

    public Transaction()
    {
        Transactions = new ArrayList<TransactionItem>();
    }

    public double prixTotalBase()
    {
        double total = 0;
        for(TransactionItem item : Transactions)
        {
            total += item.achatItem.prix * item.quantity;
        }
        return total;
    }




}
