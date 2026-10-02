// Klasseansvar: Utføre spørringer mot databasen
package code.db;

import code.model.*;
import code.util.PropertiesProvider;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScrapyardDao {
    private final MysqlDataSource scrapyardDS;

    // SQL spørringer
    private static final String ADD_ALL_SCRAPYARDS_SQL = "INSERT INTO Scrapyard (ScrapyardID, Name, Address, PhoneNumber) VALUES (?,?,?,?)";
    private static final String ADD_ALL_ELECTRIC_CARS_SQL = "INSERT INTO ElectricCar (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, BatteryCapacity, ChargeLevel) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String ADD_ALL_FOSSIL_CARS_SQL = "INSERT INTO FossilCar (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, FuelType, FuelAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String ADD_ALL_MOTORCYCLES_SQL = "INSERT INTO Motorcycle (VehicleID, Brand, Model, YearModel, RegistrationNumber, ChassisNumber, Driveable, NumberOfSellableWheels, ScrapyardID, HasSidecar, EngineCapacity, IsModified, NumberOfWheels) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_ALL_ELECTRIC_CARS_SQL = "SELECT * FROM ElectricCar JOIN Scrapyard ON ElectricCar.ScrapyardID = Scrapyard.ScrapyardID";
    private static final String GET_ALL_FOSSIL_CARS_SQL = "SELECT * FROM FossilCar JOIN Scrapyard ON FossilCar.ScrapyardID = Scrapyard.ScrapyardID";
    private static final String GET_ALL_MOTORCYCLES_SQL = "SELECT * FROM Motorcycle JOIN Scrapyard ON Motorcycle.ScrapyardID = Scrapyard.ScrapyardID";

    // Konstruktør: Setter opp MysqlDataSource
    public ScrapyardDao() {
        scrapyardDS = new MysqlDataSource();
        scrapyardDS.setServerName(PropertiesProvider.PROPS.getProperty("host"));
        scrapyardDS.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
        scrapyardDS.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
        scrapyardDS.setUser(PropertiesProvider.PROPS.getProperty("username"));
        scrapyardDS.setPassword(PropertiesProvider.PROPS.getProperty("password"));
    }

    // Metode for å legge inn Scrapyard
    public void addScrapyard(List<Scrapyard> scrapyards) throws SQLException {
        try (Connection conn = scrapyardDS.getConnection();
        PreparedStatement stmt = conn.prepareStatement(ADD_ALL_SCRAPYARDS_SQL)) {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                for (Scrapyard scrapyard : scrapyards) {
                    stmt.setInt(1, scrapyard.id());
                    stmt.setString(2, scrapyard.name());
                    stmt.setString(3, scrapyard.address());
                    stmt.setString(4, scrapyard.phoneNumber());

                    stmt.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    // Felles metode for å legge inn alle kjøretøyene
    public void addAllVehicles(List<ElectricCar> ec, List<FossilCar> fc, List<Motorcycle> mc) throws SQLException {
        try (Connection conn = scrapyardDS.getConnection()) {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                addElectricCars(conn, ec);
                addFossilCars(conn, fc);
                addMotorcycles(conn, mc);
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    // Hjelpemetode for addAllVehicles()
    private void addElectricCars(Connection conn, List<ElectricCar> electricCars) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(ADD_ALL_ELECTRIC_CARS_SQL))
        {
            for (ElectricCar electricCar : electricCars) {
                commonVehicleInstances(stmt, electricCar);
                stmt.setInt(10, electricCar.getBatteryCapacity());
                stmt.setInt(11, electricCar.getChargeLevel());

                stmt.executeUpdate();
            }
        }
    }

    // Hjelpemetode for addAllVehicles()
    private void addFossilCars(Connection conn, List<FossilCar> fossilCars) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(ADD_ALL_FOSSIL_CARS_SQL))
        {
            for (FossilCar fossilCar : fossilCars) {
                commonVehicleInstances(stmt, fossilCar);
                stmt.setString(10, fossilCar.getFuelType());
                stmt.setInt(11, fossilCar.getFuelAmount());

                stmt.executeUpdate();
            }
        }
    }

    // Hjelpemetode for addAllVehicles()
    private void addMotorcycles(Connection conn, List<Motorcycle> motorcycles) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(ADD_ALL_MOTORCYCLES_SQL))
        {
            for (Motorcycle motorcycle : motorcycles) {
                commonVehicleInstances(stmt, motorcycle);
                stmt.setBoolean(10, motorcycle.getHasSidecar());
                stmt.setInt(11, motorcycle.getEngineCapacity());
                stmt.setBoolean(12, motorcycle.getModified());
                stmt.setInt(13, motorcycle.getNumOfWheels());

                stmt.executeUpdate();
            }
        }
    }

    // Hjelpemetode for å minimere dupliserende kode
    private void commonVehicleInstances(PreparedStatement stmt, Vehicle vehicle) throws SQLException {
        stmt.setInt(1, vehicle.getVehicleId());
        stmt.setString(2, vehicle.getBrand());
        stmt.setString(3, vehicle.getModel());
        stmt.setInt(4, vehicle.getYearModel());
        stmt.setString(5, vehicle.getRegistrationNum());
        stmt.setString(6, vehicle.getChassisNum());
        stmt.setBoolean(7, vehicle.getDrivable());
        stmt.setInt(8, vehicle.getNumOfSellableWheels());
        stmt.setInt(9, vehicle.getScrapYardId());
    }

    // Metode for å hente ut alle funngjenstandene
    public List<Vehicle> getAllVehicles() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = scrapyardDS.getConnection()) {
            // FossilCar
            try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_FOSSIL_CARS_SQL);
                 ResultSet rs = stmt.executeQuery();)
            {
                while (rs.next()) {
                    // fossil bil
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNum = rs.getString("RegistrationNumber");
                    String chassisNum = rs.getString("ChassisNumber");
                    Boolean drivable = rs.getBoolean("Driveable");
                    int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");

                    String fuelType = rs.getString("FuelType");
                    int fuelAmount = rs.getInt("FuelAmount");

                    // scrapyard
                    int id = rs.getInt("ScrapyardID");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    String phoneNumber = rs.getString("PhoneNumber");
                    Scrapyard scrapyard = new Scrapyard(id, name, address, phoneNumber);

                    vehicles.add(new FossilCar(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numberOfSellableWheels, scrapyard, fuelType, fuelAmount));
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_ELECTRIC_CARS_SQL);
                 ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    // elektrisk bil
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNum = rs.getString("RegistrationNumber");
                    String chassisNum = rs.getString("ChassisNumber");
                    Boolean drivable = rs.getBoolean("Driveable");
                    int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");

                    int batteryCapacity = rs.getInt("BatteryCapacity");
                    int chargeLevel = rs.getInt("ChargeLevel");

                    // scrapyard
                    int id = rs.getInt("ScrapyardID");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    String phoneNumber = rs.getString("PhoneNumber");
                    Scrapyard scrapyard = new Scrapyard(id, name, address, phoneNumber);

                    vehicles.add(new ElectricCar(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numberOfSellableWheels, scrapyard, batteryCapacity, chargeLevel));
                }
            }
            try (PreparedStatement stmt = conn.prepareStatement(GET_ALL_MOTORCYCLES_SQL);
                 ResultSet rs = stmt.executeQuery())
            {
                while (rs.next()) {
                    // motorsykkel
                    int vehicleId = rs.getInt("VehicleID");
                    String brand = rs.getString("Brand");
                    String model = rs.getString("Model");
                    int yearModel = rs.getInt("YearModel");
                    String registrationNum = rs.getString("RegistrationNumber");
                    String chassisNum = rs.getString("ChassisNumber");
                    Boolean drivable = rs.getBoolean("Driveable");
                    int numberOfSellableWheels = rs.getInt("NumberOfSellableWheels");

                    Boolean hasSidecar = rs.getBoolean("HasSidecar");
                    int engineCapacity = rs.getInt("EngineCapacity");
                    Boolean isModified = rs.getBoolean("IsModified");
                    int numOfWheels = rs.getInt("NumberOfWheels");

                    // scrapyard
                    int id = rs.getInt("ScrapyardID");
                    String name = rs.getString("Name");
                    String address = rs.getString("Address");
                    String phoneNumber = rs.getString("PhoneNumber");
                    Scrapyard scrapyard = new Scrapyard(id, name, address, phoneNumber);

                    vehicles.add(new Motorcycle(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numberOfSellableWheels, scrapyard, hasSidecar, engineCapacity, isModified, numOfWheels));
                }
            }
         }
        return vehicles;
    }

}
