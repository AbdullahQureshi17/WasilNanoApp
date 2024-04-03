package com.nano.liteloan.presentation.fragment.profile;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.UserBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.ProfileCreateResponseInfo;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.utils.alerts.AlertUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class UserProfileFrag extends Fragment {


    private MainActivity context;
    private ProgressDialog dialog;
    private TextView tvName, tvAddress, tvcnic, tvProfession, tvIncome,
            tvEmail, tvPhone, tvAccountno;
    private TextView tvScore;
    private ProgressBar progressBar;
    private ImageView profileImage, ivBack;

    public UserProfileFrag(MainActivity context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        dialog = new ProgressDialog(context);

        tvName = view.findViewById(R.id.tv_name);
        profileImage = view.findViewById(R.id.imageView);

        tvEmail = view.findViewById(R.id.email);

        tvcnic = view.findViewById(R.id.cnic);
        tvAccountno = view.findViewById(R.id.accountinfo);
        tvIncome = view.findViewById(R.id.monthlyincome);
        tvPhone = view.findViewById(R.id.phoneno);
        tvProfession = view.findViewById(R.id.profession);

        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
            }
        });

        tvAddress = view.findViewById(R.id.address);

        tvScore = view.findViewById(R.id.score);
        progressBar = view.findViewById(R.id.progressBar);

        getProfile();

        return view;


    }

    private void getProfile() {

        dialog.show();
        UserBusiness userBusiness = new UserBusiness();
        userBusiness.getUserProfile(new ResponseCallBack<ProfileCreateResponseInfo>() {
            @Override
            public void onSuccess(ProfileCreateResponseInfo body) {
                if (body.userDate != null) {

                    if (body.userDate.name != null && !body.userDate.currentAddress.equals("")) {
                        tvName.setText(body.userDate.name);
                    }
                    if (body.userDate.city != null && !body.userDate.city.equals("")) {
                        tvAddress.setText(body.userDate.city);
                    }
                    if (body.userDate.email != null && !body.userDate.email.equals("")) {
                        tvEmail.setText(body.userDate.email);
                    }
                    if (body.userDate.phone != null && !body.userDate.phone.equals("")) {
                        tvPhone.setText(body.userDate.phone);
                    }
                    if (body.userDate.cnic != null && !body.userDate.cnic.equals("")) {
                        tvcnic.setText(body.userDate.cnic);
                    }
                    if (body.userDate.accountNumber != null && !body.userDate.accountNumber.equals("")) {
                        tvAccountno.setText(body.userDate.accountNumber);
                    }
                    if (body.userDate.sourceOfEarning != null && !body.userDate.sourceOfEarning.equals("")) {
                        tvProfession.setText(body.userDate.sourceOfEarning);
                    }
                    if (body.userDate.monthlyIncome != null && !body.userDate.monthlyIncome.equals("")) {
                        tvIncome.setText(body.userDate.monthlyIncome);
                    }

                    if (body.userDate.getScore() != null) {
                        tvScore.setText(body.userDate.getScore());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progressBar.setProgress(Integer.parseInt(body.userDate.getScore()), true);
                        } else {
                            progressBar.setProgress(Integer.parseInt(body.userDate.getScore()));
                        }
                    }

                    if (body.userDate.image != null && !body.userDate.image.isEmpty()) {
                        Picasso.get()
                                .load(body.userDate.image)
                                .into(profileImage);

                    }
                    dialog.dismiss();
                }
            }
            @Override
            public void onFailure(String message) {
                dialog.dismiss();
                AlertUtils.showAlert(context, message);
            }
        });
    }
}