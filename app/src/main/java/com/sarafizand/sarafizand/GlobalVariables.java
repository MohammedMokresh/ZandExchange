package com.sarafizand.sarafizand;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.tozny.crypto.android.AesCbcWithIntegrity;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;


/**
 * Created by Mohammed on 1/10/2017.
 */

public class GlobalVariables extends Application {
    public static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {

        super.onCreate();
        PreferenceManager.initializeInstance(this);

    }

    public GlobalVariables() throws GeneralSecurityException, UnsupportedEncodingException {
    }

    private static String FCMToken;

    public String getFCMToken() {
        return FCMToken;
    }

    public void setFCMToken(String FCMToken) {
        this.FCMToken = FCMToken;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTransactionAPI() {
        return transactionAPI;
    }

    //    public String getClient_token() {
//        return client_token;
//    }
//
//    public void setClient_token(String client_token) {
//        this.client_token = client_token;
//    }
    private String transactionAPI = "http://staging-admin.zandexchange.com/api/v1/transactions/transaction/make";
    private String client_id = "$2y$10$EyQ5sftWFVxDY479yQPpFOxuzXehVv.C3RMgHnWdGXNtnX98wR6Ru";
    private String client_secret = "$2y$10$JZU5u9QLJHKYnxkOEY1CVesjr65Tr7PC2nf7mshVMU5c9uui/itf2";
    private String url = "http://staging-admin.zandexchange.com/api/v1/client/apps/auth";
    private String client_token;
    private String createFavourite = "http://staging-admin.zandexchange.com/api/v1/user/favorites/create/favorite";
    private String favouritesList = "http://staging-admin.zandexchange.com/api/v1/user/favorites/get/favorites";

    public String getCreateFavourite() {
        return createFavourite;
    }

    public String getFavouritesList() {
        return favouritesList;
    }

    public String getFavouriteDetails() {
        return favouriteDetails;
    }

    private String favouriteDetails = "http://staging-admin.zandexchange.com/api/v1/user/favorites/get/favorite/";

    public String getCurrenciesHistoryApi() {
        return currenciesHistoryApi;
    }

    private String currenciesHistoryApi = "http://staging-admin.zandexchange.com/api/v1/currencies/history/";

    public String getTransactionHisttoryURL() {
        return transactionHisttoryURL;
    }

    private String transactionHisttoryURL = "http://staging-admin.zandexchange.com/api/v1/transactions/get/history";

    public String getGetAllNews() {
        return getAllNews;
    }

    private String bookURL = "http://staging-admin.zandexchange.com/api/v1/user/currency-booking/book";

    public String getBookURL() {
        return bookURL;
    }

    public String getCancelBookURL() {
        return cancelBookURL;
    }


    public String getBookingListURL() {
        return bookingListURL;
    }

    private String bookingListURL = "http://staging-admin.zandexchange.com/api/v1/user/currency-booking/get/all";

    public String getRequestbankURL() {
        return requestbankURL;
    }

    public String getEmailVerificationCode() {
        return emailVerificationCode;
    }

    private  String emailVerificationCode="http://staging-admin.zandexchange.com/api/v1/account-verification/";
    private String requestbankURL = "http://staging-admin.zandexchange.com/api/v1/requests/bank_account";

    public String getNotificationListUrl() {
        return notificationListUrl;
    }

    private String notificationListUrl = "http://staging-admin.zandexchange.com/api/v1/user/notifications/get/all/notifications";

    private String cancelBookURL = "http://staging-admin.zandexchange.com/api/v1/user/currency-booking/cancel/";
    private String getAllNews = "http://staging-admin.zandexchange.com/api/v1/news/get/all/";

    public String getGetNewsApi() {
        return getNewsApi;
    }

    private String getNewsApi = "http://staging-admin.zandexchange.com/api/v1/news/get/";

    public String getUpdateUserProfileApi() {
        return updateUserProfileApi;
    }

    private String updateUserProfileApi = "http://staging-admin.zandexchange.com/api/v1/user/profile/update";

    public String getShowUserProfileApi() {
        return showUserProfileApi;
    }

    private String showUserProfileApi = "http://staging-admin.zandexchange.com/api/v1/user/profile/show";

    public String getSignInUrl() {
        return signInUrl;
    }

    private String signInUrl = "http://staging-admin.zandexchange.com/api/v1/user/login";

    public String getSendFCMTokenUrl() {
        return sendFCMTokenUrl;
    }

    private String sendFCMTokenUrl = "http://staging-admin.zandexchange.com/api/v1/firebase/store/token";

    public String getUrlRegister() {
        return urlRegister;
    }

    private String urlRegister = "http://staging-admin.zandexchange.com/api/v1/user/register";

    private String currenciesApi = "http://staging-admin.zandexchange.com/api/v1/currencies/get/";

    public String getRateApi() {
        return rateApi;
    }

    private String rateApi = "http://staging-admin.zandexchange.com/api/v1/transactions/currency/get/";

    public String getCurrenciesApi() {
        return currenciesApi;
    }

    public String getCurrenciesCategoryURL() {
        return currenciesCategoryURL;
    }

    private String currenciesCategoryURL = "http://staging-admin.zandexchange.com/api/v1/currencies/categories";
    private String resetPasswordURL = "http://staging-admin.zandexchange.com/api/v1/password/reset_password";

    public String getResetSubmitPasswordURL() {
        return resetSubmitPasswordURL;
    }

    private String resetSubmitPasswordURL = "http://staging-admin.zandexchange.com/api/v1/password/submit_reset_password";

    public String getResetPasswordURL() {
        return resetPasswordURL;
    }

    public String getBarear() {
        return barear;
    }

    private String barear = "Bearer ";

    public String getTransActionURL() {
        return transActionURL;
    }

    public String getChangePasswordURL() {
        return changePasswordURL;
    }

    private String changePasswordURL = "http://staging-admin.zandexchange.com/api/v1/password/change_password";
    private String transActionURL = "http://staging-admin.zandexchange.com/api/v1/transaction/test/make";

    public AesCbcWithIntegrity.SecretKeys getKeys() {
        return keys;
    }

    AesCbcWithIntegrity.SecretKeys keys = AesCbcWithIntegrity.generateKey();


    AesCbcWithIntegrity.CipherTextIvMac cipherTextClientId = AesCbcWithIntegrity.encrypt(client_id, keys);
    //store or send to server
    String cipherTextClientIdString = cipherTextClientId.toString();

    AesCbcWithIntegrity.CipherTextIvMac cipherTextClientSecret = AesCbcWithIntegrity.encrypt(client_secret, keys);
    //store or send to server
    String cipherTextClientSecretString = cipherTextClientSecret.toString();

    public String getCipherTextClientIdString() {
        return cipherTextClientIdString;
    }

    public String getCipherTextClientSecretString() {
        return cipherTextClientSecretString;
    }
}
