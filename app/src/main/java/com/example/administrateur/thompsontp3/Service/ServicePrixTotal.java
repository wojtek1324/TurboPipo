package com.example.administrateur.thompsontp3.Service;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Model.Transaction;
import com.example.administrateur.thompsontp3.Model.TransactionItem;

import java.util.List;

/**
 * Created by 1263287 on 2015-05-12.
 */
public class ServicePrixTotal {

    public List<TransactionItem> listPourCalcul;
    private RabaisCourant rabaisCourant;
    public TransactionItem produitsGratuit;

    public ServicePrixTotal(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {
        this.listPourCalcul = pTransaction.Transactions;
        this.rabaisCourant = pRabaisCourant;
    }

    public Transaction AppliquerRabaisEtTaxes(Transaction pTransaction)
    {
        calculer2Pour1(rabaisCourant);
        double prixAvantTaxes = 0;
        for(TransactionItem itemTransaction : listPourCalcul)
        {
            prixAvantTaxes += itemTransaction.quantity * itemTransaction.achatItem.prix;
        }
        double prixApresTaxes = prixAvantTaxes;
        if(!prixAuDelaDuSeuilExemptionTaxes(prixAvantTaxes, rabaisCourant))
        {
            prixApresTaxes = CalculerTaxes();
        }
        AjouterProduitsGratuits(prixApresTaxes);

        prixApresTaxes = correctionDeDecimals(prixApresTaxes);

        pTransaction.Transactions.add(produitsGratuit);
        pTransaction.PrixApresRabaisEtTaxes = prixApresTaxes;

        return pTransaction;
    }

    public void calculer2Pour1(RabaisCourant pRabaisCourant)
    {

        for(AchatItem itemAchat : pRabaisCourant.getList2Pour1())
        {
            TransactionItem transactionItem = transactionContient(itemAchat);
            int index = listPourCalcul.indexOf(transactionItem);
            if(index != -1)
            {
                int quantitee = transactionItem.quantity;
                if(transactionItem != null)
                {
                    if(quantitee % 2 == 1)
                    {
                        quantitee /= 2;
                        quantitee++;
                    } else {
                        quantitee /= 2;
                    }
                    listPourCalcul.get(index).quantity = quantitee;
                }
            }
        }

    }

    public boolean prixAuDelaDuSeuilExemptionTaxes(double prix, RabaisCourant pRabaisCourant)
    {
        if(pRabaisCourant.getSeuilPasDeTaxes() == -1) { return false; }
        if(prix > pRabaisCourant.getSeuilPasDeTaxes())
        {
            return true;
        }
        return false;
    }


    public double CalculerTaxes()
    {
        double prixApresTaxes = 0;

        for(TransactionItem transacItem : listPourCalcul)
        {
            if(transacItem.achatItem.taxable) { prixApresTaxes += (transacItem.quantity * transacItem.achatItem.prix * 1.15); }
            else { prixApresTaxes += (transacItem.quantity * transacItem.achatItem.prix); }
        }

        return prixApresTaxes;
    }

    public void AjouterProduitsGratuits(double prix)
    {
        double tranches = rabaisCourant.getTranchesProduitGratuit();
        if(tranches == -1) { return; }

        AchatItem itemGratuit = rabaisCourant.getProduitGratuit();
        int nbProduitsGratuis = (int)(prix / tranches);

        TransactionItem itemTransaction = new TransactionItem();
        itemTransaction.achatItem = itemGratuit;
        itemTransaction.quantity = nbProduitsGratuis;

        produitsGratuit = itemTransaction;
    }

    private TransactionItem transactionContient(AchatItem item)
    {

        if(item == null) { return null; }

        for(TransactionItem itemTransac : listPourCalcul)
        {
            if(itemTransac.achatItem.equals(item))
            {
                return itemTransac;
            }
        }

        return null;
    }

    private Double correctionDeDecimals(Double doubleAajuster)
    {
        double retour = doubleAajuster;
        retour = retour * 100;
        retour = Math.round(retour);
        retour = retour /100;

        return retour;

    }


}
