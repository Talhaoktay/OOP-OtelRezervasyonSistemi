public abstract class Room implements Reservable {
    private int roomNumber;
    private int capacity;
    private double basePrice;
    private boolean isBooked;

    public Room(int roomNumber, int capacity, double basePrice) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.isBooked = false;
    }

    public abstract double calculatePrice();

    @Override
    public boolean isAvailable() { return !isBooked; }

    @Override
    public void reserve() {
        this.isBooked = true;
        System.out.println("Oda " + roomNumber + " rezerve edildi.");
    }

    @Override
    public void cancelReservation() {
        this.isBooked = false;
        System.out.println("Oda " + roomNumber + " iptal edildi.");
    }

    // Getter Metodları
    public int getRoomNumber() { return roomNumber; }
    public double getBasePrice() { return basePrice; }

    @Override
    public String toString() {
        return "Oda No: " + roomNumber + " (" + capacity + " Kisilik)";
    }
}