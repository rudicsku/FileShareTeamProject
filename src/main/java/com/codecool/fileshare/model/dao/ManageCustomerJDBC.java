package com.codecool.fileshare.model.dao;

import com.codecool.fileshare.RowMapper;
import com.codecool.fileshare.model.Image;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManageCustomerJDBC implements ImageDao {
    private Connection con;

    RowMapper<Image> mapper = rs -> new Image(
            rs.getString("id"),
            rs.getString("content"),
            rs.getString("category"),
            rs.getString("extension"));

    public ManageCustomerJDBC(Connection con) {
        this.con = con;
    }

    @Override
    public List<Image> listAll() {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from image");
            List<Image> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapper.map(rs));
            }
            return results;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteByCategory(String category) {

    }

    @Override
    public String statistics() {
        return null;
    }

    @Override
    public void changeCategoryById(String id,String categoryToSet) {
        try (PreparedStatement stmt = con.prepareStatement("UPDATE image Set category=? WHERE id=?")) {
            stmt.setString(1,categoryToSet);
            stmt.setString(2,id);
            int numberOfRowsChanged = stmt.executeUpdate();
            if (numberOfRowsChanged==0){
                System.out.println("No such an Id");
            }else {
                System.out.printf("The image id of %s's category changed to %s",id,categoryToSet);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
