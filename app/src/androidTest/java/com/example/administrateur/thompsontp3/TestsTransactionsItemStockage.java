package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Repo.RepositoryTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-04-27.
 */
public class TestsTransactionsItemStockage extends AndroidTestCase {
    CRUD<TransactionItem> repository;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        repository = new RepositoryTransaction(getContext());
        repository.deleteAll();

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        repository.deleteAll();
        repository = null;
    }

    public void testSaveAndGetAll(){
        AchatItem p1 = new AchatItem("produit", 2, "123456789");
        TransactionItem p = new TransactionItem(p1, 2);
        repository.save(p);
        assertEquals(repository.getAll().size(), 1);
    }

    public void testSaveManyAndGetAll(){
        List<TransactionItem> prods = new ArrayList<TransactionItem>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            AchatItem p1 = new AchatItem("test"+i, 1+i, "1"+i);
            TransactionItem p = new TransactionItem(p1, 1);
            prods.add(p);
        }
        repository.saveMany(prods);
        assertEquals(size, repository.getAll().size());
    }

    public void testGetById(){
        AchatItem p1 = new AchatItem("test1", 10.0, "123456");
        TransactionItem p = new TransactionItem(p1, 5);
        long tested = repository.save(p);
        TransactionItem recov = repository.getById(tested);
        assertEquals(recov.quantity.intValue(), 5);
    }

    public void testDeleteOne(){
        AchatItem p1 = new AchatItem("test1", 2, "123456");
        TransactionItem p = new TransactionItem(p1, 1);
        repository.save(p);

        assertEquals(1, repository.getAll().size());
        repository.deleteOne(p);
        assertEquals(0, repository.getAll().size());
    }

    public void testDeleteOneById(){
        AchatItem p1 = new AchatItem("test1", 2, "123456");
        TransactionItem p = new TransactionItem(p1, 1);
        repository.save(p);
        assertEquals(1, repository.getAll().size());
        repository.deleteOne(p.getId());
        Log.i("TestsCRUD", repository.getAll().toString());
        assertEquals(0, repository.getAll().size());
    }

    public void testDeleteAll(){
        List<TransactionItem> prods = new ArrayList<TransactionItem>();
        int size = 22;
        for (int i = 0 ; i < size ; i++){
            AchatItem p1 = new AchatItem("test1"+ i, 2 + i, "123456" + i);
            TransactionItem p = new TransactionItem(p1, 1);
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
            AchatItem p1 = new AchatItem("test1" + i, 2 + i, "123456" + i);
            TransactionItem p = new TransactionItem(p1, 1);
            repository.save(p);
        }
        long b = System.currentTimeMillis();
        Log.i("TestLoad", repository.getClass().getSimpleName()+"  : temps est " + (b - a) + " ms");
    }

    public void testUpdate()
    {
        AchatItem p1 = new AchatItem("test1", 2, "123456");
        TransactionItem p = new TransactionItem(p1, 1);
        repository.save(p);

    }

}
