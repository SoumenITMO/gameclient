package com.gameclient.Dto;

public class PlayerLogoutDto {

    private String playerUsername;

    public PlayerLogoutDto(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public PlayerLogoutDto() { }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }
}
