package com.example.administrateur.thompsontp3.Repo;

/**
 * Created by 1263287 on 2015-04-27.
 */

import android.content.Context;

import com.example.administrateur.thompsontp3.Model.AchatItem;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RepositoryProduitFichier implements CRUD<AchatItem> {
    Gson gson = new Gson();

    Class<AchatItem> classe = AchatItem.class;

    Context context;

    public RepositoryProduitFichier(Context c){
        this.context = c;
        this.createStorage();
    }

    public List<AchatItem> getAll() {
        synchronized (classe) {
            List<AchatItem> res = new ArrayList<AchatItem>();
            File base = context.getFilesDir();
            File bases = new File(base.getPath() + "/achatItem/");
            for (File f : bases.listFiles()){
                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    AchatItem a = gson.fromJson(content, classe);
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

    public long save(AchatItem a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, "achatItem/" + a.getId()+".achatItem"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<AchatItem> list) {
        for (AchatItem p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(AchatItem... list) {
        for (AchatItem p : list ){
            this.save(p);
        }
    }

    @Override
    public AchatItem getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,"achatItem/" +id+".achatItem");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,"achatItem/" +id+".achatItem"));
                AchatItem a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void deleteOne(AchatItem a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base, "achatItem/" +a.getId()+".achatItem");
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
            for (AchatItem a : getAll()){
                if (a.getId() > max) max = a.getId();
            }
            return max+1;
        }
    }

    public void deleteStorage(){
        File base = context.getFilesDir();
        deleteFolder(base);
    }

    public void createStorage(){
        File base = context.getFilesDir();
        File bases = new File(base.getPath() + "/achatItem/");
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
