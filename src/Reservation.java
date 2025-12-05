/**
 * Reservation.java
 * Bir rezervasyonun tüm detaylarını (Kim, Nereye, Ne Zaman, Ne Kadar) tutan sınıf.
 */
public class Reservation {
    private int id;                 // Rezervasyonun benzersiz numarası (Takip etmek için)
    private Customer customer;      // Müşteri bilgisi
    private Room room;              // Kiralanan oda
    private int nightCount;         // Kaç gece kalınıcak?
    private double totalPrice;      // Toplam ödenmesi gereken tutar
    private ReservationStatus status; // Durum: ACTIVE veya CANCELLED

    // Kurucu Metod: Rezervasyon oluşturulurken çalışır
    public Reservation(int id, Customer customer, Room room, int nightCount) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.nightCount = nightCount;

        // Başlangıçta rezervasyon durumu AKTİF olur
        this.status = ReservationStatus.ACTIVE;

        // İş Mantığı: Toplam Fiyat = Odanın Gecelik Fiyatı x Gece Sayısı
        // room.calculatePrice() polimorfizm sayesinde Standart veya Deluxe fiyatını getirir.
        this.totalPrice = room.calculatePrice() * nightCount;
    }

    // Rezervasyonu iptal etme işlemi
    public void cancel() {
        // Durumu iptal olarak güncelle
        this.status = ReservationStatus.CANCELLED;

        // Odayı tekrar "Boş" (Rezerve değil) durumuna getiriyoruz
        room.cancelReservation();

        System.out.println("Rezervasyon #" + id + " iptal edildi. Oda " + room.getRoomNumber() + " boşa çıkarıldı.");
    }

    // --- Getter Metodları (Verilere ulaşmak için) ---

    public int getId() { return id; }

    public double getTotalPrice() { return totalPrice; }

    public Customer getCustomer() { return customer; }

    public Room getRoom() { return room; }

    // Rezervasyon bilgilerini toplu yazdırmak için
    @Override
    public String toString() {
        return "Rezervasyon NO: " + id +
                "\n - Müşteri: " + customer.getName() +
                "\n - Oda: " + room.toString() +
                "\n - Süre: " + nightCount + " Gece" +
                "\n - Toplam Tutar: " + totalPrice + " TL" +
                "\n - Durum: " + status;
    }
}