package Alina.DedMorozik.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LetterDto {

    private String giftType;
    private String userName;

    @JsonProperty("giftType")
    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {

        this.giftType = giftType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}