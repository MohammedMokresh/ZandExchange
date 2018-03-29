package com.sarafizand.sarafizand.models;

import java.util.ArrayList;

/**
 * Created by Mohammed on 29/12/2017.
 */

public class CountriesForTransaction {
    String countryCode;
    String countryName;
//    Banks banks;
    ArrayList<Banks> banks;
    public String getCountryCode() {
        return countryCode;
    }

    public ArrayList<Banks> getBanks() {
        return banks;
    }

    public void setBanks(ArrayList<Banks> banks) {
        this.banks = banks;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

//    public Banks getBanks() {
//        return banks;
//    }
//
//    public void setBanks(Banks banks) {
//        this.banks = banks;
//    }


    @Override
    public String toString() {
        return ("1:" + this.getCountryCode()+
        " 2: " + this.getCountryName()+
        " 3: " + this.getBanks().toString()
        );
    }
}
