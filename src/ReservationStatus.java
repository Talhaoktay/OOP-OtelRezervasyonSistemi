/**
 * Rezervasyonların durumunu belirten Enum yapısı.
 */
public enum ReservationStatus {
    ACTIVE,      // Rezervasyon şu an aktif (Müşteri kalıyor veya kalacak)
    CANCELLED,   // Rezervasyon iptal edildi
    CHECKED_OUT  // Müşteri ödemeyi yaptı ve otelden ayrıldı
}