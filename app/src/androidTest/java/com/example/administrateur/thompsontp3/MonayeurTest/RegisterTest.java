package com.example.administrateur.thompsontp3.MonayeurTest;

import android.test.AndroidTestCase;

import junit.framework.Assert;

import com.example.administrateur.thompsontp3.Model.Monayeur.*;

import com.example.administrateur.thompsontp3.ExceptionsCaisse.TypeArgentManquantException;
import com.example.administrateur.thompsontp3.ExceptionsCaisse.TypeItemPleinException;

public class RegisterTest extends AndroidTestCase {
	

	public void test1() {
		ThompsonReg register = new ThompsonReg();
		register.useItems(Money.bill100, 50);
		// Expected value: 50
		Assert.assertEquals(50, register.numberOfItemsFor(Money.bill100));
	}
	

	public void test2() {
		ThompsonReg register = new ThompsonReg();
		// Expected: 100
		Assert.assertEquals(100, register.maxCapacityFor(Money.bill100));
	}
	

	public void test3() {
		ThompsonReg register = new ThompsonReg();
		register.useItems(Money.bill10, 100);
		register.useItems(Money.bill100, 100);
		register.useItems(Money.bill20, 100);
		register.useItems(Money.bill5, 100);
		register.useItems(Money.bill50, 100);
		register.useItems(Money.coin1, 100);
		register.useItems(Money.coin10s, 100);
		register.useItems(Money.coin2, 100);
		register.useItems(Money.coin25s, 100);
		register.useItems(Money.coin5s, 100);
		register.useItems(Money.coin1s, 100);
		
		// Expected: 0
		
		Assert.assertEquals(0, register.totalNumberOfItems());
	}
	

	public void test4() {
		ThompsonReg register = new ThompsonReg();
		register.useItems(Money.bill10, 100);
		register.useItems(Money.bill100, 100);
		register.useItems(Money.bill20, 100);
		register.useItems(Money.bill5, 100);
		register.useItems(Money.bill50, 100);
		register.useItems(Money.coin1, 100);
		register.useItems(Money.coin10s, 100);
		register.useItems(Money.coin2, 100);
		register.useItems(Money.coin25s, 100);
		register.useItems(Money.coin5s, 100);
		register.useItems(Money.coin1s, 100);
				
		// Expected: 0.0
		
		Assert.assertEquals(0.0, register.totalValue());
	}

	public void test5() {
		ThompsonReg register = new ThompsonReg();
        try{
            register.addItems(Money.bill100, -1);
            fail();
        } catch(IllegalArgumentException e) {

        }


	}

	public void test6() {
		ThompsonReg register = new ThompsonReg();
        try {
            register.addItems(Money.bill100, 10);
            fail();
        }catch (TypeItemPleinException e) {

        }

	}
	

	public void test7() {
		ThompsonReg register = new ThompsonReg();
        try {
            register.useItems(Money.bill100, 101);
        } catch(TypeArgentManquantException e) {

        }

	}
}
