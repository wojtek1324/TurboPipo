package com.example.administrateur.thompsontp3.Service;

import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Model.Transaction;

/**
 * Created by 1263287 on 2015-05-12.
 */
public class ServicePrixTotal {

    public static Transaction AppliquerRabaisEtTaxes(Transaction pTransaction, RabaisCourant pRabaisCourant)
    {



        return pTransaction;
    }

    public double calculer2Pour1(double pPrixCourant ,Transaction pTransaction, RabaisCourant pRabaisCourant)
    {
        double prix2Pour1 = 0;

        return prix2Pour1;
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


}
