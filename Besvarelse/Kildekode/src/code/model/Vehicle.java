// Klasseansvar: Superklassen for alle kjøretøyene
package code.model;

public abstract class Vehicle {
    private int vehicleId;
    private String brand;
    private String model;
    private int yearModel;
    private String registrationNum;
    private String chassisNum;
    private Boolean drivable;
    private int numOfSellableWheels;
    private int scrapYardId;

    private Scrapyard scrapyard;


    // Konstruktør for Del 1:
    public Vehicle(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, int scrapYardId) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.yearModel = yearModel;
        this.registrationNum = registrationNum;
        this.chassisNum = chassisNum;
        this.drivable = drivable;
        this.numOfSellableWheels = numOfSellableWheels;
        this.scrapYardId = scrapYardId;
    }

    // Konstruktør for Del 2: (objektreferanse for Scrapyard)
    public Vehicle(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, Scrapyard scrapyard) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.yearModel = yearModel;
        this.registrationNum = registrationNum;
        this.chassisNum = chassisNum;
        this.drivable = drivable;
        this.numOfSellableWheels = numOfSellableWheels;
        this.scrapyard = scrapyard;
    }

    // Gettere
    public int getVehicleId() {
        return vehicleId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYearModel() {
        return yearModel;
    }

    public String getRegistrationNum() {
        return registrationNum;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public Boolean getDrivable() {
        return drivable;
    }

    public int getNumOfSellableWheels() {
        return numOfSellableWheels;
    }

    public int getScrapYardId() {
        return scrapYardId;
    }

    public Scrapyard getScrapyard() {
        return scrapyard;
    }

    // Tilpasset toString
    @Override
    public String toString() {
        return String.format(
                "Vehicle Id: %d\n" +
                "Brand: %s\n" +
                "Model: %s\n" +
                "Year Model: %d\n" +
                "Registration number: %s\n" +
                "Chassis number: %s\n" +
                "Vehicle is drivable: %s\n" +
                "Number of sellable wheels: %d\n" +
                "%s\n",
                vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapyard
        );
    }
}
