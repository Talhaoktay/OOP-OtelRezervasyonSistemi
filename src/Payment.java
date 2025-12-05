/**
 * Payment.java
 * Ödeme işlemini temsil eden basit sınıf.
 */
public class Payment {
    private int paymentId;
    private double amount;          // Ödenen miktar
    private String paymentMethod;   // "Kredi Kartı", "Nakit" vb.

    public Payment(int paymentId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    // Ödeme işlemini gerçekleştiren (simüle eden) metod
    public void processPayment() {
        System.out.println("----------------------------------------");
        System.out.println("ÖDEME İŞLEMİ BAŞLATILIYOR...");
        System.out.println("Tutar: " + amount + " TL");
        System.out.println("Yöntem: " + paymentMethod);
        System.out.println("Ödeme Başarıyla Alındı (ID: " + paymentId + ")");
        System.out.println("----------------------------------------");
    }
}