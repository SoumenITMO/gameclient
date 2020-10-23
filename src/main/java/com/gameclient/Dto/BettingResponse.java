package com.gameclient.Dto;

public class BettingResponse {

    private Long transactionId;
    private Integer code;
    private Integer balanceVersion;
    private float betAmount;
    private float remainingBalance;

    public BettingResponse(Long transactionId, Integer errorCode, Integer balanceVersion, float betAmount, float remainingBalance) {

        this.transactionId = transactionId;
        this.code = errorCode;
        this.balanceVersion = balanceVersion;
        this.betAmount = betAmount;
        this.remainingBalance = remainingBalance;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer errorCode) {
        this.code = errorCode;
    }

    public Integer getBalanceVersion() {
        return balanceVersion;
    }

    public void setBalanceVersion(Integer balanceVersion) {
        this.balanceVersion = balanceVersion;
    }

    public float getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(float betAmount) {
        this.betAmount = betAmount;
    }

    public float getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(float remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
