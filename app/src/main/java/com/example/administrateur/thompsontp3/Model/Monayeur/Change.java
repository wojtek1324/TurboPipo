package com.example.administrateur.thompsontp3.Model.Monayeur;

public interface Change {

	public int numberOfItemsFor(Money m);

	public  void addItems(Money m, int number);
	
	public double totalValue();
	
	public int totalNumberOfItems();

}
