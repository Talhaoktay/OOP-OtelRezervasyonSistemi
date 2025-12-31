/**
 * Payment.java
 * Ödeme işlemlerini simüle eden sınıf.
 */
public class Payment {
    private int paymentId;
    private double amount;
    private String paymentMethod; // "Kredi Kartı", "Nakit"

    public Payment(int paymentId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    // Ödeme işlemini gerçekleştiren metod
    public boolean processPayment() {
        System.out.println("----------------------------------------");
        System.out.println("ÖDEME İŞLEMİ BAŞLATILIYOR (ID: " + paymentId + ")");
        System.out.println("Tutar: " + amount + " TL");
        System.out.println("Yöntem: " + paymentMethod);

        // Simülasyon: Ödeme her zaman başarılı varsayalım
        System.out.println("Banka ile iletişim kuruluyor...");
        System.out.println("Ödeme Başarıyla Alındı ✔");
        System.out.println("----------------------------------------");

        return true; // Başarılı döndür
    }
}