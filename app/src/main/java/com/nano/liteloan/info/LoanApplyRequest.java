package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Muhammad Ahmad on 07/10/2020.
 */
public class LoanApplyRequest {

    @SerializedName("application_id")
    public String applicationId;

    @SerializedName("category_id")
    public Integer cateId;

    @SerializedName("correspondent_id")
    public String correspondantid;

    @SerializedName("product_id")
    public String productId;

    @SerializedName("amount")
    public String amount;

    @SerializedName("other_purpose")
    public String purpose;

    @SerializedName("income")
    public String income;

    @SerializedName("source_of_earning")
    public String incomeSource;
    @SerializedName("future_expected_income")
    public String futureIncome;

    @SerializedName("loan_amount")
    public String loanamount;

    @SerializedName("active_loan")
    public String activeLoan;

    @SerializedName("monthly_repayment")
    public String monthlyRepayment;

    @SerializedName("borrower_id")
    public Integer borrowerId;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("asset_name")
    public String assetName;

    @SerializedName("asset_price")
    public String assetPrice;

    @SerializedName("asset_market_value")
    public String asstMarketValue;

    @SerializedName("asset_pic")
    public String assetPic;

    @SerializedName("pic_cnic_back")
    public String cnicBackPic;

    @SerializedName("pic_cnic_front")
    public String cnicFrontPic;

    @SerializedName("borrower_pic")
    public String profilePic;

    @SerializedName("musharka_pic")
    public String mussharkaPic;

    @SerializedName("ijarah_pic")
    public String ijarahPic;

    public String bankName;
    public String accountNumber;

    public String onlineProfile;
    public String onlineAsset;
    public String onlineCNICBack;
    public String onLineFront;
    public String onlineMusharka;
    public String onlineIjarha;

    public String rentAmount;

    public String rentPercentage;

    public String intervalTime;

    @SerializedName("musharka_datetime")
    public String dateTime;
}
