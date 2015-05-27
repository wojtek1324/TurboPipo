package com.example.administrateur.thompsontp3.Gui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.example.administrateur.thompsontp3.R;
import com.example.administrateur.thompsontp3.Service.ServicePrixTotal;
import com.example.administrateur.thompsontp3.ThompsonMainActivity;

/**
 * Created by 1263287 on 2015-05-26.
 */
public class TicketCaisseDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.ticket_caisse, container, false);

        ServicePrixTotal servicePrixTotal = new ServicePrixTotal(ThompsonMainActivity.transactionCourante, ThompsonMainActivity.rabaisCourant);

        String ticketString = "";

        ticketString += "Total avant taxes: " +  String.format("%1$,.2f", ThompsonMainActivity.transactionCourante.prixTotalBase()) + "$ \n";
        ticketString += "Total apres taxes et rabais: " + String.format("%1$,.2f", ThompsonMainActivity.transactionCourante.PrixApresRabaisEtTaxes) + "$ \n";

        ticketString += "\n";

        for(TransactionItem itemTransaction : ThompsonMainActivity.transactionCourante.Transactions) {
            ticketString += itemTransaction.achatItem.produit + espaces(30) + (itemTransaction.quantity) + espaces(30) + String.format("%1$,.2f",itemTransaction.quantity*itemTransaction.achatItem.prix) + "$";
            if(ThompsonMainActivity.rabaisCourant.itemEstEn2Pour1(itemTransaction.achatItem)) {
                ticketString += espaces(5);
                ticketString += "2 pour 1";
            }
            ticketString += "\n";
            ticketString += "\n";

        }

        if(servicePrixTotal.prixAuDelaDuSeuilExemptionTaxes(ThompsonMainActivity.transactionCourante.PrixApresRabaisEtTaxes, ThompsonMainActivity.rabaisCourant)) {
            ticketString += "Les taxes ne sont pas pris en comptes car vous avez depassé le seuil d'exemption de taxes qui est de " + String.valueOf(ThompsonMainActivity.rabaisCourant.getSeuilPasDeTaxes()) + "\n \n";
        }

        if(servicePrixTotal.produitsGratuit != null && servicePrixTotal.produitsGratuit.quantity != 0) {
            ticketString += "Vous avez recue " + String.valueOf(servicePrixTotal.produitsGratuit.quantity) + " " + servicePrixTotal.produitsGratuit.achatItem.produit + " gratuitement! \n \n";
        }

        ticketString += "Monnaie donnée: " + "\n";

        ticketString += "Monnaie rendue: ";


        TextView ticketText = (TextView) v.findViewById(R.id.ticket_text);
        ticketText.setText(ticketString);

        return v;
    }

    String espaces(int nbEspaces) {
        String espaces = "";
        for(int index = 0; index < nbEspaces; index++) {
            espaces += " ";
        }
        return espaces;
    }


}
