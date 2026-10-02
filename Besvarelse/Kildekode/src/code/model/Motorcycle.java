// Klasseansvar: Representerer motorsykkel
package code.model;

public class Motorcycle extends Vehicle {
    private Boolean hasSidecar; // rette skrivefeil
    private int engineCapacity;
    private Boolean isModified;
    private int numOfWheels;

    // Konstruktør for Del 1:
    public Motorcycle(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, int scrapYardId, Boolean hasSidecar, int engineCapacity, Boolean isModified, int numOfWHeels) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId);
        this.hasSidecar = hasSidecar;
        this.engineCapacity = engineCapacity;
        this.isModified = isModified;
        this.numOfWheels = numOfWheels;
    }

    // Konstruktør for Del 2:
    public Motorcycle(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, Scrapyard scrapyard, Boolean hasSidecar, int engineCapacity, Boolean isModified, int numOfWheels) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapyard);
        this.hasSidecar = hasSidecar;
        this.engineCapacity = engineCapacity;
        this.isModified = isModified;
        this.numOfWheels = numOfWheels;
    }

    // Gettere
    public Boolean getHasSidecar() {
        return hasSidecar;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public Boolean getModified() {
        return isModified;
    }

    public int getNumOfWheels() {
        return numOfWheels;
    }

    // Tilpasset toString
    @Override
    public String toString() {
        return String.format(
                "VEHICLE: MOTORCYCLE\n" +
                "Specific info: Sidecar = %s | " +
                "Engine Capacity = %d | " +
                "Modified = %s | " +
                "Wheels = %d\n" + super.toString(),
                hasSidecar, engineCapacity, isModified, numOfWheels
        );
    }
}
