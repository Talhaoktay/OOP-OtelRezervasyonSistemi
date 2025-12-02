/**
 * Bu bir ARAYÜZ (Interface) dosyasıdır.
 * "Rezerve edilebilir" olan her şeyin uyması gereken kuralları burada belirleriz.
 */
public interface Reservable {

    // Bir şeyin müsait olup olmadığını söyleyebilmeli
    boolean isAvailable();

    //  Rezervasyon yapma işlemi olmalı
    void reserve();

    //  İptal etme işlemi olmalı
    void cancelReservation();
}
