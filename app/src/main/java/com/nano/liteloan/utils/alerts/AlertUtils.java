package com.nano.liteloan.utils.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.nano.liteloan.R;
import com.victor.loading.rotate.RotateLoading;

/**
 * Created by Muhammad Ahmad.
 */
public class AlertUtils {


    private static RotateLoading rotateLoading;

    public static void showAlert(final Context context, String message) {


        if (message.equalsIgnoreCase("timeout")) {

        } else {
            try {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.dialog_title_text_layout, null);

                TextView textView = view.findViewById(R.id.tv_dialog_title);
                textView.setText("Alert message");
                TextView tvMessage = view.findViewById(R.id.tv_message);
                tvMessage.setText(message);

                AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setCustomTitle(view)
                        //  .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ok", null).show();

            } catch (WindowManager.BadTokenException e) {
                Log.e("ahmad", e.getMessage());
            }
        }

    }

    public static void showConfirmAlert(final Context context, String message,
                                        DialogInterface.OnClickListener onClickListener) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_title_text_layout, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Success message.!");

            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .setPositiveButton("Ok", onClickListener).show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }


    public static AlertDialog showLoader(final Context context) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.loadingloader, null);

            rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
            rotateLoading.start();

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.CustomDialog)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)

                    .setCancelable(false)
                    .show();

            return dialog;
        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }

        return null;
    }

    public static void showAlert(final Context context, String message,
                                   DialogInterface.OnClickListener onClickListener) {

        if (message.equalsIgnoreCase("timeout")) {

        } else {
            try {
                LayoutInflater li = LayoutInflater.from(context);
                View view = li.inflate(R.layout.dialog_title_text_layout, null);

                TextView textView = view.findViewById(R.id.tv_dialog_title);
                textView.setText("Alert message");

                TextView tvMessage = view.findViewById(R.id.tv_message);
                tvMessage.setText(message);

                AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setCustomTitle(view)
                        //  .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("Ok", onClickListener).show();

            } catch (WindowManager.BadTokenException e) {
                Log.e("ahmad", e.getMessage());
            }

        }

    }

    public static void showAlertYesNo(final Context context, String message,
                                 DialogInterface.OnClickListener onClickListener) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.dialog_title_text_layout, null);

            TextView textView = view.findViewById(R.id.tv_dialog_title);
            textView.setText("Alert message");

            TextView tvMessage = view.findViewById(R.id.tv_message);
            tvMessage.setText(message);

            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    //  .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .setPositiveButton("Yes", onClickListener)
                    .setNegativeButton("No", null)
                    .show();

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());
        }


    }

    public static void showBioMetricDialog(final Context context,
                                           final View.OnClickListener onClickYes, final View.OnClickListener onClickNo) {

        try {
            LayoutInflater li = LayoutInflater.from(context);
            View view = li.inflate(R.layout.bio_metric_layout, null);

            Button btnNo = view.findViewById(R.id.btn_no);
            Button btnYes = view.findViewById(R.id.btn_yes);


            final AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
                    .setCustomTitle(view)
                    .setCancelable(false)
                    .show();

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickNo.onClick(view);
                    dialog.dismiss();

                }
            });

            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickYes.onClick(view);

                    dialog.dismiss();
                }
            });

        } catch (WindowManager.BadTokenException e) {
            Log.e("ahmad", e.getMessage());

        }
    }

//    public static void userExistAlertYesNo(final Context context, String message,
//                                      DialogInterface.OnClickListener onClickListener) {
//
//        try {
//            LayoutInflater li = LayoutInflater.from(context);
//            View view = li.inflate(R.layout.dialog_title_text_layout, null);
//
//            TextView textView = view.findViewById(R.id.tv_dialog_title);
//            textView.setText("No user found , Will you like to add ?");
//
//            AlertDialog dialog = new AlertDialog.Builder(context, R.style.MyDialogTheme)
//                    .setCustomTitle(view)
//                    //  .setIcon(R.mipmap.ic_launcher)
//                    .setMessage(message)
//                    .setCancelable(false)
//                    .setPositiveButton("Yes", onClickListener)
//                    .setNegativeButton("No", null)
//                    .show();
//
//        } catch (WindowManager.BadTokenException e) {
//            Log.e("ahmad", e.getMessage());
//        }
//
//
//    }


}
