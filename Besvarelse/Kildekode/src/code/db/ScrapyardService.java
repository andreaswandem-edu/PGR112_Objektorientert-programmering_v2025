// Klasseansvar: Kobler Program og DAO sammen, samt logikk
package code.db;

import code.model.*;
import code.util.FileHandler;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScrapyardService {
    private final ScrapyardDao scrapyardDao;

    public ScrapyardService() {
        scrapyardDao = new ScrapyardDao();
    }

    // Metode for å sende listene fra FileHandler til ScrapyardDao
    public void initializeDatabase() throws SQLException, FileNotFoundException {
        FileHandler fileHandler = new FileHandler();
        List<Scrapyard> scrapyards = fileHandler.readScrapyards();
        List<Vehicle> vehicles = fileHandler.readVehicles();

        List<ElectricCar> electricCars = new ArrayList<>();
        List<FossilCar> fossilCars = new ArrayList<>();
        List<Motorcycle> motorcycles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof ElectricCar ec) {
                electricCars.add(ec);
            } else if (vehicle instanceof FossilCar fc) {
                fossilCars.add(fc);
            } else if (vehicle instanceof Motorcycle mc) {
                motorcycles.add(mc);
            }
        }

        scrapyardDao.addScrapyard(scrapyards);
        scrapyardDao.addAllVehicles(electricCars, fossilCars, motorcycles);
    }

    // Se informasjon om alle kjøretøy
    public List<Vehicle> getAllVehicles() throws SQLException {
        return scrapyardDao.getAllVehicles();
    }

    // Totalt antall fuel
    public int getTotalAmountOfFuel() throws SQLException {
        List<Vehicle> vehicles = scrapyardDao.getAllVehicles();
        int totalAmount = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof FossilCar fc) {
                totalAmount += fc.getFuelAmount();
            }
        }
        return totalAmount;
    }

    // Drivable vehicles
    public List<Vehicle> getInfoDrivableVehicles() throws SQLException {
        return scrapyardDao.getAllVehicles().stream()
                .filter(Vehicle::getDrivable)
                .toList();
    }

    //
    public List<Vehicle> getCarsOlderThan(int num) throws SQLException {
        return scrapyardDao.getAllVehicles().stream()
                .filter(v -> v.getYearModel() > num)
                .toList();
    }

}
