package com.example.administrateur.thompsontp3.Model.Monayeur;

import com.example.administrateur.thompsontp3.ExceptionsCaisse.CashException;

public interface MoneyMachine {

	public Change computeChange(double amount, CashRegister register) throws CashException;
	
}
