package com.example.administrateur.thompsontp3;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Model.Transaction;
import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.example.administrateur.thompsontp3.Service.ServicePrixTotal;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by 1195096 on 2015-05-16.
 */
public class ServicePrixTotalTest extends AndroidTestCase {
    Transaction transactionTests;
    RabaisCourant rabaisCourantTests;
    ServicePrixTotal servicePrixTotalTests;

    TransactionItem transactionItemTests;
    AchatItem achatItemTests;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        transactionTests = new Transaction();
        rabaisCourantTests = new RabaisCourant();
        servicePrixTotalTests = new ServicePrixTotal(transactionTests, rabaisCourantTests);
    }

    public void testCalculer2pou1Impaire() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 5);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.Ajouter2Pour1(achatItemTests);

        servicePrixTotalTests.calculer2Pour1(rabaisCourantTests);

        int qttAjust = servicePrixTotalTests.listPourCalcul.get(0).quantity;

        Assert.assertEquals(qttAjust, 3);
    }

    public void testCalculer2pou1Paire() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 8);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.Ajouter2Pour1(achatItemTests);

        servicePrixTotalTests.calculer2Pour1(rabaisCourantTests);

        int qttAjust = servicePrixTotalTests.listPourCalcul.get(0).quantity;

        Assert.assertEquals(qttAjust, 4);
    }

    public void testCalculer2pou1Sans2pour1() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 8);
        transactionTests.Transactions.add(transactionItemTests);

        servicePrixTotalTests.calculer2Pour1(rabaisCourantTests);

        int qttAjust = servicePrixTotalTests.listPourCalcul.get(0).quantity;

        Assert.assertEquals(qttAjust, 8);
    }

    public void testCalculer2pou1AvecProduitAbsent() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        AchatItem achatItemTests1 = new AchatItem("Porduit1", 3.25, "1234567", true);
        AchatItem achatItemTests2 = new AchatItem("Porduit2", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests1, 8);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.Ajouter2Pour1(achatItemTests2);

        servicePrixTotalTests.calculer2Pour1(rabaisCourantTests);

        int qttAjust = servicePrixTotalTests.listPourCalcul.get(0).quantity;

        Assert.assertEquals(qttAjust, 8);
    }

    public void testPrixAuDelaDuSeuil()
    {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 8);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.setSeuilPasDeTaxes(10);


        boolean resultat =  servicePrixTotalTests.prixAuDelaDuSeuilExemptionTaxes(transactionTests.prixTotalBase(), rabaisCourantTests);

        Assert.assertEquals(resultat, true);
    }

    public void testPrixAuDessouDuSeuil()
    {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 1);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.setSeuilPasDeTaxes(10);


        boolean resultat =  servicePrixTotalTests.prixAuDelaDuSeuilExemptionTaxes(transactionTests.prixTotalBase(), rabaisCourantTests);

        Assert.assertEquals(resultat, false);
    }

    public void testPrixApresRabaisAvecItems2pour1pair() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 8);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.Ajouter2Pour1(achatItemTests);

        servicePrixTotalTests.AppliquerRabaisEtTaxes(transactionTests);

        // 3.25 * 8 / 2 * 1.15
        Assert.assertEquals(transactionTests.PrixApresRabaisEtTaxes,  14.95);

    }

    public void testPrixApresRabaisAvecItems2pour1impair() throws RabaisCourant.ItemEstDejaEn2Pour1 {
        achatItemTests = new AchatItem("Porduit1", 5.00, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 5);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.Ajouter2Pour1(achatItemTests);

        servicePrixTotalTests.AppliquerRabaisEtTaxes(transactionTests);

        // 3 * 5 * 1.15
        Assert.assertEquals(transactionTests.PrixApresRabaisEtTaxes,  17.25);
    }

    public void testEgalAuSeuilPasDeTaxe()
    {
        achatItemTests = new AchatItem("Porduit1", 5.00, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 5);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.setSeuilPasDeTaxes(25);

        servicePrixTotalTests.AppliquerRabaisEtTaxes(transactionTests);

        //Vu que le total est exactement le même que le seuil pas de taxe, les taxes sont appliqués.

        Assert.assertEquals(transactionTests.PrixApresRabaisEtTaxes,  28.75);
    }

    public void testSeuilPasDeTaxeSimple()
    {
        achatItemTests = new AchatItem("Porduit1", 5.00, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 5);
        transactionTests.Transactions.add(transactionItemTests);

        rabaisCourantTests.setSeuilPasDeTaxes(24);

        servicePrixTotalTests.AppliquerRabaisEtTaxes(transactionTests);

        Assert.assertEquals(transactionTests.PrixApresRabaisEtTaxes,  25.0);
    }

    public void testQuantiteProduitGratuitRecue()
    {
        achatItemTests = new AchatItem("Porduit1", 5.00, "1234567", true);
        AchatItem achatItemTests2 = new AchatItem("ProduitGratuit", 3.00, "1234567", true);

        transactionItemTests = new TransactionItem(achatItemTests, 5);
        transactionTests.Transactions.add(transactionItemTests);


        rabaisCourantTests.setProduitGratuit(achatItemTests2);
        rabaisCourantTests.setTranchesProduitGratuit(5);

        servicePrixTotalTests.AppliquerRabaisEtTaxes(transactionTests);

        Assert.assertEquals(transactionTests.Transactions.size(), 2);
    }






}
