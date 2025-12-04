/**
 * Customer.java
 * Otelde konaklayacak müşterinin temel bilgilerini tutar.
 */
public class Customer {
    private String name;
    private String phone;
    private String email;

    // Kurucu Metod (Constructor)
    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getter metodları (Bilgilere erişmek için)
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Musteri: " + name + " (" + phone + ")";
    }
}