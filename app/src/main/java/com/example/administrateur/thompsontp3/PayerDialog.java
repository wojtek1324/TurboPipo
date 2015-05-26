package com.example.administrateur.thompsontp3;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrateur.thompsontp3.Model.ElementDeListe;
import com.example.administrateur.thompsontp3.Model.Monayeur.Money;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-05-25.
 */
public class PayerDialog extends DialogFragment {

    PayerAdapteur adapter;
    List<ElementDeListe> billets;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.payer_layout, container, false);
        // asd asda oisdh asio haiodh ioah hasdfio hadsiof hash oih asio hfdoih aio fshd foihas iodfhao sidfh oasi dfhio
        // asd asda oisdh asio haiodh ioah hasdfio hadsiof hash oih asio hfdoih aio fshd foihas iodfhao sidfh oasi dfhio
        // asd asda oisdh asio haiodh ioah hasdfio hadsiof hash oih asio hfdoih aio fshd foihas iodfhao sidfh oasi dfhio
        billets = new ArrayList<ElementDeListe>();
        adapter = new PayerAdapteur(getActivity(), billets);
        ListView list = (ListView) v.findViewById(R.id.liste_billets);
        list.setAdapter(adapter);


        for (Money money : Money.values()) {
            ElementDeListe item = new ElementDeListe();
            item.nomArgent = money.name();
            item.quantitee = 0;
            item.valeurArgent = money.value();
            adapter.add(item);

        }

        adapter.notifyDataSetChanged();

        return v;
    }


    public static DialogFragment newInstance() {
        return new PayerDialog();
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
