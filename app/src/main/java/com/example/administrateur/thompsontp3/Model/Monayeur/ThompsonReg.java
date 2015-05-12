package com.example.administrateur.thompsontp3.Model.Monayeur;

import java.util.EnumMap;
import java.util.Map;

import com.example.administrateur.thompsontp3.ExceptionsCaisse.TypeArgentManquantException;
import com.example.administrateur.thompsontp3.ExceptionsCaisse.TypeItemPleinException;

public class ThompsonReg implements CashRegister{

	Map<Money, Integer> maxValues; 
	// ArrayList<Money> items;
	Map<Money, Integer> items;
	public ThompsonReg() {
		items = new EnumMap<Money, Integer>(Money.class);
		
		maxValues = new EnumMap<Money, Integer>(Money.class);
		
		maxValues.put(Money.coin1s, 100);
		maxValues.put(Money.coin5s, 100);
		maxValues.put(Money.coin10s, 100);
	//	maxValues.put(Money.coin20s, 0);
		maxValues.put(Money.coin25s, 100);
		maxValues.put(Money.coin1, 100);
		maxValues.put(Money.coin2, 100);
		maxValues.put(Money.bill5, 100);
		maxValues.put(Money.bill10, 100);
		maxValues.put(Money.bill20, 100);		
		maxValues.put(Money.bill50, 100);
		maxValues.put(Money.bill100, 100);
		
		for (Money argent : Money.values()) {
			int maxValue = (int)maxValues.get(argent);
			items.put(argent, maxValue);
		}
	}
	
	@Override
	public int numberOfItemsFor(Money m) {
		return items.get(m);
	}

	@Override
	public void addItems(Money m, int number) {
		// TODO Auto-generated method stub
		if(number < 0) { throw new IllegalArgumentException("On ne peut pas ajouter un nombre n�gatif"); }
		int nouveauMontant = numberOfItemsFor(m) + number;
		if(nouveauMontant > maxValues.get(m))
		{
			throw new TypeItemPleinException(m);
		}
		items.put(m, nouveauMontant);
	}

	@Override
	public double totalValue() {
		double total = 0;
		for (Money m : Money.values()) {
			total += (items.get(m) * m.value());
		}
		return total;
	}

	@Override
	public int totalNumberOfItems() {
		int total = 0;
		for (Money m : Money.values()) {
			total += items.get(m);
		}
		return total;
	}

	@Override
	public int maxCapacityFor(Money m) {	
		return (int)maxValues.get(m);
	}

	@Override
	public void useItems(Money m, int number) {
		if(number < 0) { throw new IllegalArgumentException("On ne peut pas retirer un nombre n�gatif"); }
		int numberOfItemsLeft = numberOfItemsFor(m) - number;
		if(numberOfItemsLeft < 0) { throw new TypeArgentManquantException(m); }
		
		items.put(m, numberOfItemsLeft);
	}

}
