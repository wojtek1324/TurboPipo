package com.example.administrateur.thompsontp3.Model;

/**
 * Created by 1263287 on 2015-04-14.
 */
public class AchatItem {

    public AchatItem()
    {

    }

    public AchatItem(String nom, double pPrix, String pcodeBarre)
    {
        produit = nom;
        prix = pPrix;
        codeBarre = pcodeBarre;
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
}
