package com.example.administrateur.thompsontp3.MonayeurTest;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Model.Transaction;
import com.example.administrateur.thompsontp3.Model.TransactionItem;

import junit.framework.TestCase;

/**
 * Created by 1195096 on 2015-05-16.
 */
public class ServicePrixTotalTest extends AndroidTestCase {
    RabaisCourant rabaisCourantTests;
    Transaction transactionTests;
    TransactionItem transactionItemTests;
    AchatItem achatItemTests;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        achatItemTests = new AchatItem("Porduit1", 3.25, "1234567", true);
        

        transactionItemTests = new TransactionItem()
        transactionTests = new

        rabaisCourantTests = new RabaisCourant();
    }


}
