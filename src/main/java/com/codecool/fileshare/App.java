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
/*        System.out.println(manageCustomerJDBC.listAll());
        manageCustomerJDBC.deleteById("1");
        System.out.println(manageCustomerJDBC.listAll());
        manageCustomerJDBC.deleteByCategory("dog");
        System.out.println(manageCustomerJDBC.listAll());*/
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
                System.out.println("List of all files:");
                jdbc.listAll().forEach(System.out::println);
            }
            case "2" -> {
                System.out.println("Please enter the ID of the file you want to delete:");
                String idToDelete = getIdFromUser();
                jdbc.deleteById(idToDelete);
            }
            case "3" -> {
                String categoryToDelete = null;
                while (categoryToDelete == null) {
                    System.out.println("Please enter the category of the files you want to delete:");
                    categoryToDelete = input.nextLine();
                    jdbc.deleteByCategory(categoryToDelete);
                }
            }
            case "4" -> {
                System.out.println("Statistics:");
                jdbc.statistics().forEach(System.out::printf);
            }
            case "5" -> {
                System.out.println("Download");
            }
            case "6" -> {
                System.out.println("Please enter the ID of the files you want to change the category of:");
                String idToChange = getIdFromUser();
                String categoryToChange = null;
                while (categoryToChange == null) {
                    System.out.println("Please enter the new category of the file:");
                    categoryToChange = input.nextLine();
                }
                jdbc.changeCategoryById(idToChange, categoryToChange);
            }
        }

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

    private static String getIdFromUser() {
        String inputstr = null;
        while (inputstr == null) {
            System.out.println("Give me an uuid of an image)");
            inputstr = input.nextLine();
            try {
                var uuid = UUID.fromString(inputstr);
            } catch (IllegalArgumentException e) {
                System.out.println("Not a valid uuid");
                inputstr = null;
                continue;
            }
        }
        return inputstr;
    }
}