package com.gameclient.Dto;

public class BettingDto {

    private String username;
    private float betAmount;

    public BettingDto(String username, float betAmount) {
        this.username = username;
        this.betAmount = betAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(float betAmount) {
        this.betAmount = betAmount;
    }
}
