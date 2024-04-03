package com.nano.liteloan.info;

import java.io.Serializable;
/**
 * Created by Muhammad Umer on 07/06/2020.
 * Modified by Muhammad Ahmad.
 */
public class BorrowerReceiptObj implements Serializable {

    public int borrowerid;
    public int loanId;
    public String type;
    public String amount;
    public String name;
    public String purpose;
    public String status;
    public String duedate;
}
