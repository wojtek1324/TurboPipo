package com.example.administrateur.thompsontp3.Repo;

import java.util.List;

/**
 * Created by 1263287 on 2015-04-27.
 */
public interface CRUD<T> {
    long save(T o);

    void saveMany(Iterable<T> list);

    void saveMany(T... list);

    T getById(Long p);

    List<T> getAll();

    void deleteOne(Long o);

    void deleteOne(T o);

    void deleteAll();
}
