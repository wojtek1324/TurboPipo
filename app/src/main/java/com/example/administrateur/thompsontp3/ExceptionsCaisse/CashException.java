package com.example.administrateur.thompsontp3.ExceptionsCaisse;

public class CashException extends Exception{
	@Override
	public String getMessage() {
		
		return "Il ne reste pas assez d'argent dans la caisse pour retourner le montant.";		
	}
}
