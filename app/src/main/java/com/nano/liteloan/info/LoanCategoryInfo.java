package com.nano.liteloan.info;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class LoanCategoryInfo implements Serializable, Parcelable {

    @SerializedName("id")
    public Integer id;

    @SerializedName("is_active")
    public Integer isactive;


    @SerializedName("name")
    public String name;

    @SerializedName("fee")
    public String fee;

    @SerializedName("description")
    public String description;

    @SerializedName("logo")
    public String logo;

    @SerializedName("short description")
    public String shortDescription;

    @SerializedName("disclaimer")
    public String disclaimer;

    @SerializedName("loan_limit")
    public Integer loanLimit;

    @SerializedName("installment month")
    public Integer installmentMonth;

    @SerializedName("loan_duration")
    public String loanDuration;

    @SerializedName("rent_percentage")
    public String rentalPercentage;

    @SerializedName("color_code")
    public String colorCode;

    @SerializedName("products")
    public List<ProductInfo> productInfoList;

    public BorrowerList borrower;

    public LoanCategoryInfo() {

    }

    protected LoanCategoryInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            isactive = null;
        } else {
            isactive = in.readInt();
        }
        name = in.readString();
        fee = in.readString();
        description = in.readString();
        logo = in.readString();
        shortDescription = in.readString();
        disclaimer = in.readString();
        if (in.readByte() == 0) {
            loanLimit = null;
        } else {
            loanLimit = in.readInt();
        }
        if (in.readByte() == 0) {
            installmentMonth = null;
        } else {
            installmentMonth = in.readInt();
        }
        loanDuration = in.readString();
        rentalPercentage = in.readString();
        colorCode = in.readString();
        borrower = in.readParcelable(BorrowerList.class.getClassLoader());
    }

    public static final Creator<LoanCategoryInfo> CREATOR = new Creator<LoanCategoryInfo>() {
        @Override
        public LoanCategoryInfo createFromParcel(Parcel in) {
            return new LoanCategoryInfo(in);
        }

        @Override
        public LoanCategoryInfo[] newArray(int size) {
            return new LoanCategoryInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (isactive == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(isactive);
        }
        dest.writeString(name);
        dest.writeString(fee);
        dest.writeString(description);
        dest.writeString(logo);
        dest.writeString(shortDescription);
        dest.writeString(disclaimer);
        if (loanLimit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(loanLimit);
        }
        if (installmentMonth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(installmentMonth);
        }
        dest.writeString(loanDuration);
        dest.writeString(rentalPercentage);
        dest.writeString(colorCode);
        dest.writeParcelable(borrower, flags);
    }
}
