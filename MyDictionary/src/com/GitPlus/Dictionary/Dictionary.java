package com.GitPlus.Dictionary;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;


public class Dictionary {

    public static void main(String[] args) {

        try
        {
            new RegisteredUsers();
            // create a mysql database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            String myUrl = "jdbc:mysql://localhost:3306/dictionary_db";
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "root", "");

            //Creating dictionary
            Scanner scannerReply = new Scanner(System.in);
            Scanner scannerWord = new Scanner(System.in);
            Scanner scannerMeaning = new Scanner(System.in);
            Scanner scannerDelete = new Scanner(System.in);
            Scanner scannerSearch = new Scanner(System.in);

            System.out.println("WELCOME TO MY DICTIONARY");

            System.out.println();
            System.out.println("What do you wanna do?");

            System.out.println("1. Read through dictionary \n" +
                    "2. Search for a word from the dictionary \n" +
                    "3. Delete a word from the dictionary \n" +
                    "4. Update this dictionary \n");

            System.out.print("Reply: ");
            int reply = scannerReply.nextInt();

            if (reply == 4){
                System.out.print("What word do you want to add to this dictionary? ");
                String word = scannerWord.nextLine();
                System.out.print("What's the meaning of that word? ");
                String meaning = scannerMeaning.nextLine();

                String query = "insert into dictionary_table (words, meanings)"
                        + " values (?, ?)";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString (1, word);
                preparedStmt.setString (2, meaning);
                System.out.println("Dictionary has been updated!");
                preparedStmt.execute();
            }

            else if (reply == 1) {
                //Select data from database
                String querySelect = "SELECT * FROM dictionary_table";
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(querySelect);

                System.out.println("WORDS AND THEIR MEANINGS");
                while (resultSet.next()){
                    String words = resultSet.getString("words");
                    String meanings = resultSet.getString("meanings");
                    System.out.println(words + " <==> " + meanings);
                }
            }

            else if (reply == 3) {
                try{
                    String queryDelete = "DELETE FROM dictionary_table WHERE words = ?";
                    PreparedStatement preparedStmt = conn.prepareStatement(queryDelete);
                    System.out.println("Which word do you wanna delete? ");
                    String wordDelete = scannerDelete.nextLine();
                    preparedStmt.setString (1, wordDelete);
                    System.out.println("One item has been deleted from dictionary successfully!");
                    preparedStmt.executeUpdate();
                }
                catch (Exception e){
                    System.err.println("Error!");
                }
            }

            else if (reply == 2) {
                System.out.println("Which word do you wanna search for? ");
                String word = scannerSearch.nextLine();
                String querySearch = "SELECT * FROM dictionary_table WHERE words = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(querySearch);
                preparedStmt.setString (1, word);
                ResultSet resultSearch = preparedStmt.executeQuery();

                System.out.println("SEARCH RESULTS");
                while (resultSearch.next()){

                    System.out.println(resultSearch.getString("words") +
                            " <==> " + resultSearch.getString("meanings"));
                }

                preparedStmt.execute();


            }
            else{
                System.out.println("Wrong input!!!");
            }


            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
