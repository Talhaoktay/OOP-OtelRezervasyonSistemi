import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class HotelTest {

    @Test
    void testAddRoom() {
        Hotel hotel = new Hotel("Test Otel");

        // Odayı ekle
        hotel.addRoom(new StandardRoom(101, 2, 100.0));

        // Listede 1 oda var mı?
        assertEquals(1, hotel.getRooms().size(), "Oda listeye eklenemedi!");
    }

    @Test
    void testFindRoom() {
        Hotel hotel = new Hotel("Test Otel");
        Room room1 = new StandardRoom(101, 2, 100.0);
        hotel.addRoom(room1);

        // Listeden odayı geri alabiliyor muyuz?
        Room foundRoom = hotel.getRooms().get(0);
        assertEquals(101, foundRoom.getRoomNumber());
    }
}