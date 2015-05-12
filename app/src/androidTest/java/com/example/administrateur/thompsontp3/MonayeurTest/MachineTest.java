package com.example.administrateur.thompsontp3.MonayeurTest;


import android.test.AndroidTestCase;

import junit.framework.Assert;

import com.example.administrateur.thompsontp3.Model.Monayeur.*;
import com.example.administrateur.thompsontp3.ExceptionsCaisse.*;


public class MachineTest extends AndroidTestCase{
	

	public void testArrondi01()
	{
		ThompsonMachine machine = new ThompsonMachine();
		double resultat = machine.Arrondir(0.01);
		Assert.assertEquals(resultat, 0.0);
	}
	

	public void testArrondi02()
	{
		ThompsonMachine machine = new ThompsonMachine();
		double resultat = machine.Arrondir(0.02);
		Assert.assertEquals(resultat, 0.0);
	}
	

	public void testArrondi03()
	{
		ThompsonMachine machine = new ThompsonMachine();
		double resultat = machine.Arrondir(0.03);
		Assert.assertEquals(resultat, 0.05);
	}
	

	public void testArrondi04()
	{
		ThompsonMachine machine = new ThompsonMachine();
		double resultat = machine.Arrondir(0.04);
		Assert.assertEquals(resultat, 0.05);
	}
	

	public void testArrondi06()
	{
		ThompsonMachine machine = new ThompsonMachine();
		double resultat = machine.Arrondir(0.06);
		Assert.assertEquals(resultat, 0.05);
	}
	
	

	public void testAvec120() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		changeAttendu.addItems(Money.bill100, 1);
		changeAttendu.addItems(Money.bill20, 1);
				
		try {
			changeResultat = (ThompsonChange)machine.computeChange(120, register);
			Assert.assertEquals(changeAttendu.numberOfItemsFor(Money.bill100), changeResultat.numberOfItemsFor(Money.bill100));
			Assert.assertEquals(changeAttendu.numberOfItemsFor(Money.bill20), changeResultat.numberOfItemsFor(Money.bill20));
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public void testAvec999() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		changeAttendu.addItems(Money.bill100, 9);
		changeAttendu.addItems(Money.bill50, 1);
		changeAttendu.addItems(Money.bill20, 2);
		changeAttendu.addItems(Money.bill5, 1);
		changeAttendu.addItems(Money.coin2, 2);
		
		
		try {
			changeResultat = (ThompsonChange)machine.computeChange(999, register);
			for (Money m : Money.values()) {
				Assert.assertEquals(changeAttendu.numberOfItemsFor(m), changeResultat.numberOfItemsFor(m));
			}
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void testAvecTypeArgentManquantPour200() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		register.useItems(Money.bill100, 100);
		
		changeAttendu.addItems(Money.bill100, 0);
		changeAttendu.addItems(Money.bill50, 4);
		
		try {
			changeResultat = (ThompsonChange)machine.computeChange(200, register);
			for (Money m : Money.values()) {
				Assert.assertEquals(changeAttendu.numberOfItemsFor(m), changeResultat.numberOfItemsFor(m));
			}
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	


	public void testAvecTypeArgentManquantPour9000AvecTypeManquant() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		register.useItems(Money.bill100, 50);
		
		changeAttendu.addItems(Money.bill100, 50);
		changeAttendu.addItems(Money.bill50, 80);
		
		try {
			changeResultat = (ThompsonChange)machine.computeChange(9000, register);
			for (Money m : Money.values()) {
				Assert.assertEquals(changeAttendu.numberOfItemsFor(m), changeResultat.numberOfItemsFor(m));
			}
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	

	public void testAvecArgentManquantPour90000AvecTypeManquant() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		register.useItems(Money.bill100, 50);
		

		
		try {
			changeResultat = (ThompsonChange)machine.computeChange(90000, register);
			Assert.fail();
		} catch (CashException e) {
			System.out.println(e.getMessage());
		}
	}
	

	public void testPourNegatif() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		try {
			changeResultat = (ThompsonChange)machine.computeChange(-100, register);
            fail();
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException a) {

        }
	}
	
	

	public void testAvecTropDecimal() {
		ThompsonReg register = new ThompsonReg();
		ThompsonChange changeAttendu = new ThompsonChange();
		ThompsonChange changeResultat;
		ThompsonMachine machine = new ThompsonMachine();
		
		changeAttendu.addItems(Money.bill100, 1);
		changeAttendu.addItems(Money.bill20, 1);
		changeAttendu.addItems(Money.coin10s, 1);
				
		try {
			changeResultat = (ThompsonChange)machine.computeChange(120.103451, register);
			Assert.assertEquals(changeAttendu.numberOfItemsFor(Money.bill100), changeResultat.numberOfItemsFor(Money.bill100));
			Assert.assertEquals(changeAttendu.numberOfItemsFor(Money.bill20), changeResultat.numberOfItemsFor(Money.bill20));
		} catch (CashException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
