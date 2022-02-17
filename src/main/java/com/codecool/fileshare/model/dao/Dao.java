package com.codecool.fileshare.model.dao;

import java.util.List;

public interface Dao<T> {
    List<T> listAll();

    void deleteById(String id);

    void deleteByCategory(String category);

    String statistics();

//    File downloadById();

    void changeCategoryById(String id,String category);
}
