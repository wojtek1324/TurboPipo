package com.example.administrateur.thompsontp3;

import com.example.administrateur.thompsontp3.Gui.AjouterProduitDialog;
import com.example.administrateur.thompsontp3.Gui.MonAdapteur;
import com.example.administrateur.thompsontp3.Gui.PayerDialog;
import com.example.administrateur.thompsontp3.Gui.UpdateRabaisDialog;
import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.example.administrateur.thompsontp3.Model.Transaction;
import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.example.administrateur.thompsontp3.Repo.CRUD;
import com.example.administrateur.thompsontp3.Repo.RepositoryProduitFichier;
import com.example.administrateur.thompsontp3.Repo.RepositoryRabais;
import com.example.administrateur.thompsontp3.Repo.RepositoryTransaction;
import com.example.administrateur.thompsontp3.Service.ServiceProduit;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.administrateur.thompsontp3.Model.Monayeur.*;


public class ThompsonMainActivity extends ActionBarActivity {
    //public static List<AchatItem> listItems = new ArrayList<>();

    CRUD<TransactionItem> listTransactions;

    MonAdapteur adapter;
    List<TransactionItem> items;

    public static CRUD<RabaisCourant> listRabais;
    public static CRUD<AchatItem> listItems;
    public static ServiceProduit serviceProduit;

    public static RabaisCourant rabaisCourant;

    public static Transaction transactionCourante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thompson_main);

        listItems = new RepositoryProduitFichier(this.getApplication());
        listTransactions = new RepositoryTransaction(this.getApplication());
        listRabais = new RepositoryRabais(this.getApplication());


        if(listRabais.getAll().isEmpty()) {
            listRabais.save(new RabaisCourant());
        }

        rabaisCourant = listRabais.getAll().get(0);

        items = new ArrayList<TransactionItem>();
        serviceProduit = new ServiceProduit(listItems);


        adapter = new MonAdapteur(ThompsonMainActivity.this, items);
        ListView list = (ListView) findViewById(R.id.custom_list);
        list.setAdapter(adapter);

        Button btnPayer = (Button) findViewById(R.id.payer);

        btnPayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adapter.getCount() == 0)
                    return;

                transactionCourante = new Transaction();
                transactionCourante.Transactions = items;
                DialogFragment ajouterDialogue = PayerDialog.newInstance();
                //ajouterDialogue.setContext(getApplicationContext());
                ajouterDialogue.show(getFragmentManager(), "dialog");
            }
        });

        Button scannerBtn = (Button) findViewById(R.id.scannerCamera);

        scannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scannerInterogator();
            }
        });

        Button ajouterProduit = (Button) findViewById(R.id.plusProduit);

        ajouterProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment ajouterDialogue = AjouterProduitDialog.newInstance();
                ajouterDialogue.show(getFragmentManager(), "dialog");
            }
        });


    }

    public void gererRabaisClick(View v)
    {
        DialogFragment ajouterDialogue = UpdateRabaisDialog.newInstance();
        ajouterDialogue.show(getFragmentManager(), "dialog");
    }

    public void scannerInterogator() {
        IntentIntegrator interogator = new IntentIntegrator(this);
        interogator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            serviceProduit.scan(items, scanResult.getContents());
            adapter.notifyDataSetChanged();
            calculerPrixTotal();
        }
    }


    public void plusUn(View v) {
        TransactionItem i = (TransactionItem) v.getTag();

        i.quantity++;

        adapter.notifyDataSetChanged();
        calculerPrixTotal();
    }

    public void moinUn(View v) {
        TransactionItem i = (TransactionItem) v.getTag();

        if (i.quantity == 1) {
            adapter.remove(i);
        } else {
            i.quantity--;
        }
        adapter.notifyDataSetChanged();
        calculerPrixTotal();
    }

    public void deuxPour1Click(View v) {
        TransactionItem i = (TransactionItem) v.getTag();
      //  List<RabaisCourant> listTestRabais = listRabais.getAll();

     //   int total = listTestRabais.size();

     //   int bla = listTestRabais.size();

        if(((CheckBox) v).isChecked()) {

            try {
                rabaisCourant.Ajouter2Pour1(i.achatItem);
                listRabais.save(rabaisCourant);

            } catch (RabaisCourant.ItemEstDejaEn2Pour1 itemEstDejaEn2Pour1) {

            }
            Toast.makeText(getApplicationContext(), "2 pour 1 maintenent actif", Toast.LENGTH_LONG).show();
        } else {
            try {
                rabaisCourant.Supprimer2pour1(i.achatItem);
                listRabais.save(rabaisCourant);
            } catch (RabaisCourant.ItemPasDansLaListe itemPasDansLaListe) {

            }
            Toast.makeText(getApplicationContext(), "2 pour 1 Inactif", Toast.LENGTH_LONG).show();
        }
      //  adapter.notifyDataSetChanged();
    }

    public void calculerPrixTotal() {
        double total = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            TransactionItem item = adapter.getItem(i);
            total += item.quantity * item.achatItem.prix;
        }
        ThompsonMachine register = new ThompsonMachine();
        total = register.Arrondir(total);

        TextView totalAchat = (TextView) findViewById(R.id.totalPrix);
        totalAchat.setText(String.format("%1$,.2f", total) + "$");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thompson_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.creerProduit) {
            if (listItems.getAll().size() >= 20) {
                listItems.deleteAll();
            }

            listItems.save(new AchatItem("tomates", 2.70, "7011043018498"));
            listItems.save(new AchatItem("patates", 2.50, "6797413659379"));
            listItems.save(new AchatItem("Arrichos", 1.25, "7651921095861"));
            listItems.save(new AchatItem("Pistaches", 2.15, "5729286863776"));
            listItems.save(new AchatItem("Bananes", 2.05, "6075631136316"));
            listItems.save(new AchatItem("Biscuits", 2.25, "6116088359160"));
            listItems.save(new AchatItem("chips", 2.35, "3298482123547"));
            listItems.save(new AchatItem("salsa", 2.45, "1629924473432"));
            listItems.save(new AchatItem("bagels", 2.55, "5648382418072"));
            listItems.save(new AchatItem("Raisins", 2.65, "5942916222891"));
            listItems.save(new AchatItem("Fèves", 2.75, "8038722591249"));
            listItems.save(new AchatItem("coucombres", 2.85, "1589477250587"));
            listItems.save(new AchatItem("Pain brun", 2.95, "7611473873003"));
            listItems.save(new AchatItem("Pain blanc", 3.05, "5088408786417"));
            listItems.save(new AchatItem("Porc", 2.10, "3481608318716"));
            listItems.save(new AchatItem("Boeuf", 2.15, "4407073486209"));
            listItems.save(new AchatItem("Saucisses", 2, "3593013272573"));
            listItems.save(new AchatItem("Broccoli", 2, "4233891349934"));
            listItems.save(new AchatItem("Jus d'orange", 2, "2657604046187"));
            listItems.save(new AchatItem("Jus de pomme", 2, "7224672377618"));

            return true;
        }

        if (id == R.id.viderProduits) {
            listItems.deleteAll();
            adapter.clear();
            adapter.notifyDataSetChanged();
            calculerPrixTotal();
            return true;
        }

        if (id == R.id.creerCommande) {

            if (listItems.getAll().size() <= 1) {
                return true;
            }
            Random random = new Random();

            adapter.clear();
            adapter.notifyDataSetChanged();
            calculerPrixTotal();


            int taille = listItems.getAll().size();
            long[] tab_idDispo = new long[taille];

            int indextab = 0;
            for (AchatItem itemAchat : listItems.getAll()) {
                tab_idDispo[indextab] = itemAchat.getId();
                indextab++;
            }


            ArrayList<Integer> lstRandomid = new ArrayList<Integer>();

            for (int index = 0; index < taille / 2; index++) {
                int idAlea;
                boolean contientDeja;
                do {
                    contientDeja = false;
                    idAlea = random.nextInt(taille);
                    if (lstRandomid.contains(idAlea)) {
                        contientDeja = true;
                    }

                } while (contientDeja);

                lstRandomid.add(idAlea);
                AchatItem itemRandom = listItems.getById(tab_idDispo[idAlea]);
                adapter.add(new TransactionItem(itemRandom, random.nextInt(10) + 1));
            }
            adapter.notifyDataSetChanged();
            calculerPrixTotal();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
