package com.example.administrateur.thompsontp3.Service;


import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.TransactionItem;

import java.util.List;

public class ServiceProduit {

    CRUD<AchatItem> listItems;

    public ServiceProduit (CRUD<AchatItem> l) {
        this.listItems = l;
    }

    public static class SameBarcodeException extends  Exception {}

    public static class BadBarcode extends Exception {}

    public void scan(List<TransactionItem> items, String scanResult)
    {
        if(items == null) { throw new NullPointerException(); }

        for(AchatItem item : listItems.getAll()) {
            if(scanResult.equals(item.codeBarre))
            {
                for(TransactionItem transacItem: items)
                {
                    if(transacItem.achatItem.produit.equals(item.produit))
                    {
                        return;
                    }
                }
                TransactionItem transa = new TransactionItem();
                transa.achatItem = item;
                transa.quantity = 1;
                items.add(transa);
            }
        }
    }

    public  AchatItem getByBarcode(String barcode) throws BadBarcode{
        for (AchatItem item : listItems.getAll()){
            if(item.codeBarre.equals(barcode))
            {
                return item;
            }
        }
        throw new BadBarcode();
    }

    public void AjouterProduit(AchatItem item) throws SameBarcodeException
    {
        String codeBarre = item.codeBarre;
        // if exists throw exception
        try{
            this.getByBarcode(codeBarre);
            throw new SameBarcodeException();
        }
        catch(BadBarcode bb){
            // tout va bien
        }

        if(item.prix < 0.0) { throw new IllegalArgumentException(); }
        if(item.produit.equals("")) {throw new IllegalArgumentException(); }

        listItems.save(item);
    }

}
