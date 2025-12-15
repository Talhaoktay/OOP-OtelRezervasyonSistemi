/**
 * Reservation.java
 * GÜNCELLENDİ: Oda servisi (serviceCost) özelliği eklendi.
 */
public class Reservation {
    private int id;
    private Customer customer;
    private Room room;
    private int nightCount;
    private ReservationStatus status;

    // YENİ: Ekstra harcamalar (Yemek, içecek vb.)
    private double serviceCost;

    public Reservation(int id, Customer customer, Room room, int nightCount) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.nightCount = nightCount;
        this.status = ReservationStatus.ACTIVE;
        this.serviceCost = 0.0; // Başlangıçta ekstra harcama yok
    }

    // --- YENİ: Oda Servisi Ekleme Metodu ---
    public void addServiceCost(double amount) {
        this.serviceCost += amount;
        System.out.println("Rezervasyon #" + id + " hesabına " + amount + " TL eklendi.");
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        this.room.cancelReservation();
    }

    // --- Getter Metodları ---

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public ReservationStatus getStatus() { return status; }

    // YENİ: Sadece servis ücretini görmek için
    public double getServiceCost() { return serviceCost; }

    // GÜNCELLENDİ: Toplam Fiyat = (Oda Fiyatı * Gün) + Ekstra Harcamalar
    public double getTotalPrice() {
        return (room.calculatePrice() * nightCount) + serviceCost;
    }

    @Override
    public String toString() {
        return "Rezervasyon #" + id + " - Toplam: " + getTotalPrice() + " TL";
    }
}