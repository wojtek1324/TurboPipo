package com.example.administrateur.thompsontp3.Model.Monayeur;

public interface CashRegister extends Change {

	public int maxCapacityFor(Money m);
	
	public void useItems(Money m, int number);
	
}
