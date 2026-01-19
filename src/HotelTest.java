/*
 * import org.junit.jupiter.api.Test;
 * import static org.junit.jupiter.api.Assertions.*;
 * 
 * public class HotelTest {
 * 
 * @Test
 * void testAddRoom() {
 * System.out.println("--- testAddRoom Başlatılıyor ---");
 * Hotel hotel = new Hotel("Test Otel");
 * 
 * // Odayı ekle
 * hotel.addRoom(new StandardRoom(101, 2, 100.0));
 * 
 * // Listede 1 oda var mı?
 * assertEquals(1, hotel.getRooms().size(), "Oda listeye eklenemedi!");
 * 
 * // Eğer buraya kadar geldiyse test başarılıdır
 * System.out.println(" BAŞARILI: Oda listeye eklendi ve sayı doğrulandı.\n");
 * }
 * 
 * @Test
 * void testFindRoom() {
 * System.out.println("--- testFindRoom Başlatılıyor ---");
 * Hotel hotel = new Hotel("Test Otel");
 * Room room1 = new StandardRoom(101, 2, 100.0);
 * hotel.addRoom(room1);
 * 
 * // Listeden odayı geri alabiliyor muyuz?
 * Room foundRoom = hotel.getRooms().get(0);
 * assertEquals(101, foundRoom.getRoomNumber());
 * 
 * System.out.println(" BAŞARILI: 101 numaralı oda sistemden geri okunabildi.\n"
 * );
 * }
 * 
 * @Test
 * void testCannotBookOccupiedRoom() {
 * System.out.println("--- testCannotBookOccupiedRoom Başlatılıyor ---");
 * 
 * Hotel hotel = new Hotel("Test Otel");
 * // Odayı elimizle ekleyelim: 101 No, 2 Yatak, 500 TL
 * hotel.addRoom(new StandardRoom(101, 2, 500.0));
 * 
 * Customer c1 = new Customer("Ali", "555", "ali@mail.com");
 * Customer c2 = new Customer("Veli", "556", "veli@mail.com");
 * 
 * // 1. İlk Müşteri odayı tutuyor
 * hotel.makeReservation(101, c1, 3, Season.WINTER);
 * 
 * // 2. İkinci Müşteri AYNI odayı tutmaya çalışıyor
 * hotel.makeReservation(101, c2, 5, Season.WINTER);
 * 
 * // BEKLENTİ: Listede sadece 1 rezervasyon olmalı. İkincisi reddedilmeli.
 * assertEquals(1, hotel.getReservations().size(),
 * "HATA: Dolu odaya tekrar rezervasyon yapıldı!");
 * 
 * System.out.println(" BAŞARILI: Sistem dolu odaya ikinci kaydı yapmadı.\n");
 * }
 * 
 * @Test
 * void testServiceCostAccumulation() {
 * System.out.println("--- testServiceCostAccumulation Başlatılıyor ---");
 * 
 * // Hazırlık
 * Room room = new StandardRoom(205, 2, 1000.0); // Günlük 1000 TL
 * Customer customer = new Customer("Zeynep", "555", "zeynep@mail.com");
 * Reservation res = new Reservation(1, customer, room, 2, Season.WINTER); // 2
 * Gün * 1000 = 2000 TL Oda parası
 * 
 * // Eylem: Üst üste harcama ekleyelim
 * res.addServiceCost(150.0); // Akşam Yemeği
 * res.addServiceCost(50.0); // Kahve
 * 
 * // Doğrulama
 * // Oda (2000) + Yemek (150) + Kahve (50) = 2200 TL olmalı
 * assertEquals(2200.0, res.getTotalPrice(),
 * "HATA: Ekstra harcamalar doğru toplanmıyor!");
 * 
 * System.out.
 * println(" BAŞARILI: Ekstra harcamalar ve oda fiyatı doğru toplandı.\n");
 * }
 * }
 */