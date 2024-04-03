package com.nano.liteloan.presentation.fragment.profile;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BankInfo;
import com.nano.liteloan.info.ProfileCreateRequestInfo;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.fragment.dialoguefragment.WalletSuccessPopup;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Ahmad on 08/04/2020.
 */
public class MobileWalletFragment extends Fragment {

    private MainActivity context;
    private EditText edAccountNumber;
    private EditText edAccountTitle;
    private TextView tvMaskDesc;
    private ImageView ivJazzcash, ivEasyPaisa;

    private Spinner spBank;
    private String bank = "";
    private int bakId = 0;
    final List<BankInfo> bankList = PreferenceUtils.getInstance().getBankList();
    final List<BankInfo> wallets = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public MobileWalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_wallet, container, false);

        spBank = view.findViewById(R.id.sp_bank);


        edAccountNumber = view.findViewById(R.id.accountno);
        tvMaskDesc = view.findViewById(R.id.tv_mask_desc);

        ivJazzcash = view.findViewById(R.id.jazzcash);

//        setBankDetails();

        ivJazzcash.performClick();
//        ivJazzcash.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ivJazzcash.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_jazzcash_unclicked));
//                ivEasyPaisa.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_easypaisa_clicked));
//                for (BankInfo bankInfo : wallets) {
//                    if (bankInfo.bankName.equalsIgnoreCase("Mobilink(Jazz Cash)")) {
//                        bakId = bankInfo.bankId;
//                        bank = bankInfo.bankName;
//                        int maxLength = bankInfo.mask.length();
//                        edAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
//                        tvMaskDesc.setText(bankInfo.desc);
//                    }
//                }
//            }
//        });

        ivEasyPaisa = view.findViewById(R.id.easypaisa);
//        ivEasyPaisa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ivJazzcash.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_jazzcash_clicked));
//                ivEasyPaisa.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_easypaisa_unclicked));
//                for (BankInfo bankInfo : wallets) {
//                    if (bankInfo.bankName.equalsIgnoreCase("Telenor(Easy Paisa)")) {
//                        bakId = bankInfo.bankId;
//                        bank = bankInfo.bankName;
//                        int maxLength = bankInfo.mask.length();
//                        edAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
//                        tvMaskDesc.setText(bankInfo.desc);
//                    }
//                }
//            }
//        });

        Button btnSave = view.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isValidFields()) {
                    saveMobileWallet();
                }
            }
        });


        ImageView ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.homeFrag();
            }
        });
//        if (PreferenceUtils.getInstance().getPhoneNumber() != null) {
//
//            edAccountNumber.setText(PreferenceUtils.getInstance().getPhoneNumber());
//        }
        setBankSpinner();

        return view;
    }

//    private void setBankDetails() {
//        if (bankList != null && bankList.size() > 0) {
//            for (BankInfo bankInfo : bankList) {
//                if (bankInfo.bankName.equalsIgnoreCase("Mobilink(Jazz Cash)") ||
//                        bankInfo.bankName.equalsIgnoreCase("Telenor(Easy Paisa)")) {
//                    wallets.add(bankInfo);
//                    if (bankInfo.bankName.equalsIgnoreCase("Mobilink(Jazz Cash)")) {
//                        bakId = bankInfo.bankId;
//                        bank = bankInfo.bankName;
//                        int maxLength = bankInfo.mask.length();
//                        edAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
//                        tvMaskDesc.setText(bankInfo.desc);
//                    }
//                }
//            }
//        }
////    }


    private void saveMobileWallet() {

//        if (PreferenceUtils.getInstance().getBankList() != null) {
//
//
//            for (BankInfo bankInfo : PreferenceUtils.getInstance().getBankList()) {
//
//                if (bankInfo.bankSortName.equalsIgnoreCase(paymentMethod)) {
//
//                    bakId = bankInfo.bankId;
//                    break;
//                }
//
//            }
//        }

        ProfileCreateRequestInfo requestInfo = new ProfileCreateRequestInfo();
        requestInfo.setAccountnumber(edAccountNumber.getText().toString());
        requestInfo.setAccounttitle(bank);
        requestInfo.setBankid(bakId);
        requestInfo.stage = "5";


        submitUserInformation(requestInfo);
    }

    private void setBankSpinner() {

        final List<String> banks = new ArrayList<>();
        final List<BankInfo> bankList = PreferenceUtils.getInstance().getBankList();

        if (bankList != null && bankList.size() > 0) {
            for (BankInfo bankInfo : bankList) {
                banks.add(bankInfo.bankName);
            }
        }


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, banks);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spBank.setAdapter(adp1);
        spBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank = banks.get(position).toString();
                bakId = bankList.get(position).bankId;
                edAccountNumber.setText("");

                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).mask != null) {

                    int maxLength = bankList.get(position).mask.length();
                    edAccountNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});


                }
                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).desc != null) {
                    tvMaskDesc.setText(bankList.get(position).desc);
                }
                if (bankList != null && bankList.size() > 0
                        && bankList.get(position).logo != null
                        && !bankList.get(position).logo.equals("")) {
                    //  Picasso.get().load(bankList.get(position).logo).into(ivBankLogo);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean isValidFields() {

        if (edAccountNumber.getText().toString().isEmpty()) {

            edAccountNumber.setError("Please enter your valid account number");
            edAccountNumber.requestFocus();
            return false;

        }

        return true;
    }

    private void submitUserInformation(ProfileCreateRequestInfo createProfile) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        UserBusiness userBusiness = new UserBusiness();
        userBusiness.setProfile(createProfile, new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                WalletSuccessPopup walletSuccessPopup = new
                        WalletSuccessPopup(context, "for adding " + bank + " account");
                walletSuccessPopup.show(getChildFragmentManager(),
                        WalletSuccessPopup.class.getName());
            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }

                AlertUtils.showAlert(getContext(), message);
            }
        });
    }

}