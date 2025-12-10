/**
 * Room.java
 * Tüm oda tiplerinin atası olan soyut sınıf.
 * Resim yolu (imagePath) özelliği eklendi.
 */
public abstract class Room implements Reservable {
    private int roomNumber;
    private int capacity;
    private double basePrice;
    private boolean isBooked;
    private String imagePath; // <--- YENİ: Resim dosyasının yolu

    // Constructor (Kurucu Metod) güncellendi
    public Room(int roomNumber, int capacity, double basePrice, String imagePath) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
        this.isBooked = false;
    }

    // Her oda tipi fiyatı kendi hesaplar
    public abstract double calculatePrice();

    // --- Reservable Interface Metodları ---
    @Override
    public boolean isAvailable() {
        return !isBooked;
    }

    @Override
    public void reserve() {
        this.isBooked = true;
        System.out.println("Oda " + roomNumber + " rezerve edildi.");
    }

    @Override
    public void cancelReservation() {
        this.isBooked = false;
        System.out.println("Oda " + roomNumber + " iptal edildi.");
    }

    // --- Getter Metodları ---
    public int getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public double getBasePrice() { return basePrice; }
    public String getImagePath() { return imagePath; } // <--- GUI için gerekli
    public boolean isBooked() { return isBooked; }

    @Override
    public String toString() {
        return "Oda No: " + roomNumber + " (" + capacity + " Kişilik)";
    }
}