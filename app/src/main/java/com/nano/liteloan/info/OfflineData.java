package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OfflineData {
    @SerializedName("profile")
    public List<UserProfile> list = new ArrayList<>();
}
