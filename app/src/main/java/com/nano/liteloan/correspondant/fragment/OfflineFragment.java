package com.nano.liteloan.correspondant.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nano.liteloan.R;
import com.nano.liteloan.business.BusinessCorrespondantService;
import com.nano.liteloan.business.impl.BusinessCorrespondant;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.correspondant.correspondantadapter.ActiveLoanListAdapter;
import com.nano.liteloan.correspondant.correspondantadapter.OfflineInterestedList;
import com.nano.liteloan.correspondant.correspondantdialoguefragment.BorrowerInterestedFragment;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.BorrowerList;
import com.nano.liteloan.info.GetBorrowerList;
import com.nano.liteloan.info.OfflineData;
import com.nano.liteloan.info.OfflineResponce;
import com.nano.liteloan.info.User;
import com.nano.liteloan.info.UserProfile;
import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;
import com.nano.liteloan.info.businesscorrespondant.CorrespondantLoginAsRequest;
import com.nano.liteloan.info.businesscorrespondant.MyApplicants;
import com.nano.liteloan.info.responce.RecoveryPayResponse;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.OfflineOnItemClick;
import com.nano.liteloan.utils.OnItemClickListener;
import com.nano.liteloan.utils.PreferenceUtils;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OfflineFragment extends Fragment implements View.OnClickListener {


    private HashMap<String, BorrowerList> getBorrowerList;
    private MainActivity context;
    private RecyclerView recyclerView;
    private TextView tvNoRecord , tvCount;
    private RelativeLayout rlSyncdata;


    public OfflineFragment(MainActivity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offline, container, false);

        rlSyncdata = view.findViewById(R.id.syncdata);
        rlSyncdata.setOnClickListener(this);
        tvCount = view.findViewById(R.id.count);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        tvNoRecord = view.findViewById(R.id.norecord);

        if(PreferenceUtils.getInstance().getBorrowerList()!= null){
            getBorrowerList = PreferenceUtils.getInstance().getBorrowerList();
            tvCount.setText(getBorrowerList.size()+" Offline Applicant");
            if(getBorrowerList == null || getBorrowerList.size() <= 0){
                tvNoRecord.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                tvNoRecord.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                setLoansRecyclerView(getBorrowerList);
            }
        }



        return view;
    }

    private void setLoansRecyclerView(HashMap<String, BorrowerList> myApplicants) {

        ArrayList<BorrowerList> list = new ArrayList<>();
        for (Map.Entry<String, BorrowerList> entry : myApplicants.entrySet()) {
            String key = entry.getKey();
            list.add( entry.getValue());

        }

        OfflineInterestedList myLoanAdapter = new OfflineInterestedList(context , list);
        recyclerView.setAdapter(myLoanAdapter);

        myLoanAdapter.setOnItemClickListener(new OfflineOnItemClick() {
            @Override
            public void onItemClick(View view, int position, String cnic) {
                UserProfile userProfile = new UserProfile();
                if(PreferenceUtils.getInstance().getUserProfileList()!= null){
                    HashMap<String, UserProfile> map = new HashMap<>();
                    map = PreferenceUtils.getInstance().getUserProfileList();
                    userProfile = map.get(cnic);
                }
                context.offlineProfileMainFrag(userProfile , cnic);
//                context.borrowerRevcoveryFragment(applicationlist.get(position));

            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.syncdata:
                syncData();
            break;
        }
    }

    public void syncData(){
        if(PreferenceUtils.getInstance().getUserProfileList() != null){
            HashMap<String, UserProfile> map = new HashMap<>();
            map = PreferenceUtils.getInstance().getUserProfileList();
            ArrayList<UserProfile> list = new ArrayList<>();
            HashMap<String , BorrowerList> borrowerListHashMap = new HashMap<>();
            borrowerListHashMap = PreferenceUtils.getInstance().getBorrowerList();
            for (Map.Entry<String, UserProfile> entry : map.entrySet()) {
                String key = entry.getKey();
                list.add( entry.getValue());
                borrowerListHashMap.remove(key);
            }
            PreferenceUtils.getInstance().addBorrowerList(borrowerListHashMap);

            offlineSyncData(list);

        } else {
            AlertUtils.showAlert(context, "No data available to sync.");
        }
    }

    private void offlineSyncData(ArrayList<UserProfile> list) {

        OfflineData offlineData = new OfflineData();
        offlineData.list = list;
        final androidx.appcompat.app.AlertDialog dialog = AlertUtils.showLoader(context);

        if (dialog != null) {
            dialog.show();
        }
        BusinessCorrespondant userBusiness = new BusinessCorrespondant();
        userBusiness.offlineBorroweradd(offlineData ,new ResponseCallBack<OfflineResponce>() {
            @Override
            public void onSuccess(OfflineResponce body) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(context, "Data Synced Successfully.");
            }

            @Override
            public void onFailure(String message) {

                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(context, message);
            }
        });
    }
}