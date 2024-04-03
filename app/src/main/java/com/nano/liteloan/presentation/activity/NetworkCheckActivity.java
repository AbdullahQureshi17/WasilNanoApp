package com.nano.liteloan.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nano.liteloan.R;
import com.nano.liteloan.business.EasyLoanService;
import com.nano.liteloan.info.GetProductResponce;
import com.nano.liteloan.info.responce.ResponceObject;
import com.nano.liteloan.network.ApiClient;
import com.nano.liteloan.utils.AppConstantsUtils;
import com.nano.liteloan.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCheckActivity extends AppCompatActivity {

    private TextView tvIp;
    private String errorText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_check);
        tvIp = findViewById(R.id.tv_ipAddress);

        // checkNetwork(AppConstantsUtils.PTCL_IP, AppConstantsUtils.BASE_URL);
        // checkNetwork(AppConstantsUtils.NEW_IP, AppConstantsUtils.BASE_URL);
        checkNetwork(AppConstantsUtils.LIVE_IP, AppConstantsUtils.BASE_URL);
        // checkNetwork(AppConstantsUtils.LOCAL_IP, AppConstantsUtils.BASE_URL);

    }

    private void checkNetwork(String ipAddress, String baseUrl) {

        EasyLoanService loanService = ApiClient.getApiClient(ipAddress + baseUrl).create(EasyLoanService.class);

        loanService.getProducts().enqueue(new Callback<ResponceObject<GetProductResponce>>() {
            @Override
            public void onResponse(Call<ResponceObject<GetProductResponce>> call,
                                   Response<ResponceObject<GetProductResponce>> response) {

                if (response.isSuccessful()) {

//                    Toast.makeText(NetworkCheckActivity.this, ipAddress,
//                            Toast.LENGTH_SHORT).show();
                    PreferenceUtils.getInstance().setIPAddress(ipAddress);
                    if (response.body() != null && response.body().getMeta().getCode() == 200) {
                        if (response.body().getData().loanCategories.size() > 0) {
                            PreferenceUtils.getInstance().productPurpose(response.body().getData()
                                    .loanCategories.get(0).productInfoList);
                        }

                    }
                    startActivity(new Intent(getApplicationContext(), SplashActivity.class));
                    overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<ResponceObject<GetProductResponce>> call, Throwable t) {

                errorText = errorText + "\n\n" + t.getMessage();
                tvIp.setText(errorText);
            }
        });
    }
}