import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * HotelGUI.java
 * GÜNCELLENDİ: Oda Filtreleme (Dropdown) özelliği eklendi.
 */
public class HotelGUI extends JFrame {
    private Hotel hotel;
    private JPanel roomPanel;
    private JComboBox<String> filterBox; // Filtreleme kutusu

    public HotelGUI(Hotel hotel) {
        this.hotel = hotel;

        // --- Pencere Ayarları ---
        setTitle("Yıldız Teknik Palas - Otel Yönetim Sistemi");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. ÜST PANEL (BAŞLIK + FİLTRE) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(60, 63, 65));

        // Başlık
        JLabel titleLabel = new JLabel("OTEL ODA LİSTESİ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        // --- FİLTRELEME KUTUSU (YENİ) ---
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(new Color(60, 63, 65));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel filterLabel = new JLabel("Oda Tipi Filtrele: ");
        filterLabel.setForeground(Color.WHITE);
        filterLabel.setFont(new Font("Arial", Font.BOLD, 14));

        String[] filters = {"Tümü", "Standart Oda", "Deluxe Oda", "Balayı Suiti"};
        filterBox = new JComboBox<>(filters);
        filterBox.setFont(new Font("Arial", Font.PLAIN, 14));
        filterBox.setPreferredSize(new Dimension(150, 30));

        // Filtre değişince listeyi yenile
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

        // --- 3. BUTON PANELİ (ALT) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton manageButton = new JButton("Rezervasyon & Ödeme Yönetimi");
        manageButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageButton.setBackground(new Color(40, 167, 69)); // Yeşilimsi buton
        manageButton.setForeground(Color.WHITE);
        manageButton.addActionListener(e -> openManageReservationsWindow());

        bottomPanel.add(manageButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // İlk açılışta odaları yükle
        refreshRooms();
    }

    // --- ODA LİSTELEME (GÜNCELLENDİ: FİLTRE MANTIĞI) ---
    private void refreshRooms() {
        roomPanel.removeAll();

        // Seçilen filtreyi al ("Tümü", "Standart Oda" vs.)
        String selectedFilter = (String) filterBox.getSelectedItem();

        for (Room room : hotel.getRooms()) {
            boolean match = false;

            // Filtre Mantığı (Polimorfizm kontrolü - instanceof)
            if (selectedFilter.equals("Tümü")) {
                match = true;
            } else if (selectedFilter.equals("Standart Oda") && room instanceof StandardRoom) {
                match = true;
            } else if (selectedFilter.equals("Deluxe Oda") && room instanceof DeluxeRoom) {
                match = true;
            } else if (selectedFilter.equals("Balayı Suiti") && room instanceof HoneymoonSuite) {
                match = true;
            }

            // Eğer filtreye uyuyorsa ekrana ekle
            if (match) {
                roomPanel.add(createRoomCard(room));
            }
        }

        roomPanel.revalidate();
        roomPanel.repaint();
    }

    private JPanel createRoomCard(Room room) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(room.getImagePath());
        Image img = icon.getImage().getScaledInstance(300, 180, Image.SCALE_SMOOTH);
        card.add(new JLabel(new ImageIcon(img)), BorderLayout.NORTH);

        String statusText = room.isAvailable() ? "MÜSAİT" : "DOLU";
        String color = room.isAvailable() ? "green" : "red";
        String html = "<html><center><h3>Oda " + room.getRoomNumber() + "</h3>" +
                "<p>Fiyat: " + room.calculatePrice() + " TL</p>" +
                "<p style='color:" + color + "; font-weight:bold;'>" + statusText + "</p></center></html>";
        card.add(new JLabel(html, SwingConstants.CENTER), BorderLayout.CENTER);

        JButton btn = new JButton(room.isAvailable() ? "REZERVASYON YAP" : "DOLU");
        if (room.isAvailable()) {
            btn.setBackground(new Color(0, 123, 255));
            btn.setForeground(Color.WHITE);
            btn.addActionListener(e -> showReservationPopup(room));
        } else {
            btn.setEnabled(false);
        }
        card.add(btn, BorderLayout.SOUTH);

        return card;
    }

    // --- YÖNETİM PENCERESİ ---
    private void openManageReservationsWindow() {
        JDialog dialog = new JDialog(this, "Rezervasyon & Ödeme", true);
        dialog.setSize(750, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        boolean hasActiveReservation = false;

        for (Reservation res : hotel.getReservations()) {
            if (res.getStatus() == ReservationStatus.ACTIVE) {
                hasActiveReservation = true;

                JPanel resRow = new JPanel(new BorderLayout());
                resRow.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)
                ));
                resRow.setMaximumSize(new Dimension(700, 110));
                resRow.setBackground(Color.WHITE);

                String paymentStatus = res.isPaid() ? "<span style='color:green'>✔ ÖDENDİ</span>" : "<span style='color:red'>✖ ÖDENMEDİ</span>";

                String info = "<html><b>Rezervasyon #" + res.getId() + "</b> | Oda: " + res.getRoom().getRoomNumber() + "<br>" +
                        "Müşteri: " + res.getCustomer().getName() + "<br>" +
                        "Ekstra Harcama: <b style='color:blue'>" + res.getServiceCost() + " TL</b><br>" +
                        "Toplam Tutar: <b>" + res.getTotalPrice() + " TL</b><br>" +
                        "Durum: " + paymentStatus + "</html>";

                JLabel infoLabel = new JLabel(info);
                infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                resRow.add(infoLabel, BorderLayout.CENTER);

                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonsPanel.setBackground(Color.WHITE);

                JButton serviceBtn = new JButton("Servis Ekle");
                serviceBtn.setBackground(new Color(255, 193, 7));
                serviceBtn.addActionListener(e -> {
                    String costStr = JOptionPane.showInputDialog(dialog, "Harcama tutarı (TL):");
                    if (costStr != null) {
                        try {
                            res.addServiceCost(Double.parseDouble(costStr));
                            dialog.dispose(); openManageReservationsWindow();
                        } catch (Exception ex) { }
                    }
                });

                JButton payBtn = new JButton("ÖDEME AL");
                if (res.isPaid()) {
                    payBtn.setText("ÖDENDİ");
                    payBtn.setEnabled(false);
                    payBtn.setBackground(Color.GRAY);
                } else {
                    payBtn.setBackground(new Color(0, 123, 255));
                    payBtn.setForeground(Color.WHITE);
                    payBtn.addActionListener(e -> {
                        String[] options = {"Kredi Kartı", "Nakit"};
                        int choice = JOptionPane.showOptionDialog(dialog,
                                "Toplam Tutar: " + res.getTotalPrice() + " TL\nÖdeme yöntemi seçiniz:",
                                "Ödeme Ekranı",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                        if (choice != -1) {
                            String method = options[choice];
                            Payment payment = new Payment(new Random().nextInt(1000), res.getTotalPrice(), method);
                            boolean success = payment.processPayment();
                            if (success) {
                                res.confirmPayment();
                                JOptionPane.showMessageDialog(dialog, "Ödeme Başarılı!\nOda çıkışı yapıldı ve müsait duruma getirildi.");
                                dialog.dispose(); openManageReservationsWindow(); refreshRooms();
                            }
                        }
                    });
                }

                JButton cancelBtn = new JButton("İPTAL");
                cancelBtn.setBackground(Color.RED);
                cancelBtn.setForeground(Color.WHITE);
                cancelBtn.addActionListener(e -> {
                    if (JOptionPane.showConfirmDialog(dialog, "İptal edilsin mi?", "Onay", JOptionPane.YES_NO_OPTION) == 0) {
                        hotel.cancelReservation(res.getId());
                        dialog.dispose(); openManageReservationsWindow(); refreshRooms();
                    }
                });

                buttonsPanel.add(serviceBtn);
                buttonsPanel.add(payBtn);
                buttonsPanel.add(cancelBtn);
                resRow.add(buttonsPanel, BorderLayout.EAST);

                listPanel.add(resRow);
                listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        if (!hasActiveReservation) listPanel.add(new JLabel("Aktif rezervasyon yok.", SwingConstants.CENTER));

        JScrollPane scroll = new JScrollPane(listPanel);
        dialog.add(scroll, BorderLayout.CENTER);
        JButton closeBtn = new JButton("Kapat");
        closeBtn.addActionListener(e -> dialog.dispose());
        dialog.add(closeBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // --- REZERVASYON PENCERESİ (GÜNCELLENDİ: EMAİL EKLENDİ) ---
    private void showReservationPopup(Room room) {
        // 1. Veri giriş kutucuklarını oluşturuyoruz
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField(); // Yeni eklenen Email alanı
        JTextField daysField = new JTextField();

        // 2. Bu kutucukları ekranda görünecek sıraya koyuyoruz
        Object[] message = {
                "Misafir Adı Soyadı:", nameField,
                "Telefon Numarası:", phoneField,
                "Email Adresi:", emailField,    // Ekranda görünecek kısım
                "Kaç Gece Kalınacak:", daysField
        };

        // 3. Pencereyi açıyoruz
        int option = JOptionPane.showConfirmDialog(this, message, "Rezervasyon Oluştur", JOptionPane.OK_CANCEL_OPTION);

        // 4. Kullanıcı "OK" butonuna basarsa verileri alıyoruz
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText(); // Email bilgisini aldık
                String daysStr = daysField.getText();

                // Boş alan kontrolü (İsteğe bağlı)
                if (name.isEmpty() || daysStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen gerekli alanları doldurunuz!");
                    return;
                }

                int days = Integer.parseInt(daysStr);

                // BURASI ÖNEMLİ: Customer oluştururken artık email'i de gönderiyoruz.
                hotel.makeReservation(room.getRoomNumber(), new Customer(name, phone, email), days);

                JOptionPane.showMessageDialog(this, "Rezervasyon Başarıyla Tamamlandı!");
                refreshRooms(); // Ekranı yenile

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen gün sayısını sayı olarak giriniz!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Bir hata oluştu: " + ex.getMessage());
            }
        }
    }
}