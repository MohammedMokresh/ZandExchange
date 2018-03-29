package com.sarafizand.sarafizand.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sarafizand.sarafizand.GlobalVariables;
import com.sarafizand.sarafizand.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class DialogProgressNewFragment extends DialogFragment {

    public ProgressBar mProgressBar;
    public TextView mTvDesc;
    public String mDesc;

    public static DialogProgressNewFragment mDialogProgressNewFragment;

    public static DialogProgressNewFragment newInstance(String desc) {

        Bundle args = new Bundle();

        mDialogProgressNewFragment = new DialogProgressNewFragment();
        args.putString("loading", desc);
        mDialogProgressNewFragment.setArguments(args);
        return mDialogProgressNewFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDesc = getArguments().getString("loading");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_progress_new, container, false);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mTvDesc = (TextView) rootView.findViewById(R.id.tv_desc);

        mProgressBar.setIndeterminate(true);

        GlobalVariables.hideKeyboard(getActivity());
        if (mDesc != null) mTvDesc.setText(mDesc);

        mProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        setCancelable(false);

        getDialog().setCanceledOnTouchOutside(false);

        if (getDialog() != null) if (getDialog().getWindow() != null) {

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        return rootView;
    }

//
//    @Override
//    public void onResume() {
//        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//
//        super.onResume();
//    }

    @Override
    public void dismiss() {
        super.dismiss();
        mDialogProgressNewFragment = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        GlobalVariables.hideKeyboard(getActivity());
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

//        InputMethodManager imm =
//                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm.isActive())
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        super.onDismiss(dialog);
//        mDialogProgressNewFragment = null;

    }

}
