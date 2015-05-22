package com.example.administrateur.thompsontp3.Repo;

import android.content.Context;

import com.example.administrateur.thompsontp3.Model.RabaisCourant;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1263287 on 2015-05-22.
 */
public class RepositoryRabais implements CRUD<RabaisCourant>{

    Gson gson = new Gson();

    Class<RabaisCourant> classe = RabaisCourant.class;

    Context context;

    public RepositoryRabais(Context c){
        this.context = c;
        this.createStorage();
    }

    public List<RabaisCourant> getAll() {
        synchronized (classe) {
            List<RabaisCourant> res = new ArrayList<RabaisCourant>();
            File base = context.getFilesDir();
            for (File f : base.listFiles()){
                try{
                    //System.out.println("File is "+f.getName());
                    String content = FileUtils.readFileToString(f);
                    RabaisCourant a = gson.fromJson(content, classe);
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

    public long save(RabaisCourant a) {
        synchronized (classe) {
            // set the id
            if (a.getId() == null) a.setId(this.nextAvailableId());
            //
            String serialise = gson.toJson(a);
            File base = context.getFilesDir();
            try {
                FileUtils.writeStringToFile(new File(base, a.getId()+".RabaisCourant"), serialise);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a.getId();

        }
    }

    @Override
    public void saveMany(Iterable<RabaisCourant> list) {
        for (RabaisCourant p : list ){
            this.save(p);
        }
    }

    @Override
    public void saveMany(RabaisCourant... list) {
        for (RabaisCourant p : list ){
            this.save(p);
        }
    }

    @Override
    public RabaisCourant getById(Long id) {
        synchronized (classe) {
            String content;
            try {
                File base = context.getFilesDir();
                File f = new File(base,id+".RabaisCourant");
                if (!f.exists()) return null;
                content = FileUtils.readFileToString(new File(base,id+".RabaisCourant"));
                RabaisCourant a = gson.fromJson(content, classe);
                return a;
            } catch (IOException e) {
                return null;
            }
        }
    }

    public void deleteOne(RabaisCourant a) {
        synchronized (classe) {
            File base = context.getFilesDir();
            File f = new File(base, a.getId()+".RabaisCourant");
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
            for (RabaisCourant a : getAll()){
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
        base.mkdirs();
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
