package com.codecool.fileshare;

import com.codecool.fileshare.dao.ManageCustomerJDBC;
import com.codecool.fileshare.model.Image;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
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
        Boolean isrunning = true;
        //TODO validate
        while (isrunning && (!inputCheck(inputString) || Integer.parseInt(inputString) < 1 || Integer.parseInt(inputString) > 6)) {
            System.out.println("Select number between options:");
            System.out.println("1. List all files");
            System.out.println("2. Delete by Id");
            System.out.println("3. Delete by category");
            System.out.println("4. Statistics");
            System.out.println("5. Download by Id");
            System.out.println("6. Change category by id");
            inputString = input.nextLine();

            switch (inputString) {
                case "1" -> {
                    System.out.println("list");
                    for (Image i : jdbc.listAll()) {
                        String format = "%-40s %-60s %-7s %s\n";
                        System.out.printf(format, i.getUuid(), i.getBase64Code(), i.getCategory(), i.getExtension());
                    }
                    inputString = "0";
                }
                case "2" -> {
                    System.out.println("Please enter the ID of the file you want to delete:");
                    String idToDelete = getIdFromUser();
                    jdbc.deleteById(idToDelete);
                    inputString = "0";
                }
                case "3" -> {
                    String categoryToDelete = null;
                    while (categoryToDelete == null) {
                        System.out.println("Please enter the category of the files you want to delete:");
                        categoryToDelete = input.nextLine();
                        if (categoryToDelete.equals("q") || categoryToDelete.equals("Q")) {
                            Connection con = getConnection();
                            ManageCustomerJDBC manageCustomerJDBC = new ManageCustomerJDBC(con);
                            mainMenu(manageCustomerJDBC);
                        } else {
                            jdbc.deleteByCategory(categoryToDelete);
                        }
                    }
                    inputString = "0";
                }
                case "4" -> {
                    System.out.println("Statistics:");
                    Map<String, Integer> stats = jdbc.statistics();
                    //System.out.println("Category   Number_of_images");
                    System.out.printf("Category  Number_of_images by Category\n");
                    for (String s : stats.keySet()) {
                        int padding = 10 - s.length();
                        System.out.printf(s + "  %6s\n", stats.get(s));
                    }

                    System.out.println();
                    Map<String, Integer> stats2 = jdbc.statistics2();
                    System.out.printf("ImageType  Number_of_images by ImageType\n");
                    for (String s : stats2.keySet()) {
                        int padding = 10 - s.length();
                        System.out.printf(s + "  %7s\n", stats2.get(s));
                    }
                    inputString = "0";
                }
                case "5" -> {
                    System.out.println("Download");
                    inputString = "0";
                }
                case "6" -> {
                    System.out.println("Please enter the ID of the files you want to change the category of:");
                    String idToChange = getIdFromUser();
                    String categoryToChange = null;
                    while (categoryToChange == null) {
                        System.out.println("Please enter the new category of the file:");
                        categoryToChange = input.nextLine();
                        if (categoryToChange.equals("q") || categoryToChange.equals("Q")) {
                            Connection con = getConnection();
                            ManageCustomerJDBC manageCustomerJDBC = new ManageCustomerJDBC(con);
                            mainMenu(manageCustomerJDBC);
                        } else {
                            jdbc.changeCategoryById(idToChange, categoryToChange);
                        }
                    }
                    inputString = "0";
                }
                case "q", "Q" -> {
                    System.out.println("Program exiting...");
                    System.out.println("Bye!");
                    isrunning = false;
                    System.exit(0);
                }
                default -> {
                    System.out.println("Please enter a valid command!");
                    inputString = "0";
                }
            }
        }
    }

    private static void changeCategoryById(ManageCustomerJDBC jdbc) {
        System.out.println("Dear Administrator give me the id -of the image which you want to change it's category");
        String id = getIdFromUser();
        String category = null;
        while (category == null) {
            System.out.println("Give me the desired category");
            category = input.nextLine();
        }
        jdbc.changeCategoryById(id, category);

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
        Connection con = getConnection();
        ManageCustomerJDBC manageCustomerJDBC = new ManageCustomerJDBC(con);

        String inputstr = null;
        while (inputstr == null) {
            System.out.println("Give me an uuid of an image)");
            inputstr = input.nextLine();
            try {
                if (inputstr.equals("q") || inputstr.equals("Q")) {
                    mainMenu(manageCustomerJDBC);
                } else {
                    var uuid = UUID.fromString(inputstr);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Not a valid uuid");
                inputstr = null;
            }
        }
        return inputstr;
    }
}


