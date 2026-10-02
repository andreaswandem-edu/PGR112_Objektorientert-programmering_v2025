// Klasseansvar: Leser inn data fra vehicles.txt og konverterer det i tilhørende lister
package code.util;

import code.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    // Metode for innhenting av skraphandeler
    public List<Scrapyard> readScrapyards() throws FileNotFoundException {
        List<Scrapyard> scrapyards = new ArrayList<>();
        File file = new File("src/resources/vehicles.txt");
        Scanner scanner = new Scanner(file);

        // Finner ut av hvor mange skrapforhandlere den skal lese
        int amountOfScrapyards = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < amountOfScrapyards; i++) {
            int id  = Integer.parseInt(scanner.nextLine());
            String name = scanner.nextLine();
            String address = scanner.nextLine();
            String phoneNumber = scanner.nextLine();
            scrapyards.add(new Scrapyard(id, name, address, phoneNumber));

            scanner.nextLine(); // Hopper over: ---
        }
        return scrapyards;
    }

    // Metode for innhentig av alle kjøretøyene
    public List<Vehicle> readVehicles() throws FileNotFoundException {
        List<Vehicle> vehicles = new ArrayList<>();
        File file = new File("src/resources/vehicles.txt");
        Scanner scanner = new Scanner(file);

        // Denne brukes for å hoppe over skaphandlene
        int amoutOfScrapyards = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < amoutOfScrapyards; i++) {
            for (int j = 0; j < 5; j++) {
                scanner.nextLine();
            }
        }

        scanner.nextLine(); // Hopper over: antall kjøretøy

        while (scanner.hasNextLine()) {
            int vehicleId = Integer.parseInt(scanner.nextLine());
            int scrapYardId = Integer.parseInt(scanner.nextLine());
            String vehicleType = scanner.nextLine(); // Valg av List
            String brand = scanner.nextLine();
            String model = scanner.nextLine();
            int yearModel = Integer.parseInt(scanner.nextLine());
            String registrationNum = scanner.nextLine();
            String chassisNum = scanner.nextLine();
            Boolean drivable = Boolean.parseBoolean(scanner.nextLine());
            int numOfSellableWheels = Integer.parseInt(scanner.nextLine());

            switch(vehicleType) {
                case "FossilCar" -> {
                    String fuelType = scanner.nextLine();
                    int fuelAmount = Integer.parseInt(scanner.nextLine());

                    vehicles.add(new FossilCar(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId, fuelType, fuelAmount));
                }
                case "ElectricCar" -> {
                    int batteryCapacity = Integer.parseInt(scanner.nextLine());
                    int chargeLevel = Integer.parseInt(scanner.nextLine());

                    vehicles.add(new ElectricCar(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId, batteryCapacity, chargeLevel));
                }
                case "Motorcycle" -> {
                    Boolean hasSidecard = Boolean.parseBoolean(scanner.nextLine());
                    int engineCapacity = Integer.parseInt(scanner.nextLine());
                    Boolean isModified = Boolean.parseBoolean(scanner.nextLine());
                    int numOfWheels = Integer.parseInt(scanner.nextLine());

                    vehicles.add(new Motorcycle(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId, hasSidecard, engineCapacity, isModified, numOfWheels));
                }
            }
            scanner.nextLine(); // Hopper over: ---
        }
        return vehicles;
    }
}
