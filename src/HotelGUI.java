import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * HotelGUI.java (FİNAL VERSİYON)
 * Özellikler:
 * - Oda Listeleme & Filtreleme
 * - Rezervasyon Yapma
 * - Rezervasyon İptali
 * - ODA SERVİSİ EKLEME (Opsiyonel Özellik Tamamlandı!)
 */
public class HotelGUI extends JFrame {
    private Hotel hotel;
    private JPanel roomPanel;

    public HotelGUI(Hotel hotel) {
        this.hotel = hotel;

        // --- Pencere Ayarları ---
        setTitle("Yıldız Teknik Palas - Otel Yönetim Sistemi");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. BAŞLIK ---
        JLabel titleLabel = new JLabel("OTEL ODA LİSTESİ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(60, 63, 65));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titleLabel, BorderLayout.NORTH);

        // --- 2. ODA LİSTESİ (ORTA) ---
        roomPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        roomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(roomPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. BUTON PANELİ (ALT) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(Color.LIGHT_GRAY);

        JButton refreshButton = new JButton("Listeyi Yenile");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(e -> refreshRooms());

        JButton manageButton = new JButton("Rezervasyonları Yönet / Oda Servisi");
        manageButton.setFont(new Font("Arial", Font.BOLD, 14));
        manageButton.setBackground(new Color(220, 53, 69)); // Kırmızımsı buton
        manageButton.setForeground(Color.WHITE);
        manageButton.addActionListener(e -> openManageReservationsWindow());

        bottomPanel.add(refreshButton);
        bottomPanel.add(manageButton);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshRooms();
    }

    // --- ODA LİSTELEME METODLARI ---
    private void refreshRooms() {
        roomPanel.removeAll();
        for (Room room : hotel.getRooms()) {
            roomPanel.add(createRoomCard(room));
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

    // --- REZERVASYON YÖNETİM PENCERESİ ---
    private void openManageReservationsWindow() {
        JDialog dialog = new JDialog(this, "Rezervasyon & Oda Servisi Yönetimi", true);
        dialog.setSize(700, 600);
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
                resRow.setMaximumSize(new Dimension(650, 90));
                resRow.setBackground(Color.WHITE);

                // SOL: Bilgiler
                String info = "<html><b>Rezervasyon #" + res.getId() + "</b> | Oda: " + res.getRoom().getRoomNumber() + "<br>" +
                        "Müşteri: " + res.getCustomer().getName() + "<br>" +
                        "Ekstra Harcama: <b style='color:blue'>" + res.getServiceCost() + " TL</b><br>" +
                        "TOPLAM TUTAR: <b>" + res.getTotalPrice() + " TL</b></html>";

                JLabel infoLabel = new JLabel(info);
                infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                resRow.add(infoLabel, BorderLayout.CENTER);

                // SAĞ: Butonlar Paneli
                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonsPanel.setBackground(Color.WHITE);

                // 1. ODA SERVİSİ BUTONU (YENİ)
                JButton serviceBtn = new JButton("YEMEK/SERVİS EKLE");
                serviceBtn.setBackground(new Color(255, 193, 7)); // Sarı buton
                serviceBtn.addActionListener(e -> {
                    String costStr = JOptionPane.showInputDialog(dialog, "Harcama tutarı giriniz (TL):");
                    if (costStr != null) {
                        try {
                            double cost = Double.parseDouble(costStr);
                            res.addServiceCost(cost); // Tutarı ekle
                            dialog.dispose(); // Yenilemek için kapat
                            openManageReservationsWindow(); // Tekrar aç
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog, "Lütfen geçerli bir sayı girin!");
                        }
                    }
                });

                // 2. İPTAL BUTONU
                JButton cancelBtn = new JButton("İPTAL ET");
                cancelBtn.setBackground(Color.RED);
                cancelBtn.setForeground(Color.WHITE);
                cancelBtn.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(dialog, "İptal edilsin mi?", "Onay", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        hotel.cancelReservation(res.getId());
                        dialog.dispose();
                        openManageReservationsWindow();
                        refreshRooms();
                        JOptionPane.showMessageDialog(this, "Rezervasyon iptal edildi.");
                    }
                });

                buttonsPanel.add(serviceBtn);
                buttonsPanel.add(cancelBtn);
                resRow.add(buttonsPanel, BorderLayout.EAST);

                listPanel.add(resRow);
                listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        if (!hasActiveReservation) {
            listPanel.add(new JLabel("Aktif rezervasyon yok.", SwingConstants.CENTER));
        }

        JScrollPane scroll = new JScrollPane(listPanel);
        dialog.add(scroll, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Kapat");
        closeBtn.addActionListener(e -> dialog.dispose());
        dialog.add(closeBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void showReservationPopup(Room room) {
        String name = JOptionPane.showInputDialog(this, "Misafir Adı Soyadı:");
        if (name == null || name.isEmpty()) return;
        String phone = JOptionPane.showInputDialog(this, "Telefon:");
        if (phone == null) return;
        String days = JOptionPane.showInputDialog(this, "Kaç Gece?");
        if (days == null) return;

        try {
            int d = Integer.parseInt(days);
            hotel.makeReservation(room.getRoomNumber(), new Customer(name, phone, ""), d);
            JOptionPane.showMessageDialog(this, "Rezervasyon Başarılı!");
            refreshRooms();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hatalı Giriş!");
        }
    }
}