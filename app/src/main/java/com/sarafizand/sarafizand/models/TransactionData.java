package com.sarafizand.sarafizand.models;

import java.util.ArrayList;

/**
 * Created by Mohammed on 28/12/2017.
 */

public class TransactionData {
    String currencyCode;
    String currencyName;
//    CountriesForTransaction countriesForTransaction;

    ArrayList<CountriesForTransaction> countriesForTransactions;
    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

//    public CountriesForTransaction getCountriesForTransaction() {
//        return countriesForTransaction;
//    }

    public ArrayList<CountriesForTransaction> getCountriesForTransactions() {
        return countriesForTransactions;
    }

    public void setCountriesForTransactions(ArrayList<CountriesForTransaction> countriesForTransactions) {
        this.countriesForTransactions = countriesForTransactions;
    }

//    public void setCountriesForTransaction(CountriesForTransaction countriesForTransaction) {
//        this.countriesForTransaction = countriesForTransaction;
//    }

    @Override
    public String toString() {
        return ("1:" + this.getCurrencyName() +
                " 2: " + this.getCurrencyCode() +
                " 3: " + this.getCountriesForTransactions().toString()
                );
    }

}