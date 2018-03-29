package com.sarafizand.sarafizand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.fragments.ChooseTransactionCategoryFragment;
import com.sarafizand.sarafizand.fragments.CurrencyFragment;
import com.sarafizand.sarafizand.fragments.MainNewsPageFragment;
import com.sarafizand.sarafizand.fragments.ProfileFragment;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


public class MainActivity extends AppCompatActivity {

    public static BottomNavigation bottomNavigation;
    FrameLayout f1,f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f1= findViewById(R.id.frame_layout);
        f2= findViewById(R.id.frame_layout2);
        f2.setVisibility(View.GONE);
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        bottomNavigation = (BottomNavigation) findViewById(R.id.BottomNavigation);
        GlobalVariables.hideKeyboard(this);
        bottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {
                switch (i1) {
                    case 0:
                        f2.setVisibility(View.GONE);
                        f1.setVisibility(View.VISIBLE);

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.frame_layout, CurrencyFragment.newInstance());
                        ft.commit();

                        break;
                    case 1:

                        SharedPreferences prefs = getSharedPreferences("zandPref", 0);
                        String myVariable = prefs.getString("accessToken", "");

                        if (myVariable=="") {
                            f2.setVisibility(View.GONE);
                            f1.setVisibility(View.VISIBLE);


                            bottomNavigation.setSelectedIndex(3, true);
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace(R.id.frame_layout, ProfileFragment.newInstance());
                            ft3.commit();
                            break;


                        } else {
                            if (PreferenceManager.getInstance().getProfileId().equals("null")){
                                f2.setVisibility(View.GONE);
                                f1.setVisibility(View.VISIBLE);


                                bottomNavigation.setSelectedIndex(3, true);
                                Toast.makeText(getApplicationContext(),"Please upload your ID to make the Transaction",Toast.LENGTH_LONG).show();
                                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();

                                ft3.replace(R.id.frame_layout, ProfileFragment.newInstance());

                                ft3.commit();
                                break;
                            }else {
                                f1.setVisibility(View.GONE);
                                f2.setVisibility(View.VISIBLE);

                                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.frame_layout2, ChooseTransactionCategoryFragment.newInstance());
                                ft1.addToBackStack(null);
                                ft1.commit();
                                break;
                            }
//                            try{
//
//                            }catch (Exception e){
//
//                            }

                        }
                    case 2:
                        f2.setVisibility(View.GONE);
                        f1.setVisibility(View.VISIBLE);

                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        ft2.replace(R.id.frame_layout, MainNewsPageFragment.newInstance());
                        ft2.commit();

                        break;

                    case 3:
                        f2.setVisibility(View.GONE);
                        f1.setVisibility(View.VISIBLE);

                        FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                        ft3.replace(R.id.frame_layout, ProfileFragment.newInstance());
                        ft3.commit();


                        break;
                }

            }

            @Override
            public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

            }
        });
        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, CurrencyFragment.newInstance());
        transaction.commit();


        // ATTENTION: This was auto-generated to handle app links.

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceManager.getInstance().getIs_login()){
            bottomNavigation.setSelectedIndex(3, true);
            PreferenceManager.getInstance().setIs_login(false);
        }
    }
//    @Override
//    public void onBackPressed() {
//        if (isTaskRoot()) {
//            DialogUtil.showCloseAppDialog(getSupportFragmentManager());
//        } else {
//            super.onBackPressed();
//
//        }
//    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            DialogUtil.showCloseAppDialog(getSupportFragmentManager());
        }
    }

}
