package com.gameclient.Dto;

import java.io.Serializable;

public class LoginResponseDto implements Serializable {

    private String playerUsername;
    private String playerName;
    private float walletBalance;
    private double betAmount;
    private short profileStatus;

    public LoginResponseDto(String playerUsername, String playerName, float wallerBalance, double betAmount, short profileStatus) {

        this.playerUsername = playerUsername;
        this.playerName = playerName;
        this.walletBalance = wallerBalance;
        this.betAmount = betAmount;
        this.profileStatus = profileStatus;
    }

    public LoginResponseDto() {  }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public float getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(float walletBalance) {
        this.walletBalance = walletBalance;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public short getProfileStatus() {
        return profileStatus;
    }

    public void setProfileStatus(short profileStatus) {
        this.profileStatus = profileStatus;
    }
}
