// Klasseansvar: Starte programmet og håndtere eventuelle unntak
package code;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new Program().run();
        } catch (SQLException e) {
            System.out.println("SQL-Exception caught: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("File-not-found Exception caught: " + e.getMessage());
        }
    }
}
