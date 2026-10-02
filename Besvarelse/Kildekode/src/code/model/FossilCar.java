// Klasseansvar: Representerer fossilbil
package code.model;

public class FossilCar extends Vehicle {
    private String fuelType;
    private int fuelAmount;


    // Konstruktør for Del 1:
    public FossilCar(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, int scrapYardId, String fuelType, int fuelAmount) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    // Konstruktør for Del 2:
    public FossilCar(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, Scrapyard scrapyard, String fuelType, int fuelAmount) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapyard);
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    // Gettere
    public String getFuelType() {
        return fuelType;
    }

    public int getFuelAmount() {
        return fuelAmount;
    }

    // Tilpasset toString
    @Override
    public String toString() {
        return String.format(
                "VEHICLE: FOSSIL-CAR\n" +
                "Specific info: Fueltype = %s | " +
                "Fuel amount = %d\n" + super.toString(),
                 fuelType, fuelAmount
        );
    }
}
