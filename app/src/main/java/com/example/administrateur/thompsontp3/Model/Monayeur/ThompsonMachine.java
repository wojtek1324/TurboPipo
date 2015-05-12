package com.example.administrateur.thompsontp3.Model.Monayeur;

import com.example.administrateur.thompsontp3.ExceptionsCaisse.CashException;

public class ThompsonMachine implements MoneyMachine {

	@Override
	public Change computeChange(double amount, CashRegister register)
			throws CashException {
		if(amount < 0) { throw new IllegalArgumentException(); }
		
		if(amount > register.totalValue()) { throw new CashException(); }
		
		ThompsonChange change = new ThompsonChange();
		amount = Arrondir(amount);
		
		int Amount = (int)(amount * 100);
		for (Money m : Money.values()) {
			
			if((Amount / m.centValue) != 0) 
			{
				int nbTypeArgent = (Amount / m.centValue);
				if(nbTypeArgent > register.numberOfItemsFor(m)) 
				{
					nbTypeArgent = register.numberOfItemsFor(m);
				}
				change.addItems(m, nbTypeArgent);
				register.useItems(m, nbTypeArgent);
				Amount -= (int)(m.centValue * nbTypeArgent);
			}
		}		
		
		return change;
	}
	
	public double Arrondir(double pArgent) {
		if((pArgent * 100) % 5 == 0) { return pArgent; }			
		return Math.round(pArgent / 0.05) * 0.05;
	}
	
	

}
