public class DeluxeRoom extends Room {
    private int bedCount;
    private double price;

    public DeluxeRoom(int roomNumber, int bedCount, double price) {
        super(roomNumber, "img/deluxe.jpg");
        this.bedCount = bedCount;
        this.price = price;
    }

    @Override
    public double calculatePrice(Season season) {
        // Normalde Deluxe %20 pahalı.
        // Yazın 2 katına çıkınca yine %20 ekstra korunuyor.
        double baseRate = (season == Season.SUMMER) ? price * 2.0 : price;
        return baseRate * 1.20;
    }
}