// Klasseansvar: Representerer el-bil
package code.model;

public class ElectricCar extends Vehicle {
    private int batteryCapacity;
    private int chargeLevel;

    // Konstruktør for Del 1:
    public ElectricCar(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, int scrapYardId, int batteryCapacity, int chargeLevel) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapYardId);
        this.batteryCapacity = batteryCapacity;
        this.chargeLevel = chargeLevel;
    }

    // Konstruktør for Del 2:
    public ElectricCar(int vehicleId, String brand, String model, int yearModel, String registrationNum, String chassisNum, Boolean drivable, int numOfSellableWheels, Scrapyard scrapyard, int batteryCapacity, int chargeLevel) {
        super(vehicleId, brand, model, yearModel, registrationNum, chassisNum, drivable, numOfSellableWheels, scrapyard);
        this.batteryCapacity = batteryCapacity;
        this.chargeLevel = chargeLevel;
    }

    // Gettere
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public int getChargeLevel() {
        return chargeLevel;
    }

    // Tilpasset toString
    @Override
    public String toString() {
        return String.format(
                "VEHICLE: ELECTRIC-CAR\n" +
                        "Specific info: Batterycapacity = %d | " +
                        "Chargelevel = %d\n" + super.toString(),
                batteryCapacity, chargeLevel
        );
    }
}
