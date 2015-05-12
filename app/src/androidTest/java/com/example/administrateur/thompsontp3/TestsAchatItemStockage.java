package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Repo.RepositoryProduitFichier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-04-27.
 */
public class TestsAchatItemStockage extends AndroidTestCase {
    CRUD<AchatItem> repository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new RepositoryProduitFichier(getContext());
        repository.deleteAll();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.deleteAll();
        repository = null;
    }

    public void testSaveAndGetAll(){
        AchatItem p = new AchatItem("produit", 2, "123456789");
        repository.save(p);
        assertEquals(repository.getAll().size(), 1);
    }

    public void testSaveManyAndGetAll(){
        List<AchatItem> prods = new ArrayList<AchatItem>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            AchatItem p = new AchatItem("test"+i, 1+i, "1"+i);
            prods.add(p);
        }
        repository.saveMany(prods);
        assertEquals(size, repository.getAll().size());
    }

    public void testGetById(){
        AchatItem p = new AchatItem("test1", 10.0, "123456");
        long tested = repository.save(p);
        AchatItem recov = repository.getById(tested);
        assertEquals(recov.prix, 10.0);
    }

    public void testDeleteOne(){
        AchatItem p = new AchatItem("test1", 2, "123456");
        repository.save(p);

        assertEquals(1, repository.getAll().size());
        repository.deleteOne(p);
        assertEquals(0, repository.getAll().size());
    }

    public void testDeleteOneById(){
        AchatItem p = new AchatItem("test1", 2, "123456");
        repository.save(p);
        assertEquals(1, repository.getAll().size());
        repository.deleteOne(p.getId());
        Log.i("TestsCRUD", repository.getAll().toString());
        assertEquals(0, repository.getAll().size());
    }

    public void testDeleteAll(){
        List<AchatItem> prods = new ArrayList<AchatItem>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            AchatItem p = new AchatItem("test1"+ i, 2 + i, "123456" + i);
            prods.add(p);
        }
        repository.saveMany(prods);
        assertEquals(size, repository.getAll().size());
        repository.deleteAll();
        assertEquals(0, repository.getAll().size());
    }

    public void testSaveOnePerformance(){
        long a = System.currentTimeMillis();
        for (int i = 0 ; i < 30 ; i++){
            AchatItem p = new AchatItem("test1" + i, 2 + i, "123456" + i);
            repository.save(p);
        }
        long b = System.currentTimeMillis();
        Log.i("TestLoad", repository.getClass().getSimpleName()+"  : temps est " + (b - a) + " ms");
    }

    public void testUpdate()
    {
        AchatItem p = new AchatItem("test1", 2, "123456");
        repository.save(p);

    }

    public void testRechercheCodeBarre()
    {
        AchatItem p1 = new AchatItem("test1", 2, "123456");
        AchatItem p2 = new AchatItem("test2", 2, "1234567");

        repository.saveMany(p1, p2);

        AchatItem itemTrouve = null;
        for(AchatItem item : repository.getAll())
        {
            if(item.codeBarre.equals("123456"))
            {
                itemTrouve = item;
            }
        }
        assertEquals(itemTrouve.produit, "test1");
    }
}
