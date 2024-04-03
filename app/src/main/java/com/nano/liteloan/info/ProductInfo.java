package com.nano.liteloan.info;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Muhammad Umer on 07/06/2020.
 */
public class ProductInfo implements Serializable, Parcelable {

    @SerializedName("id")
    public String id;

    @SerializedName("category_id")
    public Integer categoryId;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("logo")
    public String logo;

    protected ProductInfo(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            categoryId = null;
        } else {
            categoryId = in.readInt();
        }
        name = in.readString();
        description = in.readString();
        logo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (categoryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(categoryId);
        }
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(logo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel in) {
            return new ProductInfo(in);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };
}
