package com.sarafizand.sarafizand.utils;

import android.support.v4.app.FragmentManager;

import com.sarafizand.sarafizand.fragments.AskForBookingDialogFragment;
import com.sarafizand.sarafizand.fragments.AskForCancelBookingFragment;
import com.sarafizand.sarafizand.fragments.AskForFavouriteFragment;
import com.sarafizand.sarafizand.fragments.BriefDialogFragment;
import com.sarafizand.sarafizand.fragments.DialogCloseAppFragment;
import com.sarafizand.sarafizand.fragments.DialogProgressNewFragment;
import com.sarafizand.sarafizand.fragments.NotificationDetailsDialogFragment;
import com.sarafizand.sarafizand.fragments.RequestBankAccountDialogFragment;

public class DialogUtil {

        public static void showCloseAppDialog(FragmentManager fragmentManager) {
        if(fragmentManager==null) return;
        if(DialogCloseAppFragment.mDialogCloseAppFragment ==null){
            DialogCloseAppFragment.newInstance().show(fragmentManager, "close_app");
        }
    }
    public static void showBrief(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;
        if (BriefDialogFragment.fragment == null) {
            BriefDialogFragment.newInstance().show(fragmentManager, "brief");
        }
    }

    public static void showAskForFavouriteDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;
        if (AskForFavouriteFragment.fragment == null) {
            AskForFavouriteFragment.newInstance().show(fragmentManager, "Ask_for_favourite");
        }
    }

    public static void showAskForCancelDialog(FragmentManager fragmentManager, String id) {
        if (fragmentManager == null) return;
        if (AskForCancelBookingFragment.fragment == null) {
            AskForCancelBookingFragment.newInstance(id).show(fragmentManager, "Ask_for_booking");
        }
    }

    public static void showAskForBookingDialog(FragmentManager fragmentManager, String id) {
        if (fragmentManager == null) return;
        if (AskForBookingDialogFragment.fragment == null) {
            AskForBookingDialogFragment.newInstance(id).show(fragmentManager, "Ask_for_booking");
        }
    }
    public static void showRequestBankDialog(FragmentManager fragmentManager) {
        if (fragmentManager == null) return;
        if (RequestBankAccountDialogFragment.fragment == null) {
            RequestBankAccountDialogFragment.newInstance().show(fragmentManager, "show_request");
        }
    }
    public static void showNotificationDetailsDialog(FragmentManager fragmentManager, String title,String msg) {
        if (fragmentManager == null) return;
        if (NotificationDetailsDialogFragment.fragment == null) {
            NotificationDetailsDialogFragment.newInstance(title,msg).show(fragmentManager, "notifications_details");
        }
    }
    public static void removeProgressDialog() {
        try {
            if (DialogProgressNewFragment.mDialogProgressNewFragment != null)
                DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
        } catch (Exception e) {
//            Log.d("X Progress Dia X", e.getMessage());
        }

    }

    public static void showProgressDialog(String desc, FragmentManager fragmentManager) {
        try {
            if (DialogProgressNewFragment.mDialogProgressNewFragment == null) {
                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, "fragment_dialog_progress");

            } else {
                DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, "fragment_dialog_progress");
            }

        } catch (Exception e) {
//            Log.d("X Progress Dia X", e.getMessage());
        }
    }
//    public static void showIncompleteFieldsDialog(FragmentManager fragmentManager){
//        if(fragmentManager == null) return;
//
//        try {
//            if(DialogIncompleteFieldsFragment.mDialogIncompleteFieldsFragment == null) {
//                DialogIncompleteFieldsFragment dialog=new DialogIncompleteFieldsFragment();
//                dialog.show(fragmentManager,EWConstants.TAG_INCOMPLETE_DIALOG_DIALOG_FRAGMENT);
//            }
//        }
//
//        catch (Exception e){}
//
//    }

//    public static void showNoInternetConnectionErrorDialog(FragmentManager fragmentManager) {
//        if(fragmentManager==null) return;
//
//        try {
//            if(DialogErrorFragment.mDialogErrorFragment ==null){
//                DialogErrorFragment
//                        .newInstance("Oops", "Looks like we were not able to connect to the internet, please try again later")
//                        .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//
//            }
//        }
//        catch (Exception e) {}
//    }

//    public static void showNoInternetConnectionErrorDialog(Activity activity, boolean isAdded, boolean isDetached, FragmentManager fragmentManager) {
//        if(activity == null) return;
//        if(fragmentManager==null) return;
//
//        try {
//            if(!activity.isFinishing() && isAdded && !isDetached){
//                if(DialogErrorFragment.mDialogErrorFragment ==null){
//                    DialogErrorFragment
//                            .newInstance("Oops", "Looks like we were not able to connect to the internet, please try again later")
//                            .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//                }
//            }
//        }
//        catch (Exception e) {}
//
//    }

//    public static void showConnectionTimeoutErrorDialog(Activity activity, FragmentManager fragmentManager) {
//        if(activity == null) return;
//        if(fragmentManager==null) return;
//
//        try {
//            if(!activity.isFinishing()){
//                if(DialogErrorFragment.mDialogErrorFragment ==null){
//                    DialogErrorFragment
//                            .newInstance("Connection Timeout", "There was a connection timeout")
//                            .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//                }
//            }
//        }
//
//        catch (Exception e) {}
//
//    }

//    public static void showConnectionTimeoutErrorDialog(Activity activity, boolean isAdded, boolean isDetached, FragmentManager fragmentManager) {
//        if(activity == null) return;
//        if(fragmentManager==null) return;
//
//        try {
//            if(!activity.isFinishing() && isAdded && !isDetached){
//                if(DialogErrorFragment.mDialogErrorFragment ==null){
//                    DialogErrorFragment
//                            .newInstance("Connection Timeout", "There was a connection timeout")
//                            .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//                }
//            }
//
//        }
//
//        catch (Exception e) {}
//
//
//    }

//    public static void showConnectionErrorDialog(Activity activity, boolean isAdded, boolean isDetached, FragmentManager fragmentManager) {
//        if(activity == null) return;
//        if(fragmentManager==null) return;
//
//        try {
//            if(!activity.isFinishing() && isAdded && !isDetached){
//                if(DialogErrorFragment.mDialogErrorFragment ==null){
//                    DialogErrorFragment
//                            .newInstance("Oops", "Error occurred due to network problem, please try again")
//                            .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//                }
//            }
//
//        }
//
//        catch (Exception e) {}
//
//    }

//    public static void showConnectionErrorDialog(Activity activity, FragmentManager fragmentManager) {
//        if(activity == null) return;
//        if(fragmentManager==null) return;
//
//        try {
//            if(!activity.isFinishing()){
//                if(DialogErrorFragment.mDialogErrorFragment ==null){
//                    DialogErrorFragment
//                            .newInstance("Oops", "Error occurred due to network problem, please try again")
//                            .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//                }
//            }
//
//        }
//        catch (Exception e){}
//
//    }

//    public static void showInvalidDatesError(FragmentManager fragmentManager) {
//        if(fragmentManager==null) return;
//
//        try {
//            if(DialogErrorFragment.mDialogErrorFragment ==null){
//                DialogErrorFragment
//                        .newInstance("Oops", "Looks like the dates you have entered are incorrect. Make sure the start date is smaller than the end date")
//                        .show(fragmentManager, EWConstants.TAG_ERROR_DIALOG_FRAGMENT);
//            }
//        }
//
//        catch (Exception e){}
//
//    }

//    public static void removeProgressDialog() {
//        try {
//            if(DialogProgressNewFragment.mDialogProgressNewFragment != null) DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
//        }
//        catch (Exception e){
////            Log.d("X Progress Dia X", e.getMessage());
//        }
//
//    }

//    public static void showProgressDialog(String desc, FragmentManager fragmentManager) {
//        try {
//            if(DialogProgressNewFragment.mDialogProgressNewFragment == null) {
//                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, EWConstants.TAG_PROGRESS_DIALOG_FRAGMENT);
//
//            }
//
//            else {
//                DialogProgressNewFragment.mDialogProgressNewFragment.dismiss();
//                DialogProgressNewFragment.newInstance(desc).show(fragmentManager, EWConstants.TAG_PROGRESS_DIALOG_FRAGMENT);
//            }
//
//        }
//        catch (Exception e){
////            Log.d("X Progress Dia X", e.getMessage());
//        }
//    }

}
