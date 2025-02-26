package com.nano.liteloan.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

/**
 * Created by Muhammad Ahmad on 07/06/2020.
 */
@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;
    private BiometricCallBack biometricCallBack;

    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public FingerprintHandler(Context mContext, BiometricCallBack biometricCallBack) {
        context = mContext;
        this.biometricCallBack = biometricCallBack;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {

       // Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();


    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Authentication failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override


    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {


        if (biometricCallBack != null) {

            biometricCallBack.onAuthenticationSucceeded(result);
        }

    }

    public void onAuthStop() {

        cancellationSignal.cancel();
        biometricCallBack = null;
    }

    /**
     * Created by Muhammad Ahmad on 07/06/2020.
     */
    public interface BiometricCallBack {


        public void onAuthenticationSucceeded(
                FingerprintManager.AuthenticationResult result);
    }
}
