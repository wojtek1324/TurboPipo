package com.example.administrateur.thompsontp3.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-05-12.
 */


public class RabaisCourant {

    public static class ItemEstDejaEn2Pour1 extends Exception {}

    public static  class ItemPasDansLaListe extends Exception {}

    int seuilPasDeTaxes;

    private List<AchatItem> list2Pour1;

    private AchatItem produitGratuit;

    private double TranchesProduitGratuit;

    public RabaisCourant()
    {
        list2Pour1 = new ArrayList<AchatItem>();
    }


    public List<AchatItem> getList2Pour1() {
        return list2Pour1;
    }

    public void Ajouter2Pour1(AchatItem item) throws  ItemEstDejaEn2Pour1
    {
        if(list2Pour1.contains(item)) { throw new ItemEstDejaEn2Pour1(); }

        if(item == null) { throw new IllegalArgumentException(); }

        list2Pour1.add(item);
    }

    public void Supprimer2pour1(AchatItem item) throws ItemPasDansLaListe
    {
        if(list2Pour1.contains(item))
        {
            list2Pour1.remove(item);
            return;
        }
        if(item == null) { throw new IllegalArgumentException(); }

        throw new ItemPasDansLaListe();
    }


    public int getSeuilPasDeTaxes() {
        return seuilPasDeTaxes;
    }

    public void setSeuilPasDeTaxes(int pseuilPasDeTaxes) {
        if(pseuilPasDeTaxes <= 0) { throw new IllegalArgumentException(); }
        this.seuilPasDeTaxes = pseuilPasDeTaxes;
    }


    public AchatItem getProduitGratuit() {
        return produitGratuit;
    }

    public void setProduitGratuit(AchatItem produitGratuit) {
        this.produitGratuit = produitGratuit;
    }

    public double getTranchesProduitGratuit() {
        return TranchesProduitGratuit;
    }

    public void setTranchesProduitGratuit(double tranchesProduitGratuit) {
        if(tranchesProduitGratuit <= 0) { throw new IllegalArgumentException(); }
        TranchesProduitGratuit = tranchesProduitGratuit;
    }


}
