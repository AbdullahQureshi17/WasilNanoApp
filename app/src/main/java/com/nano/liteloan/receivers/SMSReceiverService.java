package com.nano.liteloan.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nano.liteloan.application.MainApplication;
import com.nano.liteloan.utils.AppSignatureHashHelper;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

/**
 * Created by Muhammad Ahmad on 4/17/2019.
 */
public class SMSReceiverService extends BroadcastReceiver {

    private OTPReceiveListener otpListener;


    public void setOTPListener(OTPReceiveListener otpListener) {
        this.otpListener = otpListener;
    }

    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:

                    //This is the full message
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    /*<#> Your ExampleApp code is: 123ABC78
                    FA+9qCX9VSu*/

                    //Extract the OTP code and send to the listener

                    final AppSignatureHashHelper appSignatureHashHelper =
                            new AppSignatureHashHelper(MainApplication
                                    .getAppContext());
                    String fineMessage = message.replace(appSignatureHashHelper
                            .getAppSignatures().get(0), "");

                    message = fineMessage.replaceAll("\\D+", "");

                    if (otpListener != null) {
                        otpListener.onOTPReceived(message);
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    if (otpListener != null) {
                        otpListener.onOTPTimeOut();
                    }
                    break;

                case CommonStatusCodes.API_NOT_CONNECTED:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("API NOT CONNECTED");
                    }

                    break;

                case CommonStatusCodes.NETWORK_ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("NETWORK ERROR");
                    }

                    break;

                case CommonStatusCodes.ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("SOME THING WENT WRONG");
                    }

                    break;

            }
        }
    }

    /**
     * Created by Muhammad Ahmad on 4/17/2019.
     */
    public interface OTPReceiveListener {

        void onOTPReceived(String otp);

        void onOTPTimeOut();

        void onOTPReceivedError(String error);
    }
}
