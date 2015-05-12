package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Repo.RepositoryProduitFichier;

/**
 * Created by 1195096 on 2015-05-12.
 */
public class RabaisCourantTest extends AndroidTestCase{
    RabaisCourant rabaisCourantTests;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        rabaisCourantTests = new RabaisCourant();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        rabaisCourantTests = null;
    }

    public void testAjoout2pour1ItemNull() {
        try{
            AchatItem itemNull = null;
            rabaisCourantTests.Ajouter2Pour1(itemNull);
            fail();

        }catch(RabaisCourant.ItemEstDejaEn2Pour1 e){

        }catch(IllegalArgumentException e2){
        }
    }

    public void testAjout2pour1(){
        try{
            AchatItem itemAjout = new AchatItem("ProduitTest", 25.25, "1234567890");
            rabaisCourantTests.Ajouter2Pour1(itemAjout);
            rabaisCourantTests.Ajouter2Pour1(itemAjout);
            fail();
        } catch (RabaisCourant.ItemEstDejaEn2Pour1 itemEstDejaEn2Pour1) {
            itemEstDejaEn2Pour1.printStackTrace();
        }
    }

    public void testSuprimerItemInnexistant(){
        try{
            AchatItem itemInexistant = new AchatItem("ProduitTest", 25.25, "1234567890");
            rabaisCourantTests.Supprimer2pour1(itemInexistant);
            fail();
        } catch (RabaisCourant.ItemPasDansLaListe ItemPasDansLaListe)
        {
        }
    }

    public void testSeuilTaxTropPetit(){
        try {
            rabaisCourantTests.setSeuilPasDeTaxes(0);
            fail();
        }catch(IllegalArgumentException e){
        }
    }

    public void testTrancheProduitGratuiTropPetit(){
        try{
            rabaisCourantTests.setTranchesProduitGratuit(0);
        }catch(IllegalArgumentException e){
        }
    }
}
