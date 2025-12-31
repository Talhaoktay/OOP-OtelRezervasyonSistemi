public class Reservation {
    private int id;
    private Customer customer;
    private Room room;
    private int nightCount;
    private ReservationStatus status;
    private double serviceCost;

    // YENİ: Ödeme Durumu
    private boolean isPaid;

    public Reservation(int id, Customer customer, Room room, int nightCount) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.nightCount = nightCount;
        this.status = ReservationStatus.ACTIVE;
        this.serviceCost = 0.0;
        this.isPaid = false; // Başlangıçta ödenmedi
    }

    public void addServiceCost(double amount) {
        this.serviceCost += amount;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELLED;
        this.room.cancelReservation();
    }

    // GÜNCELLENDİ: Ödeme yapılınca odayı boşa çıkar (Check-out mantığı)
    public void confirmPayment() {
        this.isPaid = true;
        this.status = ReservationStatus.COMPLETED; // Durumu "Tamamlandı" yap

        // Odayı tekrar "Müsait" hale getir
        this.room.cancelReservation();

        System.out.println("Ödeme alındı, oda boşa çıkarıldı.");
    }

    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public ReservationStatus getStatus() { return status; }
    public double getServiceCost() { return serviceCost; }

    // YENİ: Ödendi mi bilgisini ver
    public boolean isPaid() { return isPaid; }

    public double getTotalPrice() {
        return (room.calculatePrice() * nightCount) + serviceCost;
    }

    @Override
    public String toString() {
        return "Rezervasyon #" + id + " - Toplam: " + getTotalPrice() + " TL";
    }
}