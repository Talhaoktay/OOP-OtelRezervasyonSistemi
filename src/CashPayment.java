import javax.swing.JOptionPane;

public class CashPayment implements IPaymentMethod {
    @Override
    public boolean pay(double amount) {
        JOptionPane.showMessageDialog(null,
                amount + " TL tutarında ödeme NAKİT olarak kasaya işlendi.",
                "Nakit İşlem", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
}