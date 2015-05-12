package com.example.administrateur.thompsontp3.Model;

/**
 * Created by 1263287 on 2015-04-14.
 */
public class AchatItem {

    public AchatItem()
    {
        taxable = true;
    }

    public AchatItem(String nom, double pPrix, String pcodeBarre)
    {
        produit = nom;
        prix = pPrix;
        codeBarre = pcodeBarre;
        taxable = true;
    }

    public AchatItem(String nom, double pPrix, String pcodeBarre, boolean ptaxable)
    {
        produit = nom;
        prix = pPrix;
        codeBarre = pcodeBarre;
        taxable = ptaxable;
    }

    public String produit;

    public double prix;

    public String codeBarre;

    public Long id;

    public Long getId()
    {
        return id;
    }

    public void setId(long id) {this.id = id; }

    public boolean taxable;
}
