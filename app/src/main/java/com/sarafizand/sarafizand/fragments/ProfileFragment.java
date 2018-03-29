package com.sarafizand.sarafizand.fragments;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.activities.BookingListActivity;
import com.sarafizand.sarafizand.activities.FavouritesActivity;
import com.sarafizand.sarafizand.activities.LoginActivity;
import com.sarafizand.sarafizand.activities.NotificationsActivity;
import com.sarafizand.sarafizand.activities.SentAndReceiveHistoryActivity;
import com.sarafizand.sarafizand.activities.TransactionsHistoryActivity;
import com.sarafizand.sarafizand.activities.changePasswordActivity;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.volleySingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    EditText userNameEdittext;
    EditText phoneEditText;
    EditText DoB;
    TextInputLayout userNameTextInputLayout, dateTextInputLayout, phoneTextInputLayout;
    Calendar myCalendar = Calendar.getInstance();
    TextView uploadCardID;
    Button updateInformationButton;
    ImageView ShowSelectedImage;
    Bitmap FixBitmap;
    ImageView noCard;
    LinearLayout ll;
    RelativeLayout rl;
    RelativeLayout imgRL;
    String nameString, emailString, phoneString, addressString, dobString, idString;
    private Button make;
    View v;
    private ProgressBar progressBar;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_profile, container, false);

        //define the views
        updateInformationButton = v.findViewById(R.id.updateButton);
        userNameTextInputLayout = v.findViewById(R.id.user_name_TextInputLayout);
        dateTextInputLayout = v.findViewById(R.id.date_TextInputLayout);
        phoneTextInputLayout = v.findViewById(R.id.phone_TextInputLayout);
        userNameEdittext = v.findViewById(R.id.username);
        DoB = v.findViewById(R.id.DateOfBirth);
        phoneEditText = v.findViewById(R.id.phone);
        ShowSelectedImage = v.findViewById(R.id.imageIdCard);
        uploadCardID = v.findViewById(R.id.card_id);
        Toolbar toolbar = v.findViewById(R.id.toolbar_profile);
        ll = v.findViewById(R.id.mo);
        rl = v.findViewById(R.id.relativeLaySignIn);
        make = v.findViewById(R.id.signIn);
        progressBar = v.findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);
        noCard=v.findViewById(R.id.no_card);
        imgRL=v.findViewById(R.id.img);
        GlobalVariables.hideKeyboard(getActivity());
        //initialize
        userNameEdittext.setEnabled(false);
        DoB.setEnabled(false);
        phoneEditText.setEnabled(false);
        ShowSelectedImage.setEnabled(false);
        noCard.setEnabled(false);
        imgRL.setEnabled(false);
        updateInformationButton.setVisibility(View.GONE);

        userNameEdittext.addTextChangedListener(new MyTextWatcher(userNameEdittext));
        DoB.addTextChangedListener(new MyTextWatcher(DoB));
        phoneEditText.addTextChangedListener(new MyTextWatcher(phoneEditText));

        SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
        String myVariable = prefs.getString("accessToken", "");

        if (myVariable.equals("")) {
//                if(userNameEdittext.getText().toString().equals("")&&DoB.getText().toString().equals("")&&phoneEditText.getText().toString().equals("")&& ShowSelectedImage.getDrawable()==null)
//                {
            ll.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        } else {
            ll.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);


        }

        //initilize the toolbar items
        MenuItem item;
        MenuItem updateInfo;
        MenuItem changePassword;
        MenuItem favourite;
        MenuItem history;
        MenuItem booking;
        MenuItem notifications;
        MenuItem requestBank;
        toolbar.setTitle("Profile");
        toolbar.inflateMenu(R.menu.profile_toolbar_menu);
        Menu menu = toolbar.getMenu();
        item = menu.findItem(R.id.logout);
        item.setVisible(true);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("accessToken", "");
                PreferenceManager.getInstance().setProfileId(null);
                editor.apply();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
                return false;
            }
        });

        notifications = menu.findItem(R.id.notifications);
        notifications.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), NotificationsActivity.class);
                startActivity(i);
                return false;
            }
        });
        requestBank = menu.findItem(R.id.request_bank);
        requestBank.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DialogUtil.showRequestBankDialog(getFragmentManager());
                return false;
            }
        });

        updateInfo = menu.findItem(R.id.updateInfo);
        updateInfo.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                userNameEdittext.setEnabled(true);
                DoB.setEnabled(true);
                phoneEditText.setEnabled(true);
                imgRL.setEnabled(true);
                YoYo.with(Techniques.FadeIn)
                        .duration(700)
                        .playOn(v.findViewById(R.id.updateButton));
                updateInformationButton.setVisibility(View.VISIBLE);
                return false;
            }
        });

        changePassword = menu.findItem(R.id.changePassword);
        changePassword.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), changePasswordActivity.class);
                startActivity(i);
                return false;
            }
        });
        favourite = menu.findItem(R.id.favourites);
        favourite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), FavouritesActivity.class);
                startActivity(i);
                return false;
            }
        });
        history = menu.findItem(R.id.history);
        history.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), TransactionsHistoryActivity.class);
                startActivity(i);
                return false;
            }
        });
        booking = menu.findItem(R.id.booking);
        booking.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), BookingListActivity.class);
                startActivity(i);
                return false;
            }
        });
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        DoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
                dpd.setTitle("Select Date");


            }
        });
        imgRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 1);

            }
        });
        bringInformation();

        updateInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = userNameEdittext.getText().toString();

                dobString = DoB.getText().toString();
                phoneString = phoneEditText.getText().toString();
                try {
                    idString = encodeTobase64(FixBitmap);
                    submitForm(nameString, idString, dobString, phoneString);

                } catch (Exception e) {
                    submitForm2(nameString, dobString, phoneString);

                }


            }
        });

        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
    @Override
    public void onResume() {
        super.onResume();
        GlobalVariables.hideKeyboard(getActivity());
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        GlobalVariables.hideKeyboard(getActivity());
    }

    private void submitForm2(String name, String dob, String phone) {
        if (!validateusername()) {
            return;
        }

        if (!validatedate()) {
            return;
        }
        if (!validatephone()) {
            return;
        }
        try {
            updateInformation2(name, dob, phone);
        } catch (NullPointerException e) {

        }
    }

    private void submitForm(String name, String id, String dob, String phone) {
        if (!validateusername()) {
            return;
        }

        if (!validatedate()) {
            return;
        }
        if (!validatephone()) {
            return;
        }
        try {
            updateInformation(name, "data:image/jpeg;base64," + id, dob, phone);
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                ShowSelectedImage.setImageBitmap(FixBitmap);
                ShowSelectedImage.setVisibility(View.VISIBLE);
                noCard.setVisibility(View.GONE);
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

//        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DoB.setText(sdf.format(myCalendar.getTime()));
    }


    private boolean validateusername() {
        if (userNameEdittext.getText().toString().trim().isEmpty()) {
            userNameTextInputLayout.setError(getString(R.string.empty_name));
            requestFocus(userNameEdittext);
            return false;
        } else {
            userNameTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatedate() {
        if (DoB.getText().toString().trim().isEmpty()) {
            dateTextInputLayout.setError(getString(R.string.empty_date));
            requestFocus(DoB);
            return false;
        } else {
            dateTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatephone() {
        if (phoneEditText.getText().toString().trim().isEmpty()) {
            phoneTextInputLayout.setError(getString(R.string.empty_phone));
            requestFocus(phoneEditText);
            return false;
        } else {
            phoneTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void updateInformation2(String name, String dob, String phonenum) {
        Map<String, String> params = new HashMap<String, String>();

//        params.put("address", addres);
        params.put("phone", phonenum);
        params.put("dob", dob);
        params.put("name", name);
        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getActivity().getApplication()).getUpdateUserProfileApi(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("hello",response.toString());

                try {
                    String ss = response.getString("message");
//                    Snackbar.make(v, ss, Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getContext(), ss, Toast.LENGTH_LONG).show();
                    updateInformationButton.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DialogUtil.removeProgressDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                DialogUtil.removeProgressDialog();


            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("access", myVariable);

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myVariable);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading", getFragmentManager());
    }

    private void updateInformation(String name, final String nationalId, String dob, String phonenum) {
        Log.e("mmm", nationalId);

        Map<String, String> params = new HashMap<String, String>();


//      params.put("address", addres);
        params.put("phone", phonenum);
        params.put("dob", dob);
        params.put("name", name);
        params.put("national_id", nationalId);
        JSONObject js = new JSONObject(params);


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST, ((GlobalVariables) getActivity().getApplication()).getUpdateUserProfileApi(),
                js, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("asduoassdu",response.toString());

                try {
                    String ss = response.getString("message");
//                    Snackbar.make(v, ss, Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getContext(), ss, Toast.LENGTH_LONG).show();
                    PreferenceManager.getInstance().setProfileId(nationalId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DialogUtil.removeProgressDialog();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                DialogUtil.removeProgressDialog();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myVariable);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getFragmentManager());
    }

    private void bringInformation() {


        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getShowUserProfileApi(),
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONObject infoJO = response.getJSONObject("user_profile");
                    userNameEdittext.setText(infoJO.getString("name"));
                    DoB.setText(infoJO.getString("dob"));
                    phoneEditText.setText(infoJO.getString("phone"));
                    PreferenceManager.getInstance().setProfileId(infoJO.getString("national_id"));
                    if (PreferenceManager.getInstance().getProfileId().equals("null")){
                        DialogUtil.removeProgressDialog();
                    }
                    try {
                        Picasso.with(getActivity())
                                .load("http://staging-admin.zandexchange.com" + infoJO.getString("national_id")).fit()
                                .into(ShowSelectedImage, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        DialogUtil.removeProgressDialog();
                                        ShowSelectedImage .setVisibility(View.VISIBLE);
                                        noCard.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void onError() {
                                        DialogUtil.removeProgressDialog();
                                        ShowSelectedImage.setVisibility(View.INVISIBLE);
                                        noCard.setVisibility(View.VISIBLE);
                                    }
                                });

                    }catch (Exception e){
                        DialogUtil.removeProgressDialog();
                    }


                } catch (JSONException e) {
                    DialogUtil.removeProgressDialog();
                    e.printStackTrace();
                    Log.e("2", e.getMessage());
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                DialogUtil.removeProgressDialog();



            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + myVariable);
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;

            }

        };
        jss.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleySingleton.getInstance(getContext()).addToRequestQueue(jss);
        DialogUtil.showProgressDialog("Loading",getFragmentManager());

    }


    class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.username:
                    validateusername();
                    break;
                case R.id.DateOfBirth:
                    validatedate();
                    break;
                case R.id.phone:
                    validatephone();
                    break;
//                case R.id.Address:
//                    validateaddress();
//                    break;

            }
        }

    }


}
