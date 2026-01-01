import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class RoomTest {

    @Test
    void testStandardRoomPrice() {
        Room room = new StandardRoom(101, 2, 500.0);
        assertEquals(500.0, room.calculatePrice(), "Standart oda fiyatı yanlış!");
    }

    @Test
    void testHoneymoonSuitePrice() {
        Room room = new HoneymoonSuite(401, 1000.0);
        assertEquals(3000.0, room.calculatePrice(), "Balayı odası fiyatı 3 katı olmalı!");
    }

    @Test
    void testRoomAvailability() {
        Room room = new StandardRoom(102, 2, 600.0);

        assertTrue(room.isAvailable(), "Oda başlangıçta boş olmalı");
        room.reserve();
        assertFalse(room.isAvailable(), "Rezervasyon sonrası oda dolu görünmeli");
    }
}