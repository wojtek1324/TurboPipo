package com.example.administrateur.thompsontp3.ExceptionsCaisse;

import com.example.administrateur.thompsontp3.Model.Monayeur.Money;

public class TypeArgentManquantException extends RuntimeException {
	private Money m_money;
	
	public TypeArgentManquantException(Money pMoney)
	{
		m_money = pMoney;
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Le type d'argent: " + m_money.toString() + " est vide.";
	}
}
