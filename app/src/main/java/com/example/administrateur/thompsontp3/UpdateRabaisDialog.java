package com.example.administrateur.thompsontp3;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrateur.thompsontp3.Model.AchatItem;

/**
 * Created by 1263287 on 2015-05-25.
 */
public class UpdateRabaisDialog extends DialogFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.gestion_rabais_layout, container, false);

        final Button button = (Button) v.findViewById(R.id.updateRabais);
        final EditText code = (EditText) v.findViewById(R.id.codeBarrePG);
        final EditText seuilPG = (EditText) v.findViewById(R.id.seuilPG);
        final EditText seuilPasTaxes = (EditText) v.findViewById(R.id.seuilPasDeTaxes);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((code.getText() == null && seuilPG.getText() != null)||
                        (code.getText() != null && seuilPG.getText() == null)) {
                    Toast.makeText(getActivity().getBaseContext(), "@string/toast_2champrequis", Toast.LENGTH_LONG).show();
                    return;
                }

                if(seuilPasTaxes.getText().length() != 0 ) {
                    double seuilPTParse = Double.parseDouble(seuilPasTaxes.getText().toString());
                    if(seuilPTParse < 0) { Toast.makeText(getActivity().getBaseContext(), "@string/toast_seuilPositif", Toast.LENGTH_LONG).show();
                        return; }

                    //Seuil pas de taxes:
                    ThompsonMainActivity.rabaisCourant.setSeuilPasDeTaxes(seuilPTParse);

                } else {
                    ThompsonMainActivity.rabaisCourant.setSeuilPasDeTaxes(Double.MAX_VALUE);
                }

                if(seuilPG.getText().length() != 0 && itemAyantCeCodeBarre(code.getText().toString()) != null) {
                    double seuilPGPrase = Double.parseDouble(seuilPG.getText().toString());
                    if(seuilPGPrase <= 0) { Toast.makeText(getActivity().getBaseContext(), "@string/toast_seuilPositif", Toast.LENGTH_LONG).show();
                        return; }

                    //Produits gratuits:
                    ThompsonMainActivity.rabaisCourant.setProduitGratuit(itemAyantCeCodeBarre(code.getText().toString()));
                    ThompsonMainActivity.rabaisCourant.setTranchesProduitGratuit(seuilPGPrase);

                } else {
                    ThompsonMainActivity.rabaisCourant.setProduitGratuit(null);
                    ThompsonMainActivity.rabaisCourant.setTranchesProduitGratuit(Double.MAX_VALUE);
                }

                ThompsonMainActivity.listRabais.save(ThompsonMainActivity.rabaisCourant);

                UpdateRabaisDialog.this.dismiss();
            }
        });

        return v;
    }



    private AchatItem itemAyantCeCodeBarre(String codeBarre)
    {
        for(AchatItem item : ThompsonMainActivity.listItems.getAll())
        {
            if(item.codeBarre.equals(codeBarre))
            {
                return item;
            }
        }
        return null;
    }


    public static DialogFragment newInstance() {
        return new UpdateRabaisDialog();
    }

}
