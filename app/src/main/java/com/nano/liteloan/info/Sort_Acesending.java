package com.nano.liteloan.info;


import com.nano.liteloan.info.businesscorrespondant.ApplicationOverDueList;

import java.util.Comparator;

public class Sort_Acesending implements Comparator<ApplicationOverDueList> {
    public int compare(ApplicationOverDueList a, ApplicationOverDueList b)
    {
        return (int) a.recoverydays - (int) b.recoverydays;
    }
}