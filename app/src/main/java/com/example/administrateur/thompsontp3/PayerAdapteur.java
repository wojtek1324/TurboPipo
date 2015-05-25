package com.example.administrateur.thompsontp3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrateur.thompsontp3.Model.ElementDeListe;
import com.example.administrateur.thompsontp3.Model.Monayeur.Money;
import com.example.administrateur.thompsontp3.Model.TransactionItem;

import java.util.List;

/**
 * Created by 1263287 on 2015-05-25.
 */

public class PayerAdapteur extends ArrayAdapter<ElementDeListe> {



    public PayerAdapteur(Context context, List<ElementDeListe> objects) {
        super(context, R.layout.liste_argent_layout, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // transformer le fichier XML en objets Java : gonfler
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.liste_argent_layout, parent, false);


        ElementDeListe item = getItem(position);
        row.findViewById(R.id.ajout_piece).setTag(item);
        row.findViewById(R.id.supprimer_piece).setTag(item);

        TextView nomElement = (TextView) row.findViewById(R.id.typeArgent);
        nomElement.setText(item.nomArgent);

        TextView quantitee = (TextView) row.findViewById(R.id.quantiteeArgent);
        quantitee.setText(item.quantitee);


        row.setTag(item);
        return row;
    }
}
