package au.com.rpn;

public class Rate {
    private final String rateName;
    private final String rate;

    public Rate(String rateName, String rate) {
        this.rateName = rateName;
        this.rate = rate;
    }
    
    public String getRateName() {
        return rateName;
    }
    public String getRate() {
        return rate;
    }
}
