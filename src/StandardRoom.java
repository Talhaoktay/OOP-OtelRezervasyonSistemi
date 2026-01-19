public class StandardRoom extends Room {
    private int bedCount;
    private double price;

    // Hotel.java içinde new StandardRoom(101, 2, 500.0) dediğimiz yer burayı çalıştırır.
    public StandardRoom(int roomNumber, int bedCount, double price) {
        // Üst sınıfa (Room) sadece onun istediği bilgileri veriyoruz:
        // Standart odalar için varsayılan bir resim yolu atadık.
        super(roomNumber, "img/standard.jpg");

        // Diğer bilgiler bu sınıfa özel olduğu için kendimiz tutuyoruz
        this.bedCount = bedCount;
        this.price = price;
    }

    // Eğer özel resim yolu vermek istersen diye bu versiyonu da ekleyebiliriz
    public StandardRoom(int roomNumber, int bedCount, double price, String imagePath) {
        super(roomNumber, imagePath);
        this.bedCount = bedCount;
        this.price = price;
    }

    @Override
    public double calculatePrice(Season season) {
        if (season == Season.SUMMER) {
            return price * 2.0; // Yazın fiyat 2 katı
        }
        return price;
    }
}