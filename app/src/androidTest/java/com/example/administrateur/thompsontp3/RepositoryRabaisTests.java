package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Repo.RepositoryRabais;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 1195096 on 2015-05-22.
 */
public class RepositoryRabaisTests extends AndroidTestCase {

    CRUD<RabaisCourant> repository;
    RabaisCourant rabaisCourant;
    AchatItem item2pour1;
    AchatItem itemGratuit;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new RepositoryRabais(getContext());
        repository.deleteAll();

        rabaisCourant = new RabaisCourant();
        AchatItem item2pour1 = new AchatItem("ProduitTest", 25.25, "1234567890");
        AchatItem itemGratuit = new AchatItem("ProduitTest2", 40.00, "3334567890");
        rabaisCourant.Ajouter2Pour1(item2pour1);
        rabaisCourant.setTranchesProduitGratuit(25);
        rabaisCourant.setProduitGratuit(itemGratuit);
        rabaisCourant.setSeuilPasDeTaxes(100);

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.deleteAll();
        repository = null;
    }

    public void testGetByID(){
        long tested = repository.save(rabaisCourant);
        RabaisCourant rabaisCourant2 = repository.getById(tested);

        assertEquals(rabaisCourant2.getSeuilPasDeTaxes(), 100.0);
        assertEquals(rabaisCourant2.getTranchesProduitGratuit(), 25.0);
        assertEquals(rabaisCourant2.getProduitGratuit(), itemGratuit);
        assertEquals(rabaisCourant2.getList2Pour1().size(), 1);
        assertEquals(rabaisCourant2.getList2Pour1().get(0), item2pour1);
    }

    public void testSaveAndGetAll(){
        repository.save(rabaisCourant);
        assertEquals(repository.getAll().size(), 1);
    }

    public void testDeleteOne(){
        repository.save(rabaisCourant);

        assertEquals(1, repository.getAll().size());
        repository.deleteOne(rabaisCourant);
        assertEquals(0, repository.getAll().size());
    }

    public void testDeleteAll(){
        repository.save(rabaisCourant);
        assertEquals(1, repository.getAll().size());
        repository.deleteAll();
        assertEquals(0, repository.getAll().size());
    }




}
