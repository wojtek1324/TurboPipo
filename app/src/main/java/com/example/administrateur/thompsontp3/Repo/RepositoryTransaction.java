package com.example.administrateur.thompsontp3.Repo;

import android.content.Context;

import com.example.administrateur.thompsontp3.Model.TransactionItem;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-04-27.
 */
public class RepositoryTransaction implements CRUD<TransactionItem> {
    Gson gson = new Gson();

    Class<TransactionItem> classe = TransactionItem.class;

    Context context;

    public RepositoryTransaction(Context c){
        this.context = c;
        this.createStorage();
    }

    public List<TransactionItem> getAll() {
        synchronized (classe) {
            List<TransactionItem> res = new ArrayList<TransactionItem>();
            File base = context.getFilesDir();
            File bases = new File(base.getPath() + "/Transaction/");
            for (File f : bases.listFiles()){
                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    TransactionItem a = gson.fromJson(content, classe);
                    res.add(a);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            return res;
        }
    }

    @Override
    public void deleteOne(Long o) {
        this.deleteOne(this.getById(o));
    }

    public long save(TransactionItem a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base,"Transaction/" + a.getId()+".TransactionItem"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<TransactionItem> list) {
        for (TransactionItem p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(TransactionItem... list) {
        for (TransactionItem p : list ){
            this.save(p);
        }
    }

    @Override
    public TransactionItem getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,"Transaction/" +id+".TransactionItem");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,"Transaction/" +id+".TransactionItem"));
                TransactionItem a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void deleteOne(TransactionItem a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base,"Transaction/" + a.getId()+".TransactionItem");
            f.delete();
        }
    }

    public void deleteAll() {
        deleteStorage();
        createStorage();
    }

    // autre methodes hors acces aux donnees pour la gestion.

    private long nextAvailableId(){
        synchronized (classe) {
            long max = 0;
            for (TransactionItem a : getAll()){
                if (a.getId() > max) max = a.getId();
            }
            return max+1;
        }
    }

    public void deleteStorage(){
        File base = context.getFilesDir();
        File bases = new File(base.getPath() + "/Transaction/");
        deleteFolder(bases);
    }

    public void createStorage(){
        File base = context.getFilesDir();
        File bases = new File(base.getPath() + "/Transaction/");
        bases.mkdirs();
    }

    private static void deleteFolder(File folder) {
        try{File[] files = folder.listFiles();
            if(files!=null) {
                for(File f: files) {
                    if(f.isDirectory())
                        deleteFolder(f);
                    else
                        f.delete();
                }
            }
            folder.delete();
        }catch(Exception e){}
    }
}
