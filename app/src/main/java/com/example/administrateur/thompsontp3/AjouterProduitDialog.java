package com.example.administrateur.thompsontp3;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Service.ServiceProduit;

/**
 * Created by 1263287 on 2015-04-24.
 */
public class AjouterProduitDialog extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // mise en place de l'interface a partir du XML
        final View v = inflater.inflate(R.layout.ajouter_produit_layout, container, false);

        // recuperation de chaque element d'interface
        final Button button = (Button)v.findViewById(R.id.add);
        final EditText code = (EditText) v.findViewById(R.id.code);
        final EditText nom = (EditText) v.findViewById(R.id.nom);

        final EditText prixProduit = (EditText) v.findViewById(R.id.prixProduit);
        final CheckBox taxable = (CheckBox) v.findViewById(R.id.taxableChk);
        final CheckBox deuxPourUn = (CheckBox) v.findViewById(R.id.est2Pour1);




        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(contientCodeBarre(code.getText().toString()) || code.getText().length() == 0 || prixProduit.getText().length() == 0)
                {

                    return;
                }

                AchatItem nouveauItem = new AchatItem();
                nouveauItem.codeBarre = code.getText().toString();
                nouveauItem.prix = Double.parseDouble(prixProduit.getText().toString());
                nouveauItem.produit = nom.getText().toString();
                nouveauItem.taxable = taxable.isChecked();

                if(deuxPourUn.isChecked())
                {
                    try {
                        ThompsonMainActivity.rabaisCourant.Ajouter2Pour1(nouveauItem);
                        ThompsonMainActivity.listRabais.save(ThompsonMainActivity.rabaisCourant);
                    } catch (RabaisCourant.ItemEstDejaEn2Pour1 itemEstDejaEn2Pour1) {

                    }

                }

                try {
                    ThompsonMainActivity.serviceProduit.AjouterProduit(nouveauItem);
                    AjouterProduitDialog.this.dismiss();
                } catch (ServiceProduit.SameBarcodeException e) {
                    Toast.makeText(getActivity(), "Il existe deja un produit avec ce code barre", Toast.LENGTH_LONG).show();
            }

            }
        });

        return v;
    }

    private boolean contientCodeBarre(String codeBarre)
    {
        for(AchatItem item : ThompsonMainActivity.listItems.getAll())
        {
            if(item.codeBarre.equals(codeBarre))
            {
                return true;
            }
        }
        return false;
    }

    public static DialogFragment newInstance() {
        return new AjouterProduitDialog();
    }

}
