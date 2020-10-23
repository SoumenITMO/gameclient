package com.gameclient.Dto;

public class BettingData {

    private Long transactionId;
    private String username;
    private float transactionAmount;

    public BettingData(Long transactionId, String username, float transactionAmount) {
        this.transactionId = transactionId;
        this.username = username;
        this.transactionAmount = transactionAmount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
