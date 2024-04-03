package com.nano.liteloan.correspondant.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.ConfigurationRespondObject;
import com.nano.liteloan.info.LoanCategoryInfo;
import com.nano.liteloan.info.LoanCategoryItems;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.LoanCategoryAdapter;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Ahmad on 9/11/2023.
 */
public class LoanProductListFragment extends Fragment {

    private RotateLoading loading;
    private MainActivity context;
    private RecyclerView rvLoans;

    private BorrowerList borrower;
    private LoanCategoryInfo categoryInfo;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = (MainActivity) context;
    }

    public LoanProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan_product_list, container, false);

        loading = view.findViewById(R.id.rotateloading);

        rvLoans = view.findViewById(R.id.rc_loanss);
        rvLoans.setLayoutManager(new LinearLayoutManager(context));

        if (this.getArguments() != null
                && this.getArguments()
                .getSerializable("borrower_details") != null) {

            borrower = (BorrowerList) this.getArguments()
                    .getSerializable("borrower_details");
        }

        getLoanProducts();

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(view1 -> {
            context.onBackPressed();
        });

        return view;
    }

    private void getLoanProducts() {

        loading.setVisibility(View.VISIBLE);
        loading.start();

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getLoanCategories(new ResponseCallBack<ConfigurationRespondObject>() {
            @Override
            public void onSuccess(ConfigurationRespondObject body) {

                List<LoanCategoryItems> listItems = new ArrayList<>();
                loading.stop();
                loading.setVisibility(View.GONE);

                if (body.loanCategories != null) {
                    for (int i = 0; i < body.loanCategories.size(); i++) {

                        if (body.loanCategories.get(i).isactive == 1 &&
                                body.loanCategories.get(i).productInfoList.size() > 1) {
                            PreferenceUtils.getInstance().productPurpose(body.loanCategories.get(i).productInfoList);
                        }

                        LoanCategoryItems items = new LoanCategoryItems();
//                        if (body.loanCategories.get(i).id == Integer.parseInt(feePaidLoanId)) {
//                            loanCategoryInfo = body.loanCategories.get(i);
//
//                        }
                        items.id = body.loanCategories.get(i).id;
                        items.name = body.loanCategories.get(i).name;
                        items.amount = body.loanCategories.get(i).fee;
                        items.isactive = body.loanCategories.get(i).isactive;
                        listItems.add(items);
                    }
                }

                setLoansRecyclerView(body, 0);

            }

            @Override
            public void onFailure(String message) {

                loading.stop();
                loading.setVisibility(View.GONE);
                AlertUtils.showAlert(context, message);
            }
        });
    }

    private void setLoansRecyclerView(final ConfigurationRespondObject body, final int value) {

        LoanCategoryAdapter categoryAdapter = new LoanCategoryAdapter(context,
                body.loanCategories, value);

        rvLoans.setAdapter(categoryAdapter);

        categoryAdapter.setOnItemClickListener((view, position) -> {

            categoryInfo = body.loanCategories.get(position);
            categoryInfo.borrower = borrower;

            context.LoanDetailFragment(categoryInfo);

        });

    }
}