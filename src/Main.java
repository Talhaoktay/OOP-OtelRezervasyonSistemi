import javax.swing.SwingUtilities;

/**
 * Main.java
 * Uygulamanın giriş noktası.
 * Artık Konsol (Siyah ekran) yerine Swing Arayüzünü (Pencereli sistem) başlatıyor.
 */
public class Main {
    public static void main(String[] args) {

        // 1. ADIM: Oteli Oluştur
        Hotel myHotel = new Hotel("Yıldız Teknik Palas");


        // Standart Odalar
        myHotel.addRoom(new StandardRoom(101, 2, 500.0));
        myHotel.addRoom(new StandardRoom(102, 2, 500.0));
        myHotel.addRoom(new StandardRoom(103, 3, 600.0)); // 3 Kişilik
        myHotel.addRoom(new StandardRoom(104, 2, 500.0));

        // Deluxe Odalar
        myHotel.addRoom(new DeluxeRoom(201, 2, 1000.0));
        myHotel.addRoom(new DeluxeRoom(202, 4, 1200.0)); // Aile odası
        myHotel.addRoom(new DeluxeRoom(301, 2, 1500.0)); // Kral Dairesi

        // BALAYI SUİTLERİ
        // Baz fiyat 1000 TL veriyoruz ama 3 katı olacağı için 3000 TL görünecek.
        myHotel.addRoom(new HoneymoonSuite(401, 1000.0));
        myHotel.addRoom(new HoneymoonSuite(402, 1000.0));

        // 3. ADIM: Arayüzü (Pencereyi) Başlat
        // Swing uygulamalarının donmaması için 'invokeLater' kullanıldı.
        SwingUtilities.invokeLater(() -> {
            // Arayüzü oluştur
            HotelGUI gui = new HotelGUI(myHotel);

            // Pencereyi görünür hale getirir.
            gui.setVisible(true);
        });
    }
}