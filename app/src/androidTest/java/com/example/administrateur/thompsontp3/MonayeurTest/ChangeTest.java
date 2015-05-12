package com.example.administrateur.thompsontp3.MonayeurTest;

import android.test.AndroidTestCase;

import com.example.administrateur.thompsontp3.Model.Monayeur.ThompsonChange;

import junit.framework.Assert;

import com.example.administrateur.thompsontp3.Model.Monayeur.*;

public class ChangeTest extends AndroidTestCase {
	

	public void test1() {
		ThompsonChange change = new ThompsonChange();
		change.addItems(Money.bill20, 2);
		//Expected value: 40
		double expected = 40;
		Assert.assertEquals(expected, change.totalValue());
	}
	

	public void test2() {
		ThompsonChange change = new ThompsonChange();
		change.addItems(Money.bill20, 2);
		change.addItems(Money.bill10, 1);
		change.addItems(Money.coin25s, 2);
		//Expected value: 50.50
		double expected = 50.50;
		Assert.assertEquals(expected, change.totalValue());
	}
	

	public void test3() {
		ThompsonChange change = new ThompsonChange();
		change.addItems(Money.bill100, 10);
		//Expected value: 10
		int expected = 10;
		Assert.assertEquals(expected, change.numberOfItemsFor(Money.bill100));
	}
	

	public void test4() {
		ThompsonChange change = new ThompsonChange();
		change.addItems(Money.bill100, 10);
		change.addItems(Money.bill100, 10);
		change.addItems(Money.bill10, 10);
		// Expected value: 30
		int expected = 30;
		Assert.assertEquals(expected, change.totalNumberOfItems());
	}
	
   // (expected = IllegalArgumentException.class)
	public void test5() {
		ThompsonChange change = new ThompsonChange();
        try
        {
            change.addItems(Money.bill10, -1);
            fail();
        } catch (IllegalArgumentException e) {

        }

	}
}
