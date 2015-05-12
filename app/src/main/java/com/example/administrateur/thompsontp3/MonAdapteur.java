package com.example.administrateur.thompsontp3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrateur.thompsontp3.Model.TransactionItem;

import java.util.List;

/**
 * Created by 1263287 on 2015-04-14.
 */
public class MonAdapteur extends ArrayAdapter<TransactionItem> {

    public MonAdapteur(Context context, List<TransactionItem> objects) {
        super(context, R.layout.list_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // transformer le fichier XML en objets Java : gonfler
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.list_item, parent, false);

        // le bouton plus se souvient qu'il correspond à cet item
        // seul moyen de distinguer tous les boutons +
        TransactionItem item = getItem(position);
        row.findViewById(R.id.item_ajout).setTag(item);
        row.findViewById(R.id.item_supprime).setTag(item);

        // remplir le layout avec les bonnes valeurs
        TextView prodTV = (TextView) row.findViewById(R.id.item_produit);
        prodTV.setText(item.achatItem.produit);

        TextView qtyTV = (TextView) row.findViewById(R.id.item_quantite);
        qtyTV.setText(String.valueOf(item.quantity));

        // attache l'item a la ligne au complet si on veut réagir à un clic partout
        row.setTag(item);
        return row;
    }
}
