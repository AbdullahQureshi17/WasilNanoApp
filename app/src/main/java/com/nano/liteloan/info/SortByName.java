package com.nano.liteloan.info;

import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;

import java.util.Comparator;

public class SortByName implements Comparator<ApplicationOverDueList> {
    public int compare(ApplicationOverDueList a, ApplicationOverDueList b) {
        return a.getFullname().compareTo(b.getFullname());
    }
}