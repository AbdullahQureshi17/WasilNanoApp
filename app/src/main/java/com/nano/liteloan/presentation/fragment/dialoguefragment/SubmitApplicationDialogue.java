package com.nano.liteloan.presentation.fragment.dialoguefragment;

import static android.view.View.GONE;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.ProductInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */

public class SubmitApplicationDialogue
        extends DialogFragment implements View.OnClickListener {

    private EditText edAmount;
    private Spinner spProduct;
    private LoanCategoryInfo loanCategoryInfo;
    private int catId;
    private String salary = "No Earning";
    private String productId;
    private OnApplicationSubmit onApplicationSubmit;
    private CardView cvPurpose;
    String otherPurpose = "";
    private EditText edOtherPurpose;
    private Spinner spSalary;
    private Spinner spCloseLoan;
    private int activeLoan = 0;
    String loanActive = "";
    LinearLayout banknameLayout, amountLayout, llmontlyrepayment;
    private EditText amount;
    private EditText monthlyRepayment;

    EditText edFincome;
    private MainActivity context;


    public SubmitApplicationDialogue(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.submit_loan_dialog, container, false);
        ImageView ivClose = (ImageView) view.findViewById(R.id.close);
        ivClose.setOnClickListener(this);

        monthlyRepayment = view.findViewById(R.id.edmonthlyrepayment);

        amount = view.findViewById(R.id.amount);
        spSalary = view.findViewById(R.id.sp_salary);
        edFincome = view.findViewById(R.id.ed_fIncome);
        spCloseLoan = view.findViewById(R.id.sp_open_close_loan);
        banknameLayout = view.findViewById(R.id.banknamelayout);
        amountLayout = view.findViewById(R.id.amountlayout);
        llmontlyrepayment = view.findViewById(R.id.montlyrepayment);

        cvPurpose = view.findViewById(R.id.cv_purpose);

        edOtherPurpose = view.findViewById(R.id.purposeother);
        edAmount = view.findViewById(R.id.ed_amount);
        spProduct = view.findViewById(R.id.sp_product);

        Button button = view.findViewById(R.id.btn_submit);
        button.setOnClickListener(this);

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("loan_detail") != null) {
            loanCategoryInfo = (LoanCategoryInfo) this.getArguments().getSerializable("loan_detail");


//            if (loanCategoryInfo != null &&
//                    loanCategoryInfo.logo != null
//                    && !loanCategoryInfo.logo.isEmpty()) {
//
//                Picasso.get().load(loanCategoryInfo.logo).into(ivLogo);
//            }
        }

        if (loanCategoryInfo.loanLimit != null) {
            edAmount.setText(String.valueOf(loanCategoryInfo.loanLimit));
        }


        setSalarySpinner();
        setSpProduct();
        setOpenCloseLoan();


        return view;
    }

    private void setOpenCloseLoan() {

        List<String> names = new ArrayList<>();

        names.add("Yes");
        names.add("No");


        ArrayAdapter<String> adp1 = new ArrayAdapter<>(context,
                R.layout.custom_spinner_text, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCloseLoan.setAdapter(adp1);
        spCloseLoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                activeLoan = position;
                loanActive = parent.getItemAtPosition(position).toString();
                if (loanActive.equalsIgnoreCase("No")
                ) {
                    banknameLayout.setVisibility(GONE);
                    amountLayout.setVisibility(GONE);
                    llmontlyrepayment.setVisibility(GONE);

                } else if (loanActive.equalsIgnoreCase("Yes")) {
                    banknameLayout.setVisibility(GONE);
                    amountLayout.setVisibility(View.VISIBLE);
                    llmontlyrepayment.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCloseLoan.setSelection(activeLoan);


    }

    private void setSalarySpinner() {

        final List<String> salarylist = new ArrayList<>();

        salarylist.add("No Earning");
        salarylist.add("Upto 5,000");
        salarylist.add("5,001-10,000");
        salarylist.add("10,001-15,000");
        salarylist.add("15,001-20,000");
        salarylist.add("20,001-25,000");
        salarylist.add("25,001-30,000");
        salarylist.add("30,001-35,000");
        salarylist.add("35,001-40,000");
        salarylist.add("Above 40,000");


        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                R.layout.custom_spinner_text, salarylist);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spSalary.setAdapter(adp1);
        spSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                salary = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSpProduct() {

        List<String> names = new ArrayList<>();

        if (loanCategoryInfo != null
                && loanCategoryInfo.productInfoList != null) {

            for (ProductInfo productInfo : loanCategoryInfo.productInfoList) {

                names.add(productInfo.name);
            }
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, names);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProduct.setAdapter(adp1);

        spProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                catId = loanCategoryInfo.productInfoList.get(i).categoryId;
                productId = loanCategoryInfo.productInfoList.get(i).id;

                cvPurpose.setVisibility(GONE);
                if (productId == "12") {
                    cvPurpose.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                SubmitApplicationDialogue.this.dismiss();
                break;

            case R.id.btn_submit:
                if (productId == "12") {
                    if (edOtherPurpose.getText().toString() == null
                            || edOtherPurpose.getText().toString().equalsIgnoreCase("")) {
                        edOtherPurpose.setError("Please filled the field ");
                    } else {
                        otherPurpose = edOtherPurpose.getText().toString();
                        submitLoan();
                    }
                } else {
                    submitLoan();
                }

                break;

        }
    }

    private void submitLoan() {

        if (edAmount.getText().toString().isEmpty() || Integer.parseInt(edAmount.getText().toString()) < 0
                || edAmount.getText().toString().equalsIgnoreCase("")) {

            edAmount.setError("please enter a valid amount");
            return;
        }

//        if (edFincome == null || edFincome.getText().toString().equalsIgnoreCase("")
//                || edFincome.getText().toString().equalsIgnoreCase("0")) {
//            edFincome.setError("Please enter Future Income");
//
//            return;
//        }
        Integer limit = loanCategoryInfo.loanLimit;
        if (Integer.parseInt(edAmount.getText().toString()) > limit) {

            edAmount.setError("loan amount can not be greater than " + limit);
            return;
        }


        LoanApplyRequest loanApplyRequest = new LoanApplyRequest();

        loanApplyRequest.assetName = loanCategoryInfo.borrower.assetName;
        loanApplyRequest.assetPrice = loanCategoryInfo.borrower.assetPrice;
        loanApplyRequest.asstMarketValue = loanCategoryInfo.borrower.assetMarketValue;
        loanApplyRequest.profilePic = loanCategoryInfo.borrower.profileImage;

        loanApplyRequest.cateId = catId;
        loanApplyRequest.productId = productId;
        loanApplyRequest.purpose = otherPurpose;
        loanApplyRequest.amount = String.valueOf(loanCategoryInfo.loanLimit);
        loanApplyRequest.income = salary;
        loanApplyRequest.futureIncome = "";

        loanApplyRequest.activeLoan = loanActive + "";

        if (PreferenceUtils.getInstance().getCorrespondentUserDetail() != null &&
                PreferenceUtils.getInstance().getCorrespondentUserDetail().getUserid() != null) {
            loanApplyRequest.correspondantid = PreferenceUtils.getInstance().getCorrespondentUserDetail().getUserid();
        } else {
            loanApplyRequest.correspondantid = "0";
        }

        if (loanActive.equals("No")) {


            loanApplyRequest.loanamount = "0";
            loanApplyRequest.monthlyRepayment = "0";

        } else {
            if (amount.getText().toString().isEmpty() || Integer.parseInt(amount.getText().toString()) <= 0
                    || amount.getText().toString().equalsIgnoreCase("")) {

                amount.setError("please enter a valid amount");
                return;
            }
            if (monthlyRepayment.getText().toString().isEmpty() ||
                    monthlyRepayment.getText().toString().equalsIgnoreCase("")) {
                monthlyRepayment.setError("Please enter a valid amount");
                return;
            }
            loanApplyRequest.loanamount = amount.getText().toString();
            loanApplyRequest.monthlyRepayment = monthlyRepayment.getText().toString();
        }


        callSubmitLoan(loanApplyRequest);

    }

    private void callSubmitLoan(LoanApplyRequest loanApplyRequest) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();

        onApplicationSubmit.onSubmit(loanApplyRequest);
        dialog.dismiss();
        SubmitApplicationDialogue.this.dismiss();

//        business.applyForLoan(loanApplyRequest, new ResponseCallBack<LoanApplyResponse>() {
//            @Override
//            public void onSuccess(LoanApplyResponse body) {
//
//                dialog.dismiss();
//
//                if (onApplicationSubmit != null) {
//
//                    onApplicationSubmit.onSubmit();
//                }
//
//                SubmitApplicationDialogue.this.dismiss();
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                dialog.dismiss();
//
//                AlertUtils.showAlert(getActivity(), message);
//            }
//        });
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

    /**
     * Created by Muhammad Ahmad on 07/10/2020.
     */

    public interface OnApplicationSubmit {

        void onSubmit(LoanApplyRequest loanApplyRequest);
    }

    public void setOnApplicationSubmit(OnApplicationSubmit onApplicationSubmit) {
        this.onApplicationSubmit = onApplicationSubmit;
    }
}