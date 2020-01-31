package Alina.DedMorozik.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {
    private Integer id;
    private String name;

    @JsonProperty("userid")

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}