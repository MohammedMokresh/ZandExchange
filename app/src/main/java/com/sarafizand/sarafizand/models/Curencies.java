package com.sarafizand.sarafizand.models;

/**
 * Created by Mohammed on 27/10/2017.
 */

public class Curencies {
    int sellPrice;
    String buyPrice;
    String lastUpdate;
    String currencyName;
    String flagName;
    int currincyId;

    public int getCurrincyId() {
        return currincyId;
    }

    public void setCurrincyId(int currincyId) {
        this.currincyId = currincyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status;
    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
}
