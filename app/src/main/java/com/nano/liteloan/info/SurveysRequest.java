package com.nano.liteloan.info;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by Muhammad Umer on 12/01/2021.
 */
public class SurveysRequest {

    @SerializedName("questions")
    List<SurveyAnswer> answers;

    public List<SurveyAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SurveyAnswer> answers) {
        this.answers = answers;
    }
}
