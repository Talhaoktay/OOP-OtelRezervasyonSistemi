/**
 * Room.java
 * Güncellendi: Artık Reservable arayüzünü (Interface) uyguluyor.
 */
// 1. "implements Reservable" ekliyoruz
public abstract class Room implements Reservable {
    private int roomNumber;
    private boolean isAvailable;
    private String imagePath;

    public Room(int roomNumber, String imagePath) {
        this.roomNumber = roomNumber;
        this.imagePath = imagePath;
        this.isAvailable = true;
    }

    // --- Reservable Interface Metotları ---

    // 2. isAvailable() zaten vardı, Interface ile uyumlu.
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    // 3. reserve() metodunu daha önce eklemiştik, bu da uyumlu.
    @Override
    public void reserve() {
        this.isAvailable = false;
    }

    // 4. cancelReservation() metodunu senin Interface'ine göre ekliyoruz.
    @Override
    public void cancelReservation() {
        this.isAvailable = true; // Odayı tekrar boş yap
    }
    // --------------------------------------

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getImagePath() {
        return imagePath;
    }

    // Bu metodu silmeyelim, Hotel sınıfı kullanabiliyor ama
    // doğrusu üstteki cancelReservation'ı kullanmaktır.
    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    // Fiyat hesaplama artık sezona göre değişiyor
    public abstract double calculatePrice(Season season);

    @Override
    public String toString() {
        return "Oda No: " + roomNumber;
    }
}