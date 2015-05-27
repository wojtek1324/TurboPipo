package com.example.administrateur.thompsontp3.Gui;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrateur.thompsontp3.Model.ElementDeListe;
import com.example.administrateur.thompsontp3.Model.Monayeur.Money;
import com.example.administrateur.thompsontp3.Model.Monayeur.ThompsonMachine;
import com.example.administrateur.thompsontp3.Model.Monayeur.ThompsonReg;
import com.example.administrateur.thompsontp3.R;
import com.example.administrateur.thompsontp3.Service.ServicePrixTotal;
import com.example.administrateur.thompsontp3.ThompsonMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-05-25.
 */
public class PayerDialog extends DialogFragment {

    PayerAdapteur adapter;
    List<ElementDeListe> billets;

    Context context;
    ThompsonReg cashRegister = new ThompsonReg();
    ThompsonMachine machine = new ThompsonMachine();
    double prixApresRabaisTaxes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.payer_layout, container, false);

        billets = new ArrayList<ElementDeListe>();
        adapter = new PayerAdapteur(v.getContext(), billets);
        ListView list = (ListView) v.findViewById(R.id.liste_billets);
        list.setAdapter(adapter);

        TextView totalAvecRabais = (TextView) v.findViewById(R.id.totalApresRabaisTaxes);

        ServicePrixTotal servicePrixTotal = new ServicePrixTotal(ThompsonMainActivity.transactionCourante, ThompsonMainActivity.rabaisCourant);
        ThompsonMainActivity.transactionCourante = servicePrixTotal.AppliquerRabaisEtTaxes(ThompsonMainActivity.transactionCourante);

        prixApresRabaisTaxes = ThompsonMainActivity.transactionCourante.PrixApresRabaisEtTaxes;

        prixApresRabaisTaxes = machine.Arrondir(prixApresRabaisTaxes);

        totalAvecRabais.setText(String.format("%1$,.2f", prixApresRabaisTaxes) + "$");

        for (Money money : Money.values()) {
            ElementDeListe item = new ElementDeListe();
            item.nomArgent = money.pretty();
            item.quantitee = 0;
            item.money = money;
            adapter.add(item);
        }

        adapter.notifyDataSetChanged();

        Button calculerTotalBtn = (Button) v.findViewById(R.id.calculerTotalBtn);
        calculerTotalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculerTotal();
            }
        });

        Button payerFactureBtn = (Button) v.findViewById(R.id.payerLaTransaction);
        payerFactureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(calculerTotal() < prixApresRabaisTaxes) {
                    return;
                }

                //TODO Pop dialog Ticket de caisse:
                TicketCaisseDialog ticketCaisseDialog = new TicketCaisseDialog();
                ticketCaisseDialog.show(getFragmentManager(), "dialog");
            }
        });

        return v;
    }


    public static DialogFragment newInstance() {
        return new PayerDialog();
    }

    public double calculerTotal() {
        double total = 0;

        for(int i = 0; i < adapter.getCount(); i++) {
            ElementDeListe item = adapter.getItem(i);
            total += item.quantitee * item.money.value();
        }

        TextView totalCalcule = (TextView) this.getView().findViewById(R.id.totalPaye);
        totalCalcule.setText(String.format("%1$,.2f", total) + "$");
        return total;
    }

}
