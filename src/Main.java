import java.util.Scanner;

/**
 * Main.java
 * Programın giriş noktasıdır.
 * Kullanıcı ile konuşan (konsol menüsü) kısımdır.
 */
public class Main {
    public static void main(String[] args) {
        // Kullanıcıdan veri almak için Scanner
        Scanner scanner = new Scanner(System.in);

        // 1. Otelimizi kuralım
        Hotel myHotel = new Hotel("Yıldız Teknik Palas");

        // 2. Otele Başlangıç Odaları Ekleyelim (Demo Verisi)
        // Standart Odalar (No, Kapasite, Fiyat)
        myHotel.addRoom(new StandardRoom(101, 2, 500.0));
        myHotel.addRoom(new StandardRoom(102, 2, 500.0));
        myHotel.addRoom(new StandardRoom(103, 3, 600.0));

        // Deluxe Odalar (No, Kapasite, Fiyat) - Fiyat %20 artacak
        myHotel.addRoom(new DeluxeRoom(201, 2, 800.0));
        myHotel.addRoom(new DeluxeRoom(202, 4, 1000.0));
        myHotel.addRoom(new DeluxeRoom(301, 2, 1200.0)); // Deniz manzaralı :)

        System.out.println("=========================================");
        System.out.println("   OTEL REZERVASYON SİSTEMİNE HOŞGELDİNİZ   ");
        System.out.println("=========================================");

        // Sonsuz döngü ile menüyü sürekli göster
        while (true) {
            System.out.println("\nLütfen bir işlem seçiniz:");
            System.out.println("1. Boş Odaları Listele");
            System.out.println("2. Rezervasyon Yap");
            System.out.println("3. Rezervasyon İptal Et");
            System.out.println("4. Tüm Oda Durumlarını Gör (Yönetici)");
            System.out.println("0. Çıkış");
            System.out.print("Seçiminiz: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Buffer temizleme (Enter tuşunu yutması için)

            switch (choice) {
                case 1:
                    // Kart 20: Boş oda arama
                    myHotel.listAvailableRooms();
                    break;

                case 2:
                    // Kart 21: Rezervasyon yapma
                    System.out.println("\n--- REZERVASYON EKRANI ---");
                    System.out.print("Hangi odayı istiyorsunuz? (Oda No): ");
                    int roomNo = scanner.nextInt();
                    scanner.nextLine(); // Buffer temizleme

                    System.out.print("Müşteri Adı Soyadı: ");
                    String name = scanner.nextLine();

                    System.out.print("Telefon Numarası: ");
                    String phone = scanner.nextLine();

                    System.out.print("E-posta Adresi: ");
                    String email = scanner.nextLine();

                    System.out.print("Kaç gece kalacaksınız?: ");
                    int nights = scanner.nextInt();
                    scanner.nextLine();

                    // Müşteri nesnesini oluştur
                    Customer newCustomer = new Customer(name, phone, email);

                    // Otel sınıfındaki metodu çağır
                    myHotel.makeReservation(roomNo, newCustomer, nights);
                    break;

                case 3:
                    // Kart 22: İptal işlemi
                    System.out.println("\n--- İPTAL EKRANI ---");
                    System.out.print("İptal edilecek Rezervasyon ID'si: ");
                    int resId = scanner.nextInt();
                    scanner.nextLine();

                    myHotel.cancelReservation(resId);
                    break;

                case 4:
                    // Ekstra: Yönetici listesi
                    myHotel.listAllRooms();
                    break;

                case 0:
                    System.out.println("Sistemden çıkılıyor. İyi günler dileriz!");
                    scanner.close();
                    return; // Programı sonlandırır

                default:
                    System.out.println("Hatalı seçim! Lütfen 0-4 arası bir sayı giriniz.");
            }
        }
    }
}