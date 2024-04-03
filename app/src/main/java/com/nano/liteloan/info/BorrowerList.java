package com.nano.liteloan.info;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.nano.liteloan.info.responce.UserAccountDetail;

import java.io.Serializable;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class BorrowerList implements Parcelable, Serializable {

    @SerializedName("borrower_id")
    public int borrowerid;

    @SerializedName("name")
    public String name;

    @SerializedName("parentage")
    public String parentage;

    @SerializedName("cnic")
    public String cnic;

    @SerializedName("phone_no")
    public String phoneno;

    @SerializedName("sanction_no")
    public String sanctionno;

    @SerializedName("dob")
    public String dob;

    @SerializedName("gender")
    public String gender;

    @SerializedName("branch_id")
    public int branchid;

    @SerializedName("account_type")
    public String accounttype;

    @SerializedName("is_active")
    public int isactive;

    @SerializedName("user_status")
    public String status;

    @SerializedName("user_details")
    public UserProfile userDetail;


    @SerializedName("approached_by")
    public String approachedby;


    @SerializedName("borrower_status")
    public String borrowerstatus;

    @SerializedName("bank_account")
    public BankDetailInfo bankDetails;

    @SerializedName("asset_name")
    public String assetName;

    @SerializedName("asset_price")
    public String assetPrice;

    @SerializedName("asset_market_value")
    public String assetMarketValue;

    @SerializedName("profile_image")
    public String profileImage;

    @SerializedName("wasil_share")
    public String wasilShare;

    @SerializedName("borrower_signature")
    public String borrowerSignature;

    @SerializedName("owner_signature")
    public String ownerSignature;

    @SerializedName("witness_signature")
    public String witnessSignature;

    public String agreementType;

    public UserAccountDetail bankDetail;

    public LoanApplyRequest loanApplyRequest;

    public BorrowerList() {

    }

    protected BorrowerList(Parcel in) {
        borrowerid = in.readInt();
        name = in.readString();
        parentage = in.readString();
        cnic = in.readString();
        phoneno = in.readString();
        sanctionno = in.readString();
        dob = in.readString();
        gender = in.readString();
        branchid = in.readInt();
        accounttype = in.readString();
        isactive = in.readInt();
        status = in.readString();
        approachedby = in.readString();
        borrowerstatus = in.readString();
        assetName = in.readString();
        assetPrice = in.readString();
        assetMarketValue = in.readString();
        profileImage = in.readString();
        wasilShare = in.readString();
        borrowerSignature = in.readString();
        ownerSignature = in.readString();
        witnessSignature = in.readString();
        agreementType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(borrowerid);
        dest.writeString(name);
        dest.writeString(parentage);
        dest.writeString(cnic);
        dest.writeString(phoneno);
        dest.writeString(sanctionno);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeInt(branchid);
        dest.writeString(accounttype);
        dest.writeInt(isactive);
        dest.writeString(status);
        dest.writeString(approachedby);
        dest.writeString(borrowerstatus);
        dest.writeString(assetName);
        dest.writeString(assetPrice);
        dest.writeString(assetMarketValue);
        dest.writeString(profileImage);
        dest.writeString(wasilShare);
        dest.writeString(borrowerSignature);
        dest.writeString(ownerSignature);
        dest.writeString(witnessSignature);
        dest.writeString(agreementType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BorrowerList> CREATOR = new Creator<BorrowerList>() {
        @Override
        public BorrowerList createFromParcel(Parcel in) {
            return new BorrowerList(in);
        }

        @Override
        public BorrowerList[] newArray(int size) {
            return new BorrowerList[size];
        }
    };
}
