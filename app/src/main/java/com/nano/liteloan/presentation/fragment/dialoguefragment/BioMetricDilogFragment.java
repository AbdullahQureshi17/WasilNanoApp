package com.nano.liteloan.presentation.fragment.dialoguefragment;

import android.Manifest;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.presentation.activity.Login;
import com.nano.liteloan.utils.FingerprintHandler;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */

public class BioMetricDilogFragment extends DialogFragment
        implements View.OnClickListener, FingerprintHandler.BiometricCallBack {

    private Login context;

    private static final String KEY_NAME = "AhmadFingerPrintKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private OnSuccess onSuccess;
    private FingerprintHandler helper;


    public BioMetricDilogFragment(Login context, OnSuccess onSuccess) {
        // Required empty public constructor
        this.context = context;
        this.onSuccess = onSuccess;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bio_metric_dialog, container, false);

        Button button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);

        fingerPrintSetting();

        return view;
    }


    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {

            helper.onAuthStop();
            BioMetricDilogFragment.this.dismiss();
        }
    }

    private void fingerPrintSetting() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KEYGUARD_SERVICE);
            FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);


            if (!fingerprintManager.isHardwareDetected()) {

            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT)
                    != PackageManager.PERMISSION_GRANTED) {

                // textView.setText("Please enable the fingerprint permission");
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {

                //  textView.setText("No fingerprint configured. Please register at least one " +
                //         "fingerprint in your device's Settings");
            }
            if (!keyguardManager.isKeyguardSecure()) {

                //textView.setText("Please enable lockscreen security in your device's Settings");

            } else {


                try {

                    generateKey();

                } catch (Exception e) {

                    Log.e("ahmad", e.getMessage());
                }

                if (initCipher()) {

                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);

                    helper = new FingerprintHandler(context, this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws FingerprintException {

        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");

            KeyGenerator keyGenerator = KeyGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);

            keyGenerator.init(new

                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | IOException
                | java.security.cert.CertificateException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean initCipher() {

        try {

            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return true;
        } catch (KeyPermanentlyInvalidatedException | java.security.cert.CertificateException e) {

            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    private class FingerprintException extends Exception {

        FingerprintException(Exception e) {
            super(e);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                R.style.dialogfragmentsettings);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow())
                .requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();

        if (d != null) {
            d.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        onSuccess.onBioSuccess();
        BioMetricDilogFragment.this.dismiss();

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if ((i == KeyEvent.KEYCODE_BACK)) {
                        helper.onAuthStop();
                        return false;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    /**
     * Created by Muhammad Ahmad on 07/10/2020.
     */
    public interface OnSuccess {

        void onBioSuccess();
    }
}