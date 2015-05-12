package com.example.administrateur.thompsontp3.Model.Monayeur;

import java.util.EnumMap;

public class ThompsonChange implements Change {

EnumMap<Money, Integer> items;

public ThompsonChange() {
	items = new EnumMap<Money, Integer>(Money.class);
	for (Money m : Money.values()) {
		items.put(m, 0);
	}
}
	
	@Override
	public int numberOfItemsFor(Money m) {		
		return items.get(m);
	}
	
	@Override
	public void addItems(Money m, int number) {
		if(number < 0) { throw new IllegalArgumentException(); }
		int nombreItems = items.get(m) + number;
		items.put(m, nombreItems);
	}

	@Override
	public double totalValue() {
		// TODO Auto-generated method stub
		double total = 0;
		for (Money money : Money.values()) {
			total += items.get(money) * money.value();
		}
		return total;
	}

	@Override
	public int totalNumberOfItems() {
		int total = 0;
		for (Money money : Money.values()) {
			total += items.get(money);
		}
		return total;
	}

	
}
