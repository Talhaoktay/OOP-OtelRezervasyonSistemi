import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Hotel.java
 * Otelin ana yönetim sınıfıdır.
 * Odaları ve rezervasyonları listeler, yeni kayıt oluşturur veya iptal eder.
 */
public class Hotel {
    private String name;
    private List<Room> rooms;
    private List<Reservation> reservations;
    private int reservationIdCounter; // Her rezervasyona benzersiz ID vermek için
    private Season currentSeason; // Otelin şu anki sezonu

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationIdCounter = 1; // ID'ler 1'den başlar
        this.currentSeason = Season.WINTER; // Varsayılan Kış
    }

    public void setSeason(Season season) {
        this.currentSeason = season;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    // Sisteme yeni oda ekleme
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Rezervasyon Yapma İşlemi
    public void makeReservation(int roomNumber, Customer customer, int days, Season season) {
        Room room = getRoomByNumber(roomNumber);

        if (room != null && room.isAvailable()) {
            // 1. Odayı rezerve et (Interface metodu)
            room.reserve();

            // 2. Yeni rezervasyon nesnesi oluştur (Sezon bilgisiyle)
            Reservation newRes = new Reservation(reservationIdCounter++, customer, room, days, season);
            reservations.add(newRes);

            System.out.println("Rezervasyon başarılı: " + customer.getName());
        } else {
            System.out.println("Hata: Oda bulunamadı veya dolu!");
        }
    }

    // Rezervasyon İptal İşlemi
    public void cancelReservation(int reservationId) {
        // Listede bu ID'ye sahip rezervasyonu bul
        Optional<Reservation> match = reservations.stream()
                .filter(r -> r.getId() == reservationId)
                .findFirst();

        if (match.isPresent()) {
            Reservation res = match.get();

            // --- ÖNEMLİ DEĞİŞİKLİK BURADA ---
            // Reservable interface'inin bize verdiği yetkiyle iptal ediyoruz.
            // Eskiden: res.getRoom().setAvailable(true); diyorduk.
            res.getRoom().cancelReservation();
            res.setStatus(ReservationStatus.CANCELLED);
            System.out.println("Rezervasyon iptal edildi: #" + reservationId);
        } else {
            System.out.println("Rezervasyon bulunamadı!");
        }
    }

    // Oda numarasına göre odayı bulup getiren yardımcı metot
    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null; // Oda yoksa null döner
    }

    // --- Getter Metotları ---
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public String getName() {
        return name;
    }
}