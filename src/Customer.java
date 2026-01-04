/**
 * Customer.java
 * Müşteri bilgilerini tutar: Ad, Telefon ve Email.
 */
public class Customer {
    private String name;
    private String phone;
    private String email;

    // Kurucu Metot
    public Customer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getter Metotları
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
        return name + " (" + email + ")";
    }
}