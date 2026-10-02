// Klasseansvar: Interagere med brukeren og kalle på nødvendige metoder
package code;

import code.db.ScrapyardService;
import java.io.FileNotFoundException;
import code.model.Vehicle;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    private final ScrapyardService scrapyardService;

    public Program() {
        scrapyardService = new ScrapyardService();
    }

    public void run() throws SQLException, FileNotFoundException {
        setupHelper();

        String choice = "";
        while (!choice.equals("q")) {
            mainMenuMessage();

            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "1" -> getAllInfoAboutVehicles();
                case "2" -> getTotalAmountOfFuel();
                case "3" -> getInfoDrivableVehicles();
                case "4" -> getVehiclesOlderThan();
                case "q" -> quitProgram();
                default -> System.out.println("ERROR: Invalid choice");
            }
        }
    }

    private void setupHelper() throws SQLException, FileNotFoundException {
        System.out.println("╔══════════════════════╗");
        System.out.println("║    DATABASE SETUP    ║");
        System.out.println("╚══════════════════════╝");
        System.out.println("WARNING: Program will not work as intended if you have not transferred data");
        System.out.println("Do you wish to transfer data from file to database? Type: (Y / N)");
        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while(!(choice.equals("y") || choice.equals("n"))) {
            choice = scanner.nextLine().toLowerCase();
            switch (choice) {
                case "y" -> {
                    try {
                        initializeSetup();
                        System.out.println("SUCCESS: Data has been successfully transferred");
                    } catch (SQLIntegrityConstraintViolationException e) { // Ser etter duplikater
                        System.out.println("INFO: Data already exists. Starting program...");
                    }
                }
                case "n" -> System.out.println("OK: Starting program...");

                default -> System.out.println("Invalid choice. Please try again: (Y or N)");
            }
        }
    }

    // Hjelpemetode for setupHelper()
    private void initializeSetup() throws SQLException, FileNotFoundException {
        scrapyardService.initializeDatabase();
    }

    private void mainMenuMessage() {
        System.out.println("╔══════════════════════╗");
        System.out.println("║      MAIN MENU       ║");
        System.out.println("╚══════════════════════╝");
        System.out.println("1: See information about all vehicles");
        System.out.println("2: See information about the total amount of fuel in all the fossil vehicles");
        System.out.println("3: See information about all drivable vehicles");
        System.out.println("4: See information about vehicles older than input");
        System.out.println("Q: Quit program");
    }

    private void getAllInfoAboutVehicles() throws SQLException {
        System.out.println("Here is a complete list of all artifacts:");
        for (Vehicle vehicle : scrapyardService.getAllVehicles()) {
            System.out.println(vehicle);
        }
    }

    private void getTotalAmountOfFuel() throws SQLException {
        System.out.println("Total amount of fuel: " + scrapyardService.getTotalAmountOfFuel());
    }

    private void getInfoDrivableVehicles() throws SQLException {
        System.out.println("Here is a list of all drivable vehicles:");
        for (Vehicle vehicle : scrapyardService.getInfoDrivableVehicles()) {
            System.out.println(vehicle);
        }
    }

    // Valgfri funksjonalitet
    private void getVehiclesOlderThan() throws SQLException {
        System.out.println("Please enter a year");
        Scanner scanner = new Scanner(System.in);
        int year = 0;
        try {
            year = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Invalid input. Taking you back to the main menu...");
            return;
        }

        List<Vehicle> vehicles = scrapyardService.getCarsOlderThan(year);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private void quitProgram() {
        System.out.println("Understood, goodbye!");
    }
}
