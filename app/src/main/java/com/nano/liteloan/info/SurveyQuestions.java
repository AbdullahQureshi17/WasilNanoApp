package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class SurveyQuestions {
    @SerializedName("questionnaire")

    public List<Questionnaire> questionnaire;
}
