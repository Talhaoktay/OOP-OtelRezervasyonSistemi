/*
 * import org.junit.jupiter.api.Test;
 * import static org.junit.jupiter.api.Assertions.*;
 * 
 * class ReservationTest {
 * 
 * @Test
 * void testTotalPriceCalculation() {
 * // 1. Hazırlık
 * // Senin eski oda yapına göre: (Oda No: 101, Yatak: 2, Fiyat: 500.0)
 * Room room = new StandardRoom(101, 2, 500.0);
 * 
 * Customer customer = new Customer("Test Müşteri", "555", "test@mail.com");
 * Reservation res = new Reservation(1, customer, room, 3, Season.WINTER);
 * 
 * // 200 TL ekstra harcama ekle
 * res.addServiceCost(200.0);
 * 
 * // 2. Eylem
 * double total = res.getTotalPrice();
 * 
 * // 3. Doğrulama
 * // (500 TL * 3 Gün) + 200 TL Ekstra = 1700 TL etmeli
 * assertEquals(1700.0, total, "Toplam fiyat hesaplaması yanlış!");
 * }
 * 
 * @Test
 * void testPaymentStatus() {
 * // Hazırlık
 * Room room = new StandardRoom(102, 2, 500.0);
 * Customer customer = new Customer("Ali", "123", "ali@mail.com");
 * 
 * Reservation res = new Reservation(2, customer, room, 1, Season.WINTER);
 * 
 * // Başlangıç kontrolü
 * assertFalse(res.isPaid(), "Yeni rezervasyon ödenmiş olarak başlıyor, hata!");
 * 
 * // Ödeme yap
 * res.confirmPayment();
 * 
 * // Sonuç kontrolü
 * assertTrue(res.isPaid(), "Ödeme onayı çalışmıyor!");
 * }
 * }
 */