package com.sarafizand.sarafizand.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;
import com.sarafizand.sarafizand.utils.DialogUtil;
import com.sarafizand.sarafizand.utils.InputFilterMinMax;
import com.sarafizand.sarafizand.utils.volleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionSecondFragment extends Fragment {
    EditText shepaNumberEditText, amountEditText, beneficiaryNameEditText, beneficiaryAccountNumberET, beneficiaryAddressEditText, bankAddressEditText, recieverPhoneNumberEditText, companyRegistrationNumberEditText, localBankCodeEditText, IBANEditText, institutionnumberEditText, referenceEditText, cardNumberEditText, bankNumberEditText, idNumberEdditTExt;
    TextInputLayout shepaNumberETIL, amountTIL, beneficiaryNameTIL, beneficiaryAccountNumberTIL, beneficiaryAddressTIL, bankAddressTIL, recieverPhoneNumberTIL, companyRegistrationNumberTIL, localBankCodeTIL, IBANTIL, institutionnumberTIL, referenceTIL, cardNumberTIL, bankNumberTIL, idNumberTIL;
    LinearLayout recipeImage;

    ImageView recipeImageView, recieverPassportImageView, senderPassportImageView;

    Bitmap recipeBitmap, reciverBitmap, senderBitmap;
    String recipeString, recieverString, senderString;

    CardView senderIdcardView,receiverIdCardView ;
    Button send;
    private ProgressBar progressBar;

    public static TransactionSecondFragment newInstance() {

        Bundle args = new Bundle();

        TransactionSecondFragment fragment = new TransactionSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TransactionSecondFragment() {
        // Required empty public constructor
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_transaction_second, container, false);
        amountEditText = v.findViewById(R.id.Amount);
        beneficiaryNameEditText = v.findViewById(R.id.beneficiary_name);
        beneficiaryAccountNumberET = v.findViewById(R.id.beneficiary_account);
        beneficiaryAddressEditText = v.findViewById(R.id.beneficiary_address);
        bankAddressEditText = v.findViewById(R.id.bank_address);
        recieverPhoneNumberEditText = v.findViewById(R.id.ReciverPhoneNumber);
        companyRegistrationNumberEditText = v.findViewById(R.id.company_registration_number);
        localBankCodeEditText = v.findViewById(R.id.local_bank_code);
        IBANEditText = v.findViewById(R.id.IBAN);
        institutionnumberEditText = v.findViewById(R.id.institution);
        referenceEditText = v.findViewById(R.id.Comment);
        cardNumberEditText = v.findViewById(R.id.cardNumberEditTExt);
        idNumberEdditTExt = v.findViewById(R.id.id_number_editText);
        bankNumberEditText = v.findViewById(R.id.bank_number_EditText);
        shepaNumberEditText = v.findViewById(R.id.shepa_number_EditText);
        senderIdcardView = v.findViewById(R.id.LayoutForCash2);
        receiverIdCardView = v.findViewById(R.id.LayoutForCash);

        recipeImageView = v.findViewById(R.id.recipe_imageview);
        recieverPassportImageView = v.findViewById(R.id.reciever_imageview);
        senderPassportImageView = v.findViewById(R.id.sender_imageview);
        progressBar = v.findViewById(R.id.progressBar_cyclic);
        progressBar.setVisibility(View.GONE);

        shepaNumberETIL = v.findViewById(R.id.shepa_number_til);
        idNumberTIL = v.findViewById(R.id.id_number_til);
        bankNumberTIL = v.findViewById(R.id.bank_number_til);
        cardNumberTIL = v.findViewById(R.id.card_numberTIL);
        amountTIL = v.findViewById(R.id.amount_til);
        beneficiaryNameTIL = v.findViewById(R.id.beneficiary_name_til);
        beneficiaryAccountNumberTIL = v.findViewById(R.id.beneficiary_account_til);
        beneficiaryAddressTIL = v.findViewById(R.id.beneficiary_address_til);
        bankAddressTIL = v.findViewById(R.id.bank_address_til);
        recieverPhoneNumberTIL = v.findViewById(R.id.reciever_phone_number);
        companyRegistrationNumberTIL = v.findViewById(R.id.company_registration_number_til);
        localBankCodeTIL = v.findViewById(R.id.local_bank_code_til);
        IBANTIL = v.findViewById(R.id.IBAN_til);
        institutionnumberTIL = v.findViewById(R.id.institution_til);
        referenceTIL = v.findViewById(R.id.Comment_til);
        recipeImage = v.findViewById(R.id.recipe_image);
        send = v.findViewById(R.id.send_button);


        amountEditText.addTextChangedListener(new MyTextWatcher(amountEditText));
        beneficiaryNameEditText.addTextChangedListener(new MyTextWatcher(beneficiaryNameEditText));
        beneficiaryAccountNumberET.addTextChangedListener(new MyTextWatcher(beneficiaryAccountNumberET));
        beneficiaryAddressEditText.addTextChangedListener(new MyTextWatcher(beneficiaryAddressEditText));
        bankAddressEditText.addTextChangedListener(new MyTextWatcher(bankAddressEditText));
        bankNumberEditText.addTextChangedListener(new MyTextWatcher(bankNumberEditText));
        recieverPhoneNumberEditText.addTextChangedListener(new MyTextWatcher(recieverPhoneNumberEditText));
        cardNumberEditText.addTextChangedListener(new MyTextWatcher(cardNumberEditText));
        companyRegistrationNumberEditText.addTextChangedListener(new MyTextWatcher(companyRegistrationNumberEditText));
        localBankCodeEditText.addTextChangedListener(new MyTextWatcher(localBankCodeEditText));
        IBANEditText.addTextChangedListener(new MyTextWatcher(IBANEditText));
        institutionnumberEditText.addTextChangedListener(new MyTextWatcher(institutionnumberEditText));
        idNumberEdditTExt.addTextChangedListener(new MyTextWatcher(idNumberEdditTExt));
        shepaNumberEditText.addTextChangedListener(new MyTextWatcher(shepaNumberEditText));

        GlobalVariables.hideKeyboard(getActivity());
        recipeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 1);

            }
        });
        recieverPassportImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 2);

            }
        });
        senderPassportImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(intent, 3);

            }
        });


        if (PreferenceManager.getInstance().getIsSender()) {


            ((ViewManager) senderIdcardView.getParent()).removeView(senderIdcardView);
        }else {
            ((ViewManager) receiverIdCardView.getParent()).removeView(receiverIdCardView);
        }
        switch (PreferenceManager.getInstance().getTransactionCategory()) {
            case "cat1":
                //m to ir
                if (PreferenceManager.getInstance().getCasetrans() == 0) {
                    ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                    ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                    ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                    ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                    ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                    ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                    ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                    ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            submitFormMalaysiaToIran();


                        }
                    });


                } else {
                    ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                    ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                    ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                    ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                    ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                    ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                    ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                    ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                    ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                    if (PreferenceManager.getInstance().getWayOfTransfer()==2){
                        ((ViewManager) beneficiaryAccountNumberTIL.getParent()).removeView(beneficiaryAccountNumberTIL);
                    }
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            submitFormIranToMalaysia();
                        }
                    });


                }
                break;

            case "cat2":
                if (PreferenceManager.getInstance().getToCurrency().equals("AED")) {
                    getRateApi("AED");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("AUD") && PreferenceManager.getInstance().getWayOfTransfer() == 0) {
                    getRateApi("AUD-Bank");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("AUD") && PreferenceManager.getInstance().getWayOfTransfer() == 1) {
                    getRateApi("AUD-Deposit");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("GBP") && PreferenceManager.getInstance().getWayOfTransfer() == 0) {
                    getRateApi("GBP-Bank");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("GBP") && PreferenceManager.getInstance().getWayOfTransfer() == 1) {
                    getRateApi("GBP-Deposit");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("CAD") && PreferenceManager.getInstance().getWayOfTransfer() == 0) {
                    getRateApi("CAD-Bank");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("CAD") && PreferenceManager.getInstance().getWayOfTransfer() == 1) {
                    getRateApi("CAD-Deposit");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("EUR")) {
                    getRateApi("EUR");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("IRR")) {
                    getRateApi("IRR");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("USD") && PreferenceManager.getInstance().getPorb() == 0) {
                    getRateApi("USD-Personal");
                } else if (PreferenceManager.getInstance().getToCurrency().equals("USD") && PreferenceManager.getInstance().getPorb() == 1) {
                    getRateApi("USD-Business");
                }else {
                    getRateApi("AED");
                }
                switch (PreferenceManager.getInstance().getToCurrency()) {
                    case "CAD":

                        if (PreferenceManager.getInstance().getWayOfTransfer() == 1) {
                            ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                            ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                            ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                            ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                            ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                            ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                            ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                            ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);

                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    submitFormTTCanadaDeposit();
                                }
                            });

                        } else {
                            ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                            ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                            ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                            ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                            ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                            ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);

                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    submitFormTTCanada();
                                }
                            });

                        }

                        break;

                    case "AUD":

                        ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                        ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                        ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                        ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                        ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                        ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                        ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                        ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                submitFormTTAustralia();
                            }
                        });


                        break;

                    case "GBP":
                        ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                        ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                        ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                        ((ViewManager) recieverPhoneNumberTIL.getParent()).removeView(recieverPhoneNumberTIL);
                        ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                        ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                        ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                        ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                        ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                        ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                submitFormTTUK();
                            }
                        });


                        break;

                    case "USD":
                        if (PreferenceManager.getInstance().getPorb() == 1) {
                            switch (PreferenceManager.getInstance().getToCountry()) {
                                case "USA":
                                    ((ViewManager) bankAddressTIL.getParent()).removeView(bankNumberTIL);
                                    ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                                    ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                                    ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                                    ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                                    ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                                    send.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            submitFormTTUSABUISENESS();
                                        }
                                    });
                                    break;


                                default:
                                    ((ViewManager) bankAddressTIL.getParent()).removeView(bankNumberTIL);
                                    ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                                    ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                                    ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                                    ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                                    ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                                    ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                                    send.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            submitFormTTUSABUISENESSNotUsa();
                                        }
                                    });
                                    break;

                            }


                        } else {
                            ((ViewManager) bankAddressTIL.getParent()).removeView(bankNumberTIL);
                            ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                            ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                            ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                            ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                            ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                            ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    submitFormTTUSAPersonal();
                                }
                            });
                        }

                        break;


                    case "EUR":
                        ((ViewManager) beneficiaryAccountNumberTIL.getParent()).removeView(beneficiaryAccountNumberTIL);
                        ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                        ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                        ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                        ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                        ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                        ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                        ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                submitFormEuro();
                            }
                        });


                        break;


                    default:

                        ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                        ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                        ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                        ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                        ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                        ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                        ((ViewManager) idNumberTIL.getParent()).removeView(idNumberTIL);
                        ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                        ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                        ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                submitFormDefaultTT();
                            }
                        });

                        break;


                }

                break;
            case "cat3":
                ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                ((ViewManager) beneficiaryAccountNumberTIL.getParent()).removeView(beneficiaryAccountNumberTIL);
                ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitFormShirazBranch();
                    }
                });


                break;

            case "cat4":
                ((ViewManager) bankNumberTIL.getParent()).removeView(bankNumberTIL);
                ((ViewManager) cardNumberTIL.getParent()).removeView(cardNumberTIL);
                ((ViewManager) companyRegistrationNumberTIL.getParent()).removeView(companyRegistrationNumberTIL);
                ((ViewManager) localBankCodeTIL.getParent()).removeView(localBankCodeTIL);
                ((ViewManager) IBANTIL.getParent()).removeView(IBANTIL);
                ((ViewManager) institutionnumberTIL.getParent()).removeView(institutionnumberTIL);
                ((ViewManager) beneficiaryAccountNumberTIL.getParent()).removeView(beneficiaryAccountNumberTIL);
                ((ViewManager) beneficiaryAddressTIL.getParent()).removeView(beneficiaryAddressTIL);
                ((ViewManager) bankAddressTIL.getParent()).removeView(bankAddressTIL);
                ((ViewManager) shepaNumberETIL.getParent()).removeView(shepaNumberETIL);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submitFormTahranBranch();
                    }
                });
                break;


        }

        return v;
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
                recipeBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                recipeImageView.setImageBitmap(recipeBitmap);
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }


        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }


            try {
                reciverBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                recieverPassportImageView.setImageBitmap(reciverBitmap);
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }

        if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }

            try {
                senderBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                senderPassportImageView.setImageBitmap(senderBitmap);
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

    private void submitFormTTAustralia() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateLocalBankCode()) {
            return;
        }

        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            australiasender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("6"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            australiaReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("6"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                   PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap));

        }


    }

    private void submitFormTTCanada() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateLocalBankCode()) {
            return;
        }


        if (!validateInstitutionNumber()) {
            return;
        }

        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }


        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }

            canadaBanksender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("4"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), bankAddressEditText.getText().toString(), institutionnumberEditText.getText().toString()
                    , PreferenceManager.getInstance().getSwiftcode());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            canadaBankReceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("4"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), bankAddressEditText.getText().toString(), institutionnumberEditText.getText().toString()
                    , PreferenceManager.getInstance().getSwiftcode(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap));

        }
    }

    private void submitFormTTCanadaDeposit() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateLocalBankCode()) {
            return;
        }


        if (!validateInstitutionNumber()) {
            return;
        }

        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            canadaDepositsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(),5, PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            canadaDepositReceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("5"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(senderBitmap));

        }
    }

    private void submitFormTTUK() {

        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }


        if (!validateLocalBankCode()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            UKsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("7"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            UKReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("7"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap));

        }


    }

    private void submitFormTTUSABUISENESSNotUsa() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateCompanyRegistrationNumberr()) {
            return;
        }

        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            usaBuisinessNotUSAsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("1"), PreferenceManager.getInstance().getFromCurrencies(),
                    true, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString(), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            usaBuisinessNotUsaReceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("1"), PreferenceManager.getInstance().getFromCurrencies(),
                    true, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(senderBitmap), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        }

    }

    private void submitFormTTUSABUISENESS() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateCompanyRegistrationNumberr()) {
            return;
        }

        if (!validateLocalBankCode()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            usaBuisinessToUSAsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("1"), PreferenceManager.getInstance().getFromCurrencies(),
                    true, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString(), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            usaBuisinessToUsaReceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("1"), PreferenceManager.getInstance().getFromCurrencies(),
                    true, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(senderBitmap), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        }

    }

    private void submitFormTTUSAPersonal() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }

        if (!validateLocalBankCode()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            personalUSAsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("2"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString(), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            personalUsaReceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank()
                    , beneficiaryAccountNumberET.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("2"), PreferenceManager.getInstance().getFromCurrencies(),
                    true, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), institutionnumberEditText.getText().toString()
                    , "data:image/jpeg;base64," + encodeTobase64(senderBitmap), bankAddressEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), companyRegistrationNumberEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        }

    }

    private void submitFormEuro() {
        if (!validateAmount()) {
            return;
        }
        if (Integer.parseInt(amountEditText.getText().toString())<1000){
            Toast.makeText( getContext(),"The amount should be more than 1000",Toast.LENGTH_LONG).show();
            return;
        }
        if (!validateBeneficiaryName()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateIBAN()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            eurosender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), IBANEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("3"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), bankAddressEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            euroReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), IBANEditText.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("3"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap)
                    , bankAddressEditText.getText().toString(), PreferenceManager.getInstance().getSwiftcode());

        }


    }

    private void submitFormDefaultTT() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            defaultsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("0"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            defaultReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , PreferenceManager.getInstance().getToCountry(), Integer.parseInt("0"), PreferenceManager.getInstance().getFromCurrencies(),
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap));

        }
    }

    private void submitFormMalaysiaToIran() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }


        if (!validateBankNumber()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        if (!validateCardNumber()) {
            return;
        }
        if (!validateShepaNumber()) {
            return;
        }

        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            malaysisaToIransender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("9"), "MYR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    "IRR", cardNumberEditText.getText().toString(), shepaNumberEditText.getText().toString());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            malaysisaToIranreceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("9"), "MYR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    "IRR", "data:image/jpeg;base64," + encodeTobase64(senderBitmap), cardNumberEditText.getText().toString(), shepaNumberEditText.getText().toString());

        }

    }

    private void submitFormIranToMalaysia() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateIdNumber()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            iranToMalaysisaender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "MY", Integer.parseInt("8"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    "MYR", idNumberEdditTExt.getText().toString());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            iranToMalaysisareceiver(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "MY", Integer.parseInt("8"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    "MYR", "data:image/jpeg;base64," + encodeTobase64(senderBitmap), idNumberEdditTExt.getText().toString());

        }


    }

    private void submitFormShirazBranch() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateIdNumber()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            shirazsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("10"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), idNumberEdditTExt.getText().toString());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            shirazReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("10"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap), idNumberEdditTExt.getText().toString());

        }


    }

    private void submitFormTahranBranch() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }


        if (!validatePhone()) {
            return;
        }


        if (!validateIdNumber()) {
            return;
        }


        try {
            recipeString = encodeTobase64(recipeBitmap);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please attach the recipe picture", Toast.LENGTH_LONG).show();
            return;
        }



        if (PreferenceManager.getInstance().getIsSender()) {
            try {
                recieverString = encodeTobase64(reciverBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the receiver id picture", Toast.LENGTH_LONG).show();
                return;
            }
            shirazsender(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("11"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), idNumberEdditTExt.getText().toString());

        } else {
            try {
                senderString = encodeTobase64(senderBitmap);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Please attach the sender id picture", Toast.LENGTH_LONG).show();
                return;
            }
            shirazReciever(Integer.parseInt(amountEditText.getText().toString()), PreferenceManager.getInstance().getBank(), beneficiaryAccountNumberET.getText().toString()
                    , beneficiaryAddressEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    beneficiaryNameEditText.getText().toString(), PreferenceManager.getInstance().getWayOfTransfer()
                    , "IR", Integer.parseInt("11"), "IRR",
                    false, PreferenceManager.getInstance().getIsSender(), localBankCodeEditText.getText().toString(),
                    PreferenceManager.getInstance().getRateCurrencyid(), referenceEditText.getText().toString(), recieverPhoneNumberEditText.getText().toString(), "data:image/jpeg;base64," + encodeTobase64(recipeBitmap),
                    PreferenceManager.getInstance().getToCurrency(), "data:image/jpeg;base64," + encodeTobase64(senderBitmap), idNumberEdditTExt.getText().toString());

        }


    }

    private void submitForm() {
        if (!validateAmount()) {
            return;
        }

        if (!validateBeneficiaryName()) {
            return;
        }
        if (!validateBeneficiaryAccountNumber()) {
            return;
        }

        if (!validateBeneficiaryAddress()) {
            return;
        }

        if (!validateBankAddress()) {
            return;
        }

        if (!validateBankNumber()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }

        if (!validateCardNumber()) {
            return;
        }

        if (!validateCompanyRegistrationNumberr()) {
            return;
        }

        if (!validateLocalBankCode()) {
            return;
        }

        if (!validateIBAN()) {
            return;
        }

        if (!validateInstitutionNumber()) {
            return;
        }

        if (!validateIdNumber()) {
            return;
        }


        try {
//            SignINCall(email, pass);
            Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {

        }
    }


    private boolean validateAmount() {
        if (amountEditText.getText().toString().trim().isEmpty()) {
            amountTIL.setError(getString(R.string.empty_amount));
            requestFocus(amountEditText);
            return false;
        } else {
            amountTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateBeneficiaryName() {
        if (beneficiaryNameEditText.getText().toString().trim().isEmpty()) {
            beneficiaryNameTIL.setError(getString(R.string.empty_beneficiary_name));
            requestFocus(beneficiaryNameEditText);
            return false;
        } else {
            beneficiaryNameTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateBeneficiaryAccountNumber() {
        if (beneficiaryAccountNumberET.getText().toString().trim().isEmpty()) {
            beneficiaryAccountNumberTIL.setError(getString(R.string.empty_beneficiary_account_number));
            requestFocus(beneficiaryAccountNumberET);
            return false;
        } else {
            beneficiaryAccountNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateBeneficiaryAddress() {
        if (beneficiaryAddressEditText.getText().toString().trim().isEmpty()) {
            beneficiaryAddressTIL.setError(getString(R.string.empty_Beneficiary_address));
            requestFocus(beneficiaryAddressEditText);
            return false;
        } else {
            beneficiaryAddressTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateBankAddress() {
        if (bankAddressEditText.getText().toString().trim().isEmpty()) {
            bankAddressTIL.setError(getString(R.string.empty_bank_address));
            requestFocus(bankAddressEditText);
            return false;
        } else {
            bankAddressTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateBankNumber() {
        if (bankNumberEditText.getText().toString().trim().isEmpty()) {
            bankNumberTIL.setError(getString(R.string.empty_bank_number));
            requestFocus(bankNumberEditText);
            return false;
        } else {
            bankNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validatePhone() {
        if (recieverPhoneNumberEditText.getText().toString().trim().isEmpty()) {
            recieverPhoneNumberTIL.setError(getString(R.string.empty_tel));
            requestFocus(recieverPhoneNumberEditText);
            return false;
        } else {
            recieverPhoneNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateCardNumber() {
        if (cardNumberEditText.getText().toString().trim().isEmpty()) {
            cardNumberTIL.setError(getString(R.string.empty_card_number));
            requestFocus(cardNumberEditText);
            return false;
        } else {
            cardNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateCompanyRegistrationNumberr() {
        if (companyRegistrationNumberEditText.getText().toString().trim().isEmpty()) {
            companyRegistrationNumberTIL.setError(getString(R.string.empty_CRN));
            requestFocus(companyRegistrationNumberEditText);
            return false;
        } else {
            companyRegistrationNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateLocalBankCode() {
        if (localBankCodeEditText.getText().toString().trim().isEmpty()) {
            localBankCodeTIL.setError(getString(R.string.empty_local_bank_code));
            requestFocus(localBankCodeEditText);
            return false;
        } else {
            localBankCodeTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateIBAN() {
        if (IBANEditText.getText().toString().trim().isEmpty()) {
            IBANTIL.setError(getString(R.string.empty_IBAN));
            requestFocus(IBANEditText);
            return false;
        } else {
            IBANTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateInstitutionNumber() {
        if (institutionnumberEditText.getText().toString().trim().isEmpty()) {
            institutionnumberTIL.setError(getString(R.string.empty_institution));
            requestFocus(institutionnumberEditText);
            return false;
        } else {
            institutionnumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateIdNumber() {
        if (idNumberEdditTExt.getText().toString().trim().isEmpty()) {
            idNumberTIL.setError(getString(R.string.empty_id_number));
            requestFocus(idNumberEdditTExt);
            return false;
        } else {
            idNumberTIL.setErrorEnabled(false);
        }

        return true;
    }

    public boolean validateShepaNumber() {
        if (shepaNumberEditText.getText().toString().trim().isEmpty()) {
            shepaNumberETIL.setError(getString(R.string.empty_shepa_number));
            requestFocus(shepaNumberEditText);
            return false;
        } else {
            shepaNumberETIL.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    ////// Australia
    private void australiaReciever(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        PreferenceManager.getInstance().setAmount(amount);
                        try {
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void australiasender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PreferenceManager.getInstance().setAmount(amount);
                        try {
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    //////canada
    private void canadaBankReceiver(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String bankAddress, String institutionNumber, String swift, String senderID) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("institution_no", institutionNumber);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("swift", swift);
        params.put("sender_id", senderID);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void canadaBanksender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String bankAddress, String institutionNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("institution_no", institutionNumber);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("swift", swift);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void canadaDepositReceiver(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String senderID) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("institution_no", institutionNumber);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", senderID);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void canadaDepositsender(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("institution_no", institutionNumber);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////// UK
    private void UKReciever(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void UKsender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////// EURO
    private void euroReciever(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id, String bankAddress, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("iban", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("swift", swift);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void eurosender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency, String bankAddress, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("iban", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("swift", swift);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////////USA
    private void usaBuisinessToUsaReceiver(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String senderID, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("reg_no", registrationNumber);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", senderID);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void usaBuisinessToUSAsender(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("reg_no", registrationNumber);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void usaBuisinessNotUsaReceiver(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String senderID, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("reg_no", registrationNumber);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", senderID);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void usaBuisinessNotUSAsender(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("reg_no", registrationNumber);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    private void personalUsaReceiver(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String senderID, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", senderID);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void personalUSAsender(final int amount, String bankName, String benAccountNumber, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String institutionNumber, String bankAddress, String benAddress, String registrationNumber, String swift) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("bank_address", bankAddress);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("swift", swift);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////// Australia
    private void defaultReciever(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void defaultsender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////// Iran to malaysia
    private void iranToMalaysisareceiver(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id, String idNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("id_no", idNumber);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void iranToMalaysisaender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency, String idNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("id_no", idNumber);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    //////  malaysia to Iran
    private void malaysisaToIranreceiver(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id, String cardNumber, String shepaNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("card_no", cardNumber);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("sheba_no", shepaNumber);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            Log.e("aa", response.toString());
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("aa", e.getMessage());
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void malaysisaToIransender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency, String cardNumber, String shepaNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("card_no", cardNumber);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("sheba_no", shepaNumber);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    ////// shiraz
    private void shirazReciever(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency
            , String sender_id, String idNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("id_no", idNumber);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);
        params.put("sender_id", sender_id);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences prefs = getActivity().getSharedPreferences("zandPref", 0);
                String myVariable = prefs.getString("accessToken", "");
                Log.e("token", myVariable);
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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void shirazsender(final int amount, String bankName, String benAccountNumber, String benAddress, String benID
            , String benName, int collection, String country, int formId, String fromCurrencies, boolean isBuisiness
            , boolean isSender, String localBankCode, int rate, String ref, String tel, String recipt, String toCurrency, String idNumber) {
        Map<String, Object> m = new HashMap<>();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", amount);
        params.put("bank", bankName);
        params.put("ben_acc_no", benAccountNumber);
        params.put("ben_address", benAddress);
        params.put("ben_id", benID);
        params.put("ben_name", benName);
        params.put("collection", collection);
        params.put("country", country);
        params.put("id_no", idNumber);
        params.put("form_id", formId);
        params.put("from_currency", fromCurrencies);
        params.put("is_business", isBuisiness);
        params.put("is_sender", isSender);
        params.put("local_bank_code", localBankCode);
        params.put("rate", rate);
        params.put("ref", ref);
        params.put("tel", tel);
        params.put("tx_receipt", recipt);
        params.put("to_currency", toCurrency);

        JSONObject js = new JSONObject(params);
        Log.e("test", js.toString());
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.POST
                , ((GlobalVariables) getActivity().getApplication()).getTransactionAPI(), js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            PreferenceManager.getInstance().setAmount(amount);
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                            PreferenceManager.getInstance().setTransactionId(response.getInt("transaction_id"));
                            DialogUtil.showAskForFavouriteDialog(getFragmentManager());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.e("res", error.toString());

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
        progressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    public void getRateApi(final String code) {
        JsonObjectRequest jss = new JsonObjectRequest(Request.Method.GET, ((GlobalVariables) getActivity().getApplication()).getRateApi() + code,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("res", response.toString());

                try {

                    JSONObject s = response.getJSONObject("currency");
                    int currencyid = s.getInt("id");
                    int sellPrice = s.getInt("sell");


                    PreferenceManager.getInstance().setRateCurrencyid(currencyid);
                    PreferenceManager.getInstance().setSell(sellPrice);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    }


    private class MyTextWatcher implements TextWatcher {

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
                case R.id.Amount:
                    validateAmount();
                    break;
                case R.id.beneficiary_name:
                    validateBeneficiaryName();
                    break;
                case R.id.beneficiary_account:
                    validateBeneficiaryAccountNumber();
                    break;
                case R.id.beneficiary_address:
                    validateBeneficiaryAddress();
                    break;
                case R.id.bank_address:
                    validateBankAddress();
                    break;
                case R.id.bank_number_EditText:
                    validateBankNumber();
                    break;
                case R.id.ReciverPhoneNumber:
                    validatePhone();
                    break;
                case R.id.cardNumberEditTExt:
                    validateCardNumber();
                    break;
                case R.id.company_registration_number:
                    validateCompanyRegistrationNumberr();
                    break;
                case R.id.local_bank_code:
                    validateLocalBankCode();
                    break;
                case R.id.IBAN:
                    validateIBAN();
                    break;
                case R.id.institution:
                    validateInstitutionNumber();
                    break;
                case R.id.id_number_editText:
                    validateIdNumber();
                    break;
                case R.id.shepa_number_EditText:
                    validateShepaNumber();
                    break;

            }
        }

    }


}
