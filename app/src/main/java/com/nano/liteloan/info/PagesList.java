package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Umer on 10/08/2020.
 */
public class PagesList {

    @SerializedName("pages_list")
    private List<AboutUs> pageList;

    public List<AboutUs> getPageList() {
        return pageList;
    }

    public void setPageList(List<AboutUs> pageList) {
        this.pageList = pageList;
    }
}
