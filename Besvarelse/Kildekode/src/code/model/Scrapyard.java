// Klasseansvar: Representerer skraphandeler
package code.model;

public record Scrapyard(int id, String name, String address, String phoneNumber) {


    // Tilpasset toString
    @Override
    public String toString() {
        return String.format(
                "Scrapyard info: Id = %d | " +
                "Name = %s | " +
                "Address = %s | " +
                "Phonenumber = %s",
                id, name, address, phoneNumber
        );
    }
}
