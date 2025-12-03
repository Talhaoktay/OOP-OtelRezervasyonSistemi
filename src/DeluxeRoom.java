/**
 * DeluxeRoom.java
 * Lüks odalar, standart fiyattan %60 daha pahalı.
 */
public class DeluxeRoom extends Room {

    public DeluxeRoom(int roomNumber, int capacity, double basePrice) {
        super(roomNumber, capacity, basePrice);
    }

    @Override
    public double calculatePrice() {
        // Lüks oda fiyatı = Taban Fiyat + %60 fazlası
        return getBasePrice() * 1.60;
    }

    @Override
    public String toString() {
        return "[Deluxe]   " + super.toString() + " - Fiyat: " + calculatePrice() + " TL";
    }
}