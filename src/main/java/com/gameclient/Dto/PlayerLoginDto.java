package com.gameclient.Dto;

public class PlayerLoginDto {

    private String username;
    private String password;

    public PlayerLoginDto(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public PlayerLoginDto() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
