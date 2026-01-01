import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OtelSistemiTest {

    // 1. TEST: REZERVASYON FİYAT HESAPLAMA
    @Test
    public void testReservationPrice() {
        Customer customer = new Customer("Ali Yilmaz", "5551234567", "ali@mail.com");

        // StandardRoom (ID, Kapasite, Fiyat) alıyor gibi görünüyor (Hata vermemişti)
        Room room = new StandardRoom(101, 2, 1000.0);

        Reservation reservation = new Reservation(1, customer, room, 3);

        assertEquals(3000.0, reservation.getTotalPrice(), "Fiyat hesaplaması yanlış!");
    }

    // 2. TEST: OTEL YÖNETİMİ - ODA DURUMU
    @Test
    public void testRoomAvailabilityAfterBooking() {
        Hotel hotel = new Hotel("Grand Hotel");
        Customer customer = new Customer("Ayse Demir", "5559876543", "ayse@mail.com");

        // Eğer DeluxeRoom da hata verirse buradaki '3'ü silip (202, 2000.0) yapmalısın.
        // Şimdilik hata vermediği için bırakıyorum.
        Room room = new DeluxeRoom(202, 3, 2000.0);
        hotel.addRoom(room);

        // Rezervasyon Yap (OdaNo, Müşteri, Gün)
        hotel.makeReservation(202, customer, 3);

        assertFalse(room.isAvailable(), "HATA: Rezervasyon sonrası oda dolu görünmüyor!");
    }

    // 3. TEST: ÇİFTE REZERVASYON DENEMESİ
    @Test
    public void testDoubleBooking() {
        Hotel hotel = new Hotel("Grand Hotel");
        Customer customer1 = new Customer("Musteri Bir", "5551111111", "m1@mail.com");
        Customer customer2 = new Customer("Musteri Iki", "5552222222", "m2@mail.com");

        // DÜZELTME: HoneymoonSuite sadece (ID, Fiyat) alıyor.
        // Ortadaki kapasite sayısı (2) SİLİNDİ.
        Room room = new HoneymoonSuite(303, 5000.0);

        hotel.addRoom(room);

        // İlk rezervasyon
        hotel.makeReservation(303, customer1, 2);

        assertFalse(room.isAvailable(), "İlk rezervasyon başarısız oldu.");

        // İkinci rezervasyon denemesi (Hata vermemeli, sadece işlem yapmamalı)
        hotel.makeReservation(303, customer2, 2);

        assertFalse(room.isAvailable(), "Oda durumu bozuldu.");
    }
}