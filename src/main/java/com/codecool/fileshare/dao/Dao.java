package com.codecool.fileshare.dao;

import java.util.List;
import java.util.Map;

public interface Dao<T> {
    List<T> listAll();

    void deleteById(String id);

    void deleteByCategory(String category);

    Map<String, Integer> statistics();

//    File downloadById();

    void changeCategoryById(String id,String category);
}
