package com.sarafizand.sarafizand.models;

public class Favourite {

    private String favorite_id;
    private String ben_name;
    private String ben_acc_no;
    private String bank;
    private String country;
    private String to_currency;

    public String getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(String favorite_id) {
        this.favorite_id = favorite_id;
    }

    public String getBen_name() {
        return ben_name;
    }

    public void setBen_name(String ben_name) {
        this.ben_name = ben_name;
    }

    public String getBen_acc_no() {
        return ben_acc_no;
    }

    public void setBen_acc_no(String ben_acc_no) {
        this.ben_acc_no = ben_acc_no;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTo_currency() {
        return to_currency;
    }

    public void setTo_currency(String to_currency) {
        this.to_currency = to_currency;
    }


}