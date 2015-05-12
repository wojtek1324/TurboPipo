package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Repo.RepositoryProduitFichier;
import com.example.administrateur.thompsontp3.Service.ServiceProduit;

import java.util.ArrayList;

/**
 * Created by 1263287 on 2015-05-05.
 */
public class ServiceProduitTest extends AndroidTestCase {


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


    public void testScan1(){
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        ArrayList<TransactionItem> lstTransac = new ArrayList<TransactionItem>();
        AchatItem itemTest1 = new AchatItem("produitTest1", 20.0, "1234567891");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");
        repository.saveMany(itemTest1, itemTest2);
        lstTransac.add(new TransactionItem(itemTest1, 1));

        serviceProduit.scan(lstTransac, "1234567892");

        assertEquals(lstTransac.size(), 2);
    }

    public void testScan2(){
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        ArrayList<TransactionItem> lstTransac = new ArrayList<TransactionItem>();
        AchatItem itemTest1 = new AchatItem("produitTest1", 20.0, "1234567891");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");
        repository.saveMany(itemTest1, itemTest2);
        lstTransac.add(new TransactionItem(itemTest1, 1));

        serviceProduit.scan(lstTransac, "1234567891");

        assertEquals(lstTransac.size(), 1);
    }


    public void testScanException1(){
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        ArrayList<TransactionItem> lstTransac = null;
        AchatItem itemTest1 = new AchatItem("produitTest1", 20.0, "1234567891");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");
        repository.saveMany(itemTest1, itemTest2);

        try
        {
            serviceProduit.scan(lstTransac, "1234567891");
            fail();
        } catch (NullPointerException e)
        {

        }
    }


    public void testAjouterProduit1()
    {
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        AchatItem itemTest1 = new AchatItem("produitTest1", 20.0, "1234567891");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");

        try {
            serviceProduit.AjouterProduit(itemTest1);
            assertEquals(repository.getAll().size(), 1);

            serviceProduit.AjouterProduit(itemTest2);
            assertEquals(repository.getAll().size(), 2);
        } catch (ServiceProduit.SameBarcodeException e) {
            e.printStackTrace();
        }


    }

    public void testAjouterProduit2()
    {
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        AchatItem itemTest1 = new AchatItem("produitTest1", 20.0, "1234567892");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");

        try {
            serviceProduit.AjouterProduit(itemTest1);
            assertEquals(repository.getAll().size(), 1);

            serviceProduit.AjouterProduit(itemTest2);
            fail();
        } catch (ServiceProduit.SameBarcodeException e) {
            e.printStackTrace();
        }


    }


    public void testAjouterProduit3()
    {
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        AchatItem itemTest1 = new AchatItem("", 20.0, "1234567892");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");

        try {
            serviceProduit.AjouterProduit(itemTest1);
            fail();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (ServiceProduit.SameBarcodeException e) {
            e.printStackTrace();
        }


    }

    public void testAjouterProduit4()
    {
        ServiceProduit serviceProduit = new ServiceProduit(repository);
        AchatItem itemTest1 = new AchatItem("eweasd", -20.0, "1234567892");
        AchatItem itemTest2 = new AchatItem("produitTest2", 20.0, "1234567892");

        try {
            serviceProduit.AjouterProduit(itemTest1);
            fail();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (ServiceProduit.SameBarcodeException e) {
            e.printStackTrace();
        }


    }

}
