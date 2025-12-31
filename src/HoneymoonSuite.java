/**
 * HoneymoonSuite.java
 * Özel balayı çiftleri için tasarlanmış lüks oda.
 */
public class HoneymoonSuite extends Room {

    public HoneymoonSuite(int roomNumber, double basePrice) {
        // Balayı odaları genelde 2 kişiliktir.
        // Resim yolu otomatik olarak 'honeymoon.jpg' seçilir.
        super(roomNumber, 2, basePrice, "src/img/honeymoon.jpg");
    }

    @Override
    public double calculatePrice() {
        // Balayı suiti, baz fiyatın 3 katıdır (Özel hizmet, jakuzi vb.)
        return getBasePrice() * 3.0;
    }

    @Override
    public String toString() {
        return "[BALAYI SUİTİ] " + super.toString() + " - Fiyat: " + calculatePrice() + " TL";
    }
}