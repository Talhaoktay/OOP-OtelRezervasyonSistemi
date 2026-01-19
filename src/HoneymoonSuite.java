public class HoneymoonSuite extends Room {
    private double price;

    public HoneymoonSuite(int roomNumber, double price) {
        super(roomNumber, "img/honeymoon.jpg");
        this.price = price;
    }

    @Override
    public double calculatePrice(Season season) {
        // Balayı suiti normalde 3 katkı
        // Yazın: Fiyat * 3 * 2 (Yaz zammı)
        double multiplier = (season == Season.SUMMER) ? 2.0 : 1.0;
        return price * 3.0 * multiplier;
    }
}