import javax.swing.*;
import java.awt.*;
import java.io.File; // Resim kontrolü için gerekli
import java.util.Random;

/**
 * HotelGUI.java
 * Otel Rezervasyon Sistemi - Ana Arayüz
 * Özellikler: Oda Listeleme, Filtreleme, Rezervasyon Yapma, Ödeme (Interface
 * ile), İptal.
 */
public class HotelGUI extends JFrame {
    private Hotel hotel;
    private JPanel roomPanel;
    private JComboBox<String> filterBox;

    // --- SABİT RENKLER VE FONTLAR (Temiz Kod İçin) ---
    private final Color DARK_BG = new Color(60, 63, 65);
    private final Color GREEN_BTN = new Color(40, 167, 69);
    private final Color BLUE_BTN = new Color(0, 123, 255);
    private final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 28);
    private final Font NORMAL_FONT = new Font("Arial", Font.PLAIN, 14);

    public HotelGUI(Hotel hotel) {
        this.hotel = hotel;
        initUI(); // Arayüzü oluştur
    }

    private void initUI() {
        setTitle("Yıldız Teknik Palas - Otel Yönetim Sistemi");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. ÜST PANEL (BAŞLIK + FİLTRE) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(DARK_BG);

        JLabel titleLabel = new JLabel("OTEL ODA LİSTESİ", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // Filtreleme Paneli
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(DARK_BG);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel filterLabel = new JLabel("Oda Tipi Filtrele: ");
        filterLabel.setForeground(Color.WHITE);
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] filters = { "Tümü", "Standart Oda", "Deluxe Oda", "Balayı Suiti" };
        filterBox = new JComboBox<>(filters);
        filterBox.setFont(NORMAL_FONT);
        filterBox.setPreferredSize(new Dimension(150, 30));

        // Lambda Expression ile filtre değişince listeyi yenile
        filterBox.addActionListener(e -> refreshRooms());

        // Sezon Seçimi
        JLabel seasonLabel = new JLabel("Sezon: ");
        seasonLabel.setForeground(Color.WHITE);
        seasonLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JComboBox<Season> seasonBox = new JComboBox<>(Season.values());
        seasonBox.setFont(NORMAL_FONT);
        seasonBox.setPreferredSize(new Dimension(220, 30));
        // Başlangıç sezonunu seçili yap
        // Lambda Expression ile filtre değişince listeyi yenile
        filterBox.addActionListener(e -> refreshRooms());

        filterPanel.add(filterLabel);
        filterPanel.add(filterBox);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // --- 2. ODA LİSTESİ (ORTA) ---
        roomPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        roomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(roomPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. ALT PANEL (YÖNETİM BUTONU) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton manageButton = new JButton("Rezervasyon & Ödeme Yönetimi");
        manageButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageButton.setBackground(GREEN_BTN);
        manageButton.setForeground(Color.WHITE);
        manageButton.addActionListener(e -> openManageReservationsWindow());

        bottomPanel.add(manageButton);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshRooms(); // Başlangıçta listeyi doldur
    }

    // --- FİLTRELEME VE LİSTELEME MANTIĞI ---
    private void refreshRooms() {
        roomPanel.removeAll();
        String selectedFilter = (String) filterBox.getSelectedItem();

        for (Room room : hotel.getRooms()) {
            // Helper metot ile filtre kontrolü
            if (shouldShowRoom(room, selectedFilter)) {
                roomPanel.add(createRoomCard(room));
            }
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    private boolean shouldShowRoom(Room room, String filter) {
        if ("Tümü".equals(filter))
            return true;
        // instanceof kullanımı (Polymorphism kontrolü)
        if ("Standart Oda".equals(filter) && room instanceof StandardRoom)
            return true;
        if ("Deluxe Oda".equals(filter) && room instanceof DeluxeRoom)
            return true;
        return "Balayı Suiti".equals(filter) && room instanceof HoneymoonSuite;
    }

    // --- ODA KARTI OLUŞTURMA (RESİM KONTROLLÜ) ---
    private JPanel createRoomCard(Room room) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);

        // --- RESİM KONTROLÜ ---
        JLabel imageLabel = new JLabel();
        File imgFile = new File(room.getImagePath());

        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(room.getImagePath());
            Image img = icon.getImage().getScaledInstance(300, 180, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } else {
            // Resim yoksa yerine şık bir kutu koy (Fallback)
            imageLabel.setText("<html><center>RESİM YOK<br>(" + room.getImagePath() + ")</center></html>");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setPreferredSize(new Dimension(300, 180));
            imageLabel.setOpaque(true);
            imageLabel.setBackground(Color.LIGHT_GRAY);
            imageLabel.setForeground(Color.DARK_GRAY);
        }
        card.add(imageLabel, BorderLayout.NORTH);

        // Bilgi Kısmı (HTML Format)
        String color = room.isAvailable() ? "green" : "red";
        String statusText = room.isAvailable() ? "MÜSAİT" : "DOLU";

        // Görüntüleme için BUGÜNÜN tarihine göre sezonu alalım
        java.time.LocalDate today = java.time.LocalDate.now();
        Season currentSeason = Season.getSeasonForMonth(today.getMonthValue());

        String infoHtml = String.format("<html><center><h3>Oda %d</h3><p>Fiyat: %.1f TL</p>" +
                "<p style='color:%s; font-weight:bold;'>%s</p></center></html>",
                room.getRoomNumber(), room.calculatePrice(currentSeason), color, statusText);

        card.add(new JLabel(infoHtml, SwingConstants.CENTER), BorderLayout.CENTER);

        // Buton Kısmı
        JButton btn = new JButton(room.isAvailable() ? "REZERVASYON YAP" : "DOLU");
        if (room.isAvailable()) {
            btn.setBackground(BLUE_BTN);
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> showReservationPopup(room));
        } else {
            btn.setEnabled(false);
        }
        card.add(btn, BorderLayout.SOUTH);

        return card;
    }

    // --- REZERVASYON YÖNETİM PENCERESİ ---
    private void openManageReservationsWindow() {
        JDialog dialog = new JDialog(this, "Rezervasyon & Ödeme", true);
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        boolean hasActive = false;

        // Sadece aktif rezervasyonları listele
        for (Reservation res : hotel.getReservations()) {
            if (res.getStatus() == ReservationStatus.ACTIVE) {
                hasActive = true;
                listPanel.add(createReservationRow(res, dialog));
                listPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Boşluk
            }
        }

        if (!hasActive)
            listPanel.add(new JLabel("Aktif rezervasyon bulunamadı.", SwingConstants.CENTER));

        dialog.add(new JScrollPane(listPanel), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Kapat");
        closeBtn.addActionListener(e -> dialog.dispose());
        dialog.add(closeBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Rezervasyon Satırı Oluşturma
    private JPanel createReservationRow(Reservation res, JDialog parentDialog) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)));
        row.setMaximumSize(new Dimension(750, 110));
        row.setBackground(Color.WHITE);

        String paymentHtml = res.isPaid() ? "<span style='color:green'>✔ ÖDENDİ</span>"
                : "<span style='color:red'>✖ ÖDENMEDİ</span>";
        String infoHtml = String.format("<html><b>Rezervasyon #%d</b> | Oda: %d<br>" +
                "Müşteri: %s <br><i>(Tel: %s | Email: %s)</i><br>" +
                "Ekstra: <b style='color:blue'>%.1f TL</b> | Toplam: <b>%.1f TL</b><br>Durum: %s</html>",
                res.getId(), res.getRoom().getRoomNumber(), res.getCustomer().getName(),
                res.getCustomer().getPhone(), res.getCustomer().getEmail(),
                res.getServiceCost(), res.getTotalPrice(), paymentHtml);

        JLabel infoLabel = new JLabel(infoHtml);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        row.add(infoLabel, BorderLayout.CENTER);

        // İşlem Butonları
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);

        JButton serviceBtn = new JButton("Servis Ekle");
        serviceBtn.setBackground(new Color(255, 193, 7)); // Sarı
        serviceBtn.addActionListener(e -> {
            String cost = JOptionPane.showInputDialog(parentDialog, "Harcama tutarı (TL):");
            if (cost != null) {
                try {
                    res.addServiceCost(Double.parseDouble(cost));
                    parentDialog.dispose();
                    openManageReservationsWindow();
                } catch (Exception ex) {
                    /* Hata yok say */ }
            }
        });

        JButton payBtn = new JButton(res.isPaid() ? "ÖDENDİ" : "ÖDEME AL");
        if (res.isPaid()) {
            payBtn.setEnabled(false);
            payBtn.setBackground(Color.GRAY);
        } else {
            payBtn.setBackground(BLUE_BTN);
            payBtn.setForeground(Color.WHITE);
            payBtn.addActionListener(e -> handlePayment(res, parentDialog));
        }

        JButton cancelBtn = new JButton("İPTAL");
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(parentDialog, "Rezervasyon iptal edilsin mi?", "Onay",
                    JOptionPane.YES_NO_OPTION) == 0) {
                hotel.cancelReservation(res.getId());
                parentDialog.dispose();
                openManageReservationsWindow();
                refreshRooms();
            }
        });

        btnPanel.add(serviceBtn);
        btnPanel.add(payBtn);
        btnPanel.add(cancelBtn);
        row.add(btnPanel, BorderLayout.EAST);

        return row;
    }

    // --- ÖDEME SİSTEMİ (INTERFACE VE POLYMORPHISM KULLANIMI) ---
    private void handlePayment(Reservation res, JDialog dialog) {
        String[] options = { "Kredi Kartı", "Nakit" };
        int choice = JOptionPane.showOptionDialog(dialog,
                "Toplam Tutar: " + res.getTotalPrice() + " TL\nÖdeme yöntemi seçiniz:",
                "Ödeme Ekranı",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice != -1) {
            // Polymorphism: Arayüz referansı ile nesne tutuyoruz
            IPaymentMethod paymentMethod;

            if (choice == 0) {
                paymentMethod = new CreditCardPayment();
            } else {
                paymentMethod = new CashPayment();
            }

            // Seçilen yöntemin .pay() metodu çalışır
            boolean success = paymentMethod.pay(res.getTotalPrice());

            if (success) {
                res.confirmPayment();
                res.getRoom().setAvailable(true); // Oda tekrar müsait olur
                res.setStatus(ReservationStatus.CHECKED_OUT); // Çıkış yapıldı olarak işaretle

                JOptionPane.showMessageDialog(dialog, "Ödeme alındı ve oda çıkışı yapıldı.");
                dialog.dispose();
                openManageReservationsWindow();
                refreshRooms();
            }
        }
    }

    // --- REZERVASYON POPUP (VALIDATION DÖNGÜSÜ İLE) ---
    private void showReservationPopup(Room room) {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField daysField = new JTextField();

        // Tarih seçimi için 3'lü ComboBox (Gün / Ay / Yıl)
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++)
            days[i] = i + 1;
        JComboBox<Integer> dayBox = new JComboBox<>(days);

        String[] months = { "01 - Ocak", "02 - Şubat", "03 - Mart", "04 - Nisan", "05 - Mayıs", "06 - Haziran",
                "07 - Temmuz", "08 - Ağustos", "09 - Eylül", "10 - Ekim", "11 - Kasım", "12 - Aralık" };
        JComboBox<String> monthBox = new JComboBox<>(months);

        Integer[] years = { 2026, 2027, 2028, 2029, 2030, 2031 };
        JComboBox<Integer> yearBox = new JComboBox<>(years);

        datePanel.add(dayBox);
        datePanel.add(new JLabel(" / "));
        datePanel.add(monthBox);
        datePanel.add(new JLabel(" / "));
        datePanel.add(yearBox);

        Object[] message = {
                "Misafir Adı Soyadı:", nameField,
                "Telefon Numarası:", phoneField,
                "Email Adresi:", emailField,
                "Giriş Tarihi:", datePanel,
                "Kaç Gece Kalınacak:", daysField
        };

        // Hatalı girişte veriler silinmesin diye döngü
        while (true) {
            int option = JOptionPane.showConfirmDialog(this, message, "Rezervasyon: Oda " + room.getRoomNumber(),
                    JOptionPane.OK_CANCEL_OPTION);

            if (option != JOptionPane.OK_OPTION)
                return; // İptal edilirse çık

            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String daysStr = daysField.getText().trim();

            StringBuilder errors = new StringBuilder();
            if (name.isEmpty())
                errors.append("- Ad Soyad giriniz.\n");
            if (phone.isEmpty())
                errors.append("- Telefon giriniz.\n");
            if (email.isEmpty())
                errors.append("- Email giriniz.\n");
            if (daysStr.isEmpty())
                errors.append("- Gün sayısı giriniz.\n");

            if (errors.length() > 0) {
                JOptionPane.showMessageDialog(this, "Eksik Bilgiler:\n" + errors, "Hata", JOptionPane.WARNING_MESSAGE);
                continue; // Başa dön
            }

            try {
                int userDays = Integer.parseInt(daysStr);
                if (userDays <= 0)
                    throw new NumberFormatException();

                // Seçilen Tarihi Al
                int day = (Integer) dayBox.getSelectedItem();
                int monthIndex = monthBox.getSelectedIndex() + 1; // 0-based index to 1-based month
                int year = (Integer) yearBox.getSelectedItem();

                // Sezonu Belirle (Ay bilgisine göre)
                Season determinedSeason = Season.getSeasonForMonth(monthIndex);

                // Hata yoksa rezervasyonu yap
                hotel.makeReservation(room.getRoomNumber(), new Customer(name, phone, email), userDays,
                        determinedSeason);

                String seasonMsg = (determinedSeason == Season.SUMMER) ? "YAZ Sezonu (Yüksek Fiyat)"
                        : "KIŞ Sezonu (Standart Fiyat)";
                JOptionPane.showMessageDialog(this, "Rezervasyon Başarıyla Oluşturuldu!\nTarih: " + day + "/"
                        + monthIndex + "/" + year + "\nAlgılanan Sezon: " + seasonMsg);

                refreshRooms();
                break; // Döngüden çık

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Gün sayısı pozitif bir rakam olmalıdır!", "Hata",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Beklenmedik hata: " + ex.getMessage());
                break;
            }
        }
    }
}