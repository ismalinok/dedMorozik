package Alina.DedMorozik.model;


import org.hibernate.validator.constraints.NotBlank;

public class Letter {

    String username;
    String gifttype;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGifttype() {
        return gifttype;
    }

    public void setGifttype(String gifttype) {
        this.gifttype = gifttype;
    }
}