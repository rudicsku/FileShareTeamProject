package com.codecool.fileshare;

import com.codecool.fileshare.dao.ManageCustomerJDBC;
import org.postgresql.ds.PGSimpleDataSource;

import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class App {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Connection con = getConnection();
        ManageCustomerJDBC manageCustomerJDBC = new ManageCustomerJDBC(con);
        mainMenu(manageCustomerJDBC);


    }

    public static void mainMenu(ManageCustomerJDBC jdbc) {
        String inputString = "0";
        //TODO validate
        while (Integer.parseInt(inputString) < 1 || Integer.parseInt(inputString) > 6) {
            System.out.println("Select number between options:");
            System.out.println("1. List all files");
            System.out.println("2. Delete by Id");
            System.out.println("3. Delete by category");
            System.out.println("4. Statistics");
            System.out.println("5. Download by Id");
            System.out.println("6. Change category by id");
            inputString = input.nextLine();
        }

        switch (inputString) {
            case "1" -> {
                System.out.println("list");
                jdbc.listAll().forEach(System.out::println);//TODO
            }
            case "2" -> {
                System.out.println("Delete id");
                jdbc.deleteById("13141");
            } //TODO
            case "3" -> System.out.println("Delete category"); //TODO
            case "4" -> System.out.println("stat"); //TODO
            case "5" -> System.out.println("Download"); //TODO
            case "6" -> {
                changeCategoryById(jdbc);
            }
        }

    }
    private static void changeCategoryById(ManageCustomerJDBC jdbc){
        System.out.println("Dear Administrator give me the id -of the image which you want to change it's category");
        String id = getIdFromUser();
        System.out.println("Give me the desired category");
        String category = input.nextLine(); //todo nullcheck
        jdbc.changeCategoryById(id,category);

    }
    public static boolean inputCheck(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Connection getConnection() {
        var ds = new PGSimpleDataSource();
        ds.setURL(System.getenv("DB_URL"));
        ds.setUser(System.getenv("DB_USER"));
        ds.setPassword(System.getenv("DB_PASSWORD"));
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getIdFromUser(){
        String inputstr=null;
        while (inputstr==null){
            System.out.println("Give me an uuid of an image)");
            inputstr= input.nextLine();
            try {
                var uuid =UUID.fromString(inputstr);
            }catch (IllegalArgumentException e){
                System.out.println("Not a valid uuid");
                inputstr=null;
                continue;
            }
        }
        return  inputstr;
    }
}


