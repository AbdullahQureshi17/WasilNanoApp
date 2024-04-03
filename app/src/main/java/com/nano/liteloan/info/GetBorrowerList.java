package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;
import com.nano.liteloan.info.businesscorrespondant.PotentialBorrowerResponseList;

import java.util.List;

/**
 * Created by Muhammad Umer on 09/07/2021.
 */
public class GetBorrowerList {
    @SerializedName("my_borrowers_list")
    public List<BorrowerList> borrowerList;

    @SerializedName("count")
    public int count;

    @SerializedName("response_list")
    public List<String> responselist;

}
