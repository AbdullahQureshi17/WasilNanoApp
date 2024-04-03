package com.nano.liteloan.presentation.fragment.businesscorrespondance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerReceiptObj;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.SearchBorrower;
import com.nano.liteloan.info.SearchBorrowerRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.SearchBorrowerAdapter;
import com.nano.liteloan.presentation.fragment.dialoguefragment.AddNewUserFragment;
import com.nano.liteloan.presentation.fragment.dialoguefragment.BorrowerExistDialgoue;
import com.nano.liteloan.presentation.fragment.dialoguefragment.PinSucessMessage;
import com.nano.liteloan.presentation.fragment.dialoguefragment.RecoveryInfoDialgoue;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.santalu.maskedittext.MaskEditText;


/**
 * Created by Muhammad Umer on 04/06/2020.
 */
public class BusinessCorrespondance extends Fragment {

    private MainActivity context;
    private MaskEditText edCnic;
    private RecyclerView recyclerView;
    private ImageView ivNoBorrower;
    private ImageView btAdd;

    private ImageView btSearch;

    public BusinessCorrespondance(MainActivity context) {
        this.context = context;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_correspondance, container, false);

        edCnic = view.findViewById(R.id.ed_cnic);

        ivNoBorrower = view.findViewById(R.id.no_borrower);

        btAdd = view.findViewById(R.id.button_add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edCnic.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edCnic, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btSearch = view.findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchUser();
            }
        });

        getBorrowerList();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }

    private void searchUser() {
        if (edCnic.getText().toString().length() < 15) {
            edCnic.setError("Enter Valid Cnic");
            return;
        } else {
//           serachBorrower(edCnic.getText().toString());

        }
    }

    private void setLoansRecyclerView(final GetBorrowerList body) {


    }

//    void serachBorrower(String cnic) {
//        final AlertDialog dialog = AlertUtils.showLoader(context);
//
//        if (dialog != null) {
//            dialog.show();
//        }
//
//        SearchBorrowerRequest searchBorrowerRequest = new SearchBorrowerRequest();
//        searchBorrowerRequest.setCnic(cnic);
//
//        EasyLoanBusiness business = new EasyLoanBusiness();
//        business.searchBorrower(searchBorrowerRequest, new ResponseCallBack<SearchBorrower>() {
//            @Override
//            public void onSuccess(SearchBorrower body) {
//
////                if (body.loaninfo.size() > 0 && body.loaninfo.get(0).status.equalsIgnoreCase("Pending")) {
////
////                    dialog.dismiss();
////                    BorrowerReceiptObj borrowerReceiptObj = new BorrowerReceiptObj();
////                    borrowerReceiptObj.borrowerid = body.loaninfo.get(0).borrowerid;
////                    borrowerReceiptObj.loanId = body.loaninfo.get(0).loanid;
////                    borrowerReceiptObj.type = body.user.accounttype;
////                    borrowerReceiptObj.amount = body.loaninfo.get(0).loanAmount;
////
////                    if (body.user.accounttype.equalsIgnoreCase("Mobile Wallet")) {
////                        context.userWalletFragment(borrowerReceiptObj, loanInfoRequest);
////                    } else if (body.user.accounttype.equalsIgnoreCase("Cheque")) {
////                        context.userChequeFragment(borrowerReceiptObj, loanInfoRequest);
////                    }
////
////
////                } else
//                if (body.loaninfo.size() > 0 && body.loaninfo.get(0).status.equalsIgnoreCase("disbursed")) {
//                    dialog.dismiss();
//                    BorrowerReceiptObj borrowerReceiptObj = new BorrowerReceiptObj();
//                    borrowerReceiptObj.borrowerid = body.loaninfo.get(0).borrowerid;
//                    borrowerReceiptObj.loanId = body.loaninfo.get(0).loanid;
////                    borrowerReceiptObj.type = body.borrowerList.get(position).;
//                    borrowerReceiptObj.amount = body.loaninfo.get(0).loanAmount;
//                    borrowerReceiptObj.name = body.user.name;
//                    borrowerReceiptObj.duedate = body.loaninfo.get(0).dueDate;
//                    borrowerReceiptObj.purpose = body.loaninfo.get(0).loanPurpose;
//                    borrowerReceiptObj.status = body.loaninfo.get(0).status;
//
//
//                    RecoveryInfoDialgoue pinSucessMessage = new RecoveryInfoDialgoue(context, borrowerReceiptObj);
//                    pinSucessMessage.show(context.getSupportFragmentManager(),
//                            PinSucessMessage.class.getName());
//                }
//
//
//                if (body.loaninfo.size() <= 0 && body.user.name == null) {
//                    dialog.dismiss();
//                    AddNewUserFragment popup = new AddNewUserFragment(context, edCnic.getText().toString(), body);
//                    popup.show(getChildFragmentManager(), AddNewUserFragment.class.getName());
//                } else if (body.loaninfo.size() <= 0 && body.user.name != null) {
//                    dialog.dismiss();
//                    BorrowerExistDialgoue popup = new BorrowerExistDialgoue(context, edCnic.getText().toString(), body);
//                    popup.show(getChildFragmentManager(), BorrowerExistDialgoue.class.getName());
//                }
//                dialog.dismiss();
//
//
//            }
//
//            @Override
//            public void onFailure(String message) {
//
//                dialog.dismiss();
//                AlertUtils.showAlert(getActivity(), message);
//            }
//        });
//    }

    void getBorrowerList() {


    }


}