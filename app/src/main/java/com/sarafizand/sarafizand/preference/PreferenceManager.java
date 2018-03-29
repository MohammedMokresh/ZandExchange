package com.sarafizand.sarafizand.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    private static final String PREF_NAME = "com.sarafizand.PREF";
    private static final String KEY_PHONE_NUMBER = "com.squincy.eyewil.preference.PHONE_NUMBER";
    private static final String IS_SENDER = "is_sender";
    private static final String WAY_OF_TRANSFER = "way_of_transfer";
    private static final String PERSONAL_OR_BUISINESS = "personal_or_business";
    private static final String TO_CURRENCY = "to_currency";
    private static final String TRANSACTION_CATEGORY = "transaction_category";
    private static final String FROM_COUNTRY = "from_country";
    private static final String FROM_CURRENCIES = "from_currencies";
    private static final String BANK = "bank";
    private static final String SWIFTCODE = "swiftcode";
    private static final String CASETRANS = "casetrans";
    private static final String PORB = "porb";
    private static final String client_token = "client_token";
    private static final String PROFILE_ID = "profile_id";
    private static final String TRANSACTION_ID = "transaction_id";
    private static final String AMOUNT = "amount";
    private static final String BACK = "back";
    private static final String SELL = "sell";
    private static final String IS_MEMBER = "is_member";
    private static final String is_login = "is_login";

    private static final String BUY = "buy";
    private static final String RATE_CURRENCYID = "rate_currency_id";

    private static PreferenceManager sInstance;
    private final SharedPreferences mPref;

    private PreferenceManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferenceManager(context);
        }
    }

    public static synchronized PreferenceManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferenceManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setToCountry(String value) {
        mPref.edit()
                .putString(KEY_PHONE_NUMBER, value)
                .apply();
    }
    public String getToCountry() {
        return mPref.getString(KEY_PHONE_NUMBER, null);
    }

    public void setProfileId(String value) {
        mPref.edit()
                .putString(PROFILE_ID, value)
                .apply();
    }

    public String getProfileId() {
        return mPref.getString(PROFILE_ID, null);
    }

    public void setBuy(int value) {
        mPref.edit()
                .putInt(BUY, value)
                .apply();
    }
    public int getBuy() {
        return mPref.getInt(BUY, -1);
    }

    public void setSell(int value) {
        mPref.edit()
                .putInt(SELL, value)
                .apply();
    }
    public int getSell() {
        return mPref.getInt(SELL, -1);
    }


    public void setRateCurrencyid(int value) {
        mPref.edit()
                .putInt(RATE_CURRENCYID, value)
                .apply();
    }
    public int getRateCurrencyid() {
        return mPref.getInt(RATE_CURRENCYID, -1);
    }


    public void setClient_token(String value) {
        mPref.edit()
                .putString(client_token, value)
                .apply();
    }
    public String getClient_token() {
        return mPref.getString(client_token, null);
    }


    public void setBank(String value) {
        mPref.edit()
                .putString(BANK, value)
                .apply();
    }
    public String getBank() {
        return mPref.getString(BANK, null);
    }


    public void setSwiftcode(String value) {
        mPref.edit()
                .putString(SWIFTCODE, value)
                .apply();
    }
    public String getSwiftcode() {
        return mPref.getString(SWIFTCODE, null);
    }


    public void setFromCountry(String value) {
        mPref.edit()
                .putString(FROM_COUNTRY, value)
                .apply();
    }
    public String getFromCountry() {
        return mPref.getString(FROM_COUNTRY, null);
    }

    public void setFromCurrencies(String value) {
        mPref.edit()
                .putString(FROM_CURRENCIES, value)
                .apply();
    }
    public String getFromCurrencies() {
        return mPref.getString(FROM_CURRENCIES, null);
    }


    public void setIsMember(boolean value) {
        mPref.edit()
                .putBoolean(IS_MEMBER, value)
                .apply();
    }
    public boolean getIsMember() {
        return mPref.getBoolean(IS_MEMBER, false);
    }


    public void setIs_login(boolean value) {
        mPref.edit()
                .putBoolean(is_login, value)
                .apply();
    }
    public boolean getIs_login() {
        return mPref.getBoolean(is_login, false);
    }

    public void setBack(boolean value) {
        mPref.edit()
                .putBoolean(BACK, value)
                .apply();
    }
    public boolean getBack() {
        return mPref.getBoolean(BACK, false);
    }


    public void setIsSender(boolean value) {
        mPref.edit()
                .putBoolean(IS_SENDER, value)
                .apply();
    }
    public boolean getIsSender() {
        return mPref.getBoolean(IS_SENDER, false);
    }
    public void setWayOfTransfer(int value) {
        mPref.edit()
                .putInt(WAY_OF_TRANSFER, value)
                .apply();
    }
    public int getWayOfTransfer() {
        return mPref.getInt(WAY_OF_TRANSFER, 3);
    }


    public void setPorb(int value) {
        mPref.edit()
                .putInt(PORB, value)
                .apply();
    }
    public int getPorb() {
        return mPref.getInt(PORB, 2);
    }



    public void setCasetrans(int value) {
        mPref.edit()
                .putInt(CASETRANS, value)
                .apply();
    }
    public int getCasetrans() {
        return mPref.getInt(CASETRANS,2);
    }


    public void setTransactionId(int value) {
        mPref.edit()
                .putInt(TRANSACTION_ID, value)
                .apply();
    }
    public int getTransactionId() {
        return mPref.getInt(TRANSACTION_ID,1);
    }
    public void setAmount(int value) {
        mPref.edit()
                .putInt(AMOUNT, value)
                .apply();
    }
    public int getAmount() {
        return mPref.getInt(AMOUNT,1);
    }


//    public void setPersonalOrBuisiness(int value) {
//        mPref.edit()
//                .putInt(PERSONAL_OR_BUISINESS, value)
//                .apply();
//    }
//    public int getPersonalOrBuisiness() {
//        return mPref.getInt(PERSONAL_OR_BUISINESS, 2);
//    }


    public void setToCurrency(String value) {
        mPref.edit()
                .putString(TO_CURRENCY, value)
                .apply();
    }
    public String getToCurrency() {
        return mPref.getString(TO_CURRENCY, null);
    }



    public void setTransactionCategory(String value) {
        mPref.edit()
                .putString(TRANSACTION_CATEGORY, value)
                .apply();
    }
    public String getTransactionCategory() {
        return mPref.getString(TRANSACTION_CATEGORY, null);
    }



    public boolean clear() {
        return mPref.edit()
                .clear()
                .commit();
    }
}
