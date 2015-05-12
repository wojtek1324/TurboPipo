package com.example.administrateur.thompsontp3.Model;

/**
 * Created by 1263287 on 2015-04-21.
 */
public class TransactionItem {

    public TransactionItem() {}

    public TransactionItem(AchatItem pAchatItem, Integer pQuantity)
    {
        achatItem = pAchatItem;
        quantity = pQuantity;
    }

    public AchatItem achatItem;

    public Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long id;


}
