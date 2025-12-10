public class StandardRoom extends Room {

    public StandardRoom(int roomNumber, int capacity, double basePrice) {
        // "src/img/standard.jpg" yolunu buraya elle veriyoruz
        super(roomNumber, capacity, basePrice, "src/img/standard.jpg");
    }

    @Override
    public double calculatePrice() {
        return getBasePrice();
    }

    @Override
    public String toString() {
        return "[Standart] " + super.toString() + " - Fiyat: " + calculatePrice() + " TL";
    }
}
