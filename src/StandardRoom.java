/**
 * StandardRoom.java
 * Standart odalar için sınıf. Fiyatı sabittir (basePrice).
 */
public class StandardRoom extends Room {

    public StandardRoom(int roomNumber, int capacity, double basePrice) {
        // Room sınıfının kurucusunu çağırıyoruz
        super(roomNumber, capacity, basePrice);
    }

    @Override
    public double calculatePrice() {
        // Standart odada fiyat değişmez, direkt taban fiyat geçerlidir.
        return getBasePrice();
    }

    @Override
    public String toString() {
        return "[Standart] " + super.toString() + " - Fiyat: " + calculatePrice() + " TL";
    }
}
