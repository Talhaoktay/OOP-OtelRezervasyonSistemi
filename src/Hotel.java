import java.util.ArrayList;
import java.util.List;

/**
 * Hotel.java
 * Otel yönetim sisteminin ana sınıfıdır.
 * Odaları ve rezervasyonları listeler, yönetir.
 */
public class Hotel {
    private String name;
    private List<Room> rooms;               // Oteldeki tüm odalar
    private List<Reservation> reservations; // Yapılan tüm rezervasyonlar
    private int reservationIdCounter;       // Otomatik rezervasyon numarası vermek için sayaç

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationIdCounter = 1; // ID'ler 1'den başlasın
    }

    // --- 1. Oda Ekleme (Sisteme oda tanıtma) ---
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // --- 2. Boş Odaları Arama  ---
    public void listAvailableRooms() {
        System.out.println("--- " + name + " : MÜSAİT ODALAR ---");
        boolean found = false;

        for (Room room : rooms) {
            // Reservable arayüzünden gelen 'isAvailable' metodunu kullanıyoruz
            if (room.isAvailable()) {
                System.out.println(room.toString());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Maalesef şu an hiç boş oda yok.");
        }
    }

    // --- 3. Rezervasyon Yapma
    public void makeReservation(int roomNumber, Customer customer, int nightCount) {
        // Önce odayı bulalım
        Room selectedRoom = getRoom(roomNumber);

        if (selectedRoom == null) {
            System.out.println("Hata: " + roomNumber + " numaralı bir oda bulunamadı.");
            return;
        }

        // Oda dolu mu kontrol et
        if (!selectedRoom.isAvailable()) {
            System.out.println("Hata: Oda " + roomNumber + " zaten dolu!");
            return;
        }

        // 1. Odayı rezerve et (Room sınıfındaki metodu tetikler)
        selectedRoom.reserve();

        // 2. Yeni bir rezervasyon fişi oluştur
        Reservation newReservation = new Reservation(reservationIdCounter++, customer, selectedRoom, nightCount);

        // 3. Bu fişi listeye sakla
        reservations.add(newReservation);

        System.out.println("---------------------------------------");
        System.out.println("REZERVASYON BAŞARIYLA OLUŞTURULDU");
        System.out.println(newReservation.toString()); // Fişi ekrana yazdır
        System.out.println("---------------------------------------");
    }

    // --- 4. Rezervasyon İptal Etme  ---
    public void cancelReservation(int reservationId) {
        Reservation targetReservation = null;

        // ID'ye göre rezervasyonu bul
        for (Reservation res : reservations) {
            if (res.getId() == reservationId) {
                targetReservation = res;
                break;
            }
        }

        if (targetReservation != null) {
            // Bulunduysa iptal et (Reservation sınıfındaki cancel metodunu çağırır)
            targetReservation.cancel();
            // Listeden tamamen silmek yerine durumunu CANCELLED yaptık, o yüzden listede kalabilir.
            // Böylece "İptal edilenler" raporu da alabiliriz.
        } else {
            System.out.println("Hata: " + reservationId + " numaralı rezervasyon bulunamadı.");
        }
    }

    // --- Yardımcı Metod: Numaradan Oda Bulma ---
    public Room getRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    // --- Yönetici Metodu: Tüm Odaları Listele ---
    public void listAllRooms() {
        System.out.println("\n--- TÜM ODA DURUMLARI ---");
        for (Room room : rooms) {
            String durum = room.isAvailable() ? "[BOŞ]" : "[DOLU]";
            System.out.println(durum + " " + room.toString());
        }
    }// GUI (Arayüz) sınıfının odalar listesine erişmesi için gerekli metod
    public List<Room> getRooms() {
        return rooms;
    }
    // GUI'nin rezervasyon listesine erişmesi için gerekli
    public List<Reservation> getReservations() {
        return reservations;
    }
}
