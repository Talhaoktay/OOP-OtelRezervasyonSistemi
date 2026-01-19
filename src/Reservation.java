/**
 * Reservation.java
 * Bir müşterinin odayı kiralamasını temsil eden sınıf.
 * Rezervasyon durumunu, ödeme bilgisini ve ekstra harcamaları tutar.
 */
public class Reservation {
    private int id;
    private Customer customer;
    private Room room;
    private int nightCount;
    private double serviceCost; // Oda servisi vb. ekstra harcamalar
    private boolean isPaid;

    // --- YENİ EKLENEN ALAN ---
    private ReservationStatus status;

    // --- YENİ ALAN ---
    private Season season;

    public Reservation(int id, Customer customer, Room room, int nightCount, Season season) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.nightCount = nightCount;
        this.season = season; // Sezon bilgisini sakla
        this.serviceCost = 0.0;
        this.isPaid = false;

        // --- YENİ: Varsayılan olarak rezervasyon AKTİF başlar ---
        this.status = ReservationStatus.ACTIVE;
    }

    // Ekstra hizmet ekleme (Yemek, SPA vs.)
    public void addServiceCost(double cost) {
        this.serviceCost += cost;
    }

    // Toplam fiyat hesabı: (Günlük Fiyat * Gün Sayısı) + Ekstralar
    public double getTotalPrice() {
        // Artık odanın fiyatını, rezervasyonun yapıldığı sezona göre istiyoruz
        return (room.calculatePrice(season) * nightCount) + serviceCost;
    }

    public void confirmPayment() {
        this.isPaid = true;
    }

    // --- GETTER METOTLARI ---
    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Room getRoom() {
        return room;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public boolean isPaid() {
        return isPaid;
    }

    // --- EKSİK OLAN VE HATAYI ÇÖZEN METOTLAR ---
    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // İptal kolaylığı için yardımcı metot (İsteğe bağlı)
    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        this.room.setAvailable(true); // Odayı boşa çıkar
    }

    @Override
    public String toString() {
        return "Rezervasyon #" + id + " - " + customer.getName() +
                " (" + status + ")";
    }
}