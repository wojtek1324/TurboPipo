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

    private List<TransactionItem> listPourCalcul;
    private RabaisCourant rabaisCourant;

    public ServicePrixTotal(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {
        this.listPourCalcul = pTransaction.Transactions;
        this.rabaisCourant = pRabaisCourant;
    }

    public static Transaction AppliquerRabaisEtTaxes(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {



        return pTransaction;
    }

    public void calculer2Pour1(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {

        for(AchatItem itemAchat : pRabaisCourant.getList2Pour1())
        {

        }

    }

    public boolean prixAuDelaDuSeuilExemptionTaxes(double prix, RabaisCourant pRabaisCourant)
    {

        return false;
    }


    public double CalculerTaxes(double prixAvantTaxes)
    {
        double prixApresTaxes = 0;


        return prixApresTaxes;
    }

    public void AjouterProduitsGratuits(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {

    }

    private boolean transactionContient(AchatItem item)
    {
        boolean contientAchatItem = false;
        


        return contientAchatItem;
    }


}
