package au.com.dragon;

public enum RateType {
    FLAT_RATE("Flat Rate"),
    HOURLY_RATE("Hourly Rate");
    
    private final String display;
    
    RateType(String display) {
        this.display = display;
    }
    
    String getDisplay() {
        return display;
    }
}
