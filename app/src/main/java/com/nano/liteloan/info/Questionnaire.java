package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class Questionnaire {

    @SerializedName("id")
    public int id;

    @SerializedName("question")
    public String question;

    @SerializedName("mcqs")
    public List<String> mcqs;
}
