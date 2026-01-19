import javax.swing.JOptionPane;

public class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        // Kart seçildiyse, kullanıcıdan kart numarası isteyelim
        String cardNumber = JOptionPane.showInputDialog(null,
                "Ödenecek Tutar: " + amount + " TL\nLütfen 16 haneli kart numaranızı giriniz:",
                "Kredi Kartı İle Ödeme",
                JOptionPane.QUESTION_MESSAGE);

        // Kullanıcı "İptal" derse veya boş bırakırsa ödeme başarısız olsun
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ödeme işlemi iptal edildi.", "İptal", JOptionPane.WARNING_MESSAGE);
            return false; // Başarısız döndür
        }

        // Simülasyon: Banka onayı
        System.out.println("Banka sistemine bağlanılıyor...");
        System.out.println("Kart No: " + cardNumber + " üzerinden " + amount + " TL çekildi.");

        JOptionPane.showMessageDialog(null,
                "İşlem Başarılı!\n" + amount + " TL kartınızdan çekildi.",
                "Banka Onayı", JOptionPane.INFORMATION_MESSAGE);

        return true; // İşlem başarılı
    }
}