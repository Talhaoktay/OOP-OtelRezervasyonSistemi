
/**
 * Season.java
 * Otel için sezon tiplerini belirtir.
 */
public enum Season {
    WINTER("Kış Sezonu (Standart Fiyat)"),
    SUMMER("Yaz Sezonu (Yüksek Fiyat)");

    private final String description;

    Season(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }

    // Ay numarasına göre sezonu döndürür (1: Ocak, ... 12: Aralık)
    public static Season getSeasonForMonth(int month) {
        // HAZİRAN(6), TEMMUZ(7), AĞUSTOS(8), EYLÜL(9) -> YAZ
        if (month >= 6 && month <= 9) {
            return SUMMER;
        }
        return WINTER;
    }
}
