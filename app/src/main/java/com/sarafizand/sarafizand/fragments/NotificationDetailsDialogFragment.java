package com.sarafizand.sarafizand.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;
import com.sarafizand.sarafizand.preference.PreferenceManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationDetailsDialogFragment extends DialogFragment implements Button.OnClickListener {

    TextView titleTextView, msgTextView;
    Button doneButton;
    String title;
    String msg;
    public static NotificationDetailsDialogFragment fragment;


    public NotificationDetailsDialogFragment() {
        // Required empty public constructor
    }

    public static NotificationDetailsDialogFragment newInstance(String title,String msg) {

        Bundle args = new Bundle();

        NotificationDetailsDialogFragment fragment = new NotificationDetailsDialogFragment();
        args.putString("title", title);
        args.putString("msg", msg);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            msg=getArguments().getString("msg");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notification_details_dialog, container, false);
        titleTextView = rootView.findViewById(R.id.title);
        msgTextView = rootView.findViewById(R.id.msg);

        doneButton = rootView.findViewById(R.id.done_button);

        GlobalVariables.hideKeyboard(getActivity());
        titleTextView.setText(title);
        msgTextView.setText(msg);

        setCancelable(false);

        doneButton.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {

            case R.id.done_button:
                dismiss();
                fragment = null;
                break;


        }
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }


}
