public class DeluxeRoom extends Room {

    public DeluxeRoom(int roomNumber, int capacity, double basePrice) {
        // "src/img/deluxe.jpg" yolunu buraya elle veriyoruz
        super(roomNumber, capacity, basePrice, "src/img/deluxe.jpg");
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() * 1.5; // %50 daha pahalı
    }

    @Override
    public String toString() {
        return "[Deluxe]   " + super.toString() + " - Fiyat: " + calculatePrice() + " TL";
    }
}