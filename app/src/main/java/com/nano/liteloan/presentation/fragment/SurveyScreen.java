package com.nano.liteloan.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nano.liteloan.R;
import com.nano.liteloan.business.impl.EasyLoanBusiness;
import com.nano.liteloan.delegate.ResponseCallBack;
import com.nano.liteloan.info.LoanApplyRequest;
import com.nano.liteloan.info.LoanApplyResponse;
import com.nano.liteloan.info.SurveyAnswer;
import com.nano.liteloan.info.SurveyAnswerResponce;
import com.nano.liteloan.info.SurveyQuestions;
import com.nano.liteloan.info.SurveysRequest;
import com.nano.liteloan.presentation.activity.MainActivity;
import com.nano.liteloan.presentation.adapters.SurveyAdapter;
import com.nano.liteloan.presentation.fragment.dialoguefragment.ApplicationSubmitSuccessDialog;
import com.nano.liteloan.utils.SurvayAdapterListener;
import com.nano.liteloan.utils.alerts.AlertUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Muhammad Umer on 04/06/2020.
 */
public class SurveyScreen extends Fragment {

    private RecyclerView recyclerView;
    private MainActivity context;
    private HashMap<Integer, String> map = new HashMap<Integer, String>();
    private LoanApplyRequest loanApplyRequest;
    private ImageView ivBack;
    int questionno = 1;

    private Button btClick;

    public SurveyScreen(MainActivity context, LoanApplyRequest loanApplyRequest) {
        this.context = context;
        this.loanApplyRequest = loanApplyRequest;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey_screen, container, false);

        ivBack = view.findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.homeFrag();
            }
        });
        btClick = view.findViewById(R.id.click);
        btClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (map.size() < questionno) {
                    AlertUtils.showAlert(context, "Please Select All options");
                } else {
                    postSurvey(map);
                }


            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        getSurvey();


        return view;
    }

    private void callsurvey(HashMap<Integer, String> map) {

    }

    public void getSurvey() {
        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();
        business.getSurvey(new ResponseCallBack<SurveyQuestions>() {
            @Override
            public void onSuccess(SurveyQuestions body) {
                if (dialog != null) {
                    dialog.dismiss();
                }



                questionno = body.questionnaire.size();
                setRecyclerView(body);


            }

            @Override
            public void onFailure(String message) {
                if (dialog != null) {
                    dialog.dismiss();
                }
                AlertUtils.showAlert(getActivity(), message);
            }
        });

    }

    private void setRecyclerView(SurveyQuestions body) {
        SurveyAdapter surveyAdapter = new SurveyAdapter(body, context);
        recyclerView.setAdapter(surveyAdapter);

        surveyAdapter.setOnItemClickListener(new SurvayAdapterListener() {
            @Override
            public void onItemClick(int position, String option) {
                map.put(position, option);
            }
        });
    }


    public void postSurvey(HashMap<Integer, String> map){


        SurveysRequest request = new SurveysRequest();
        List<SurveyAnswer> list = new ArrayList<>();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            SurveyAnswer answer = new SurveyAnswer();
            answer.setResult(entry.getValue());
            answer.setQuestionid(entry.getKey().toString());
            list.add(answer);


        }

        request.setAnswers(list);


        EasyLoanBusiness business = new EasyLoanBusiness();
        business.postSurvey(request, new ResponseCallBack<SurveyAnswerResponce>() {
            @Override
            public void onSuccess(SurveyAnswerResponce body) {

                callSubmitLoan(loanApplyRequest);


            }

            @Override
            public void onFailure(String message) {

                AlertUtils.showAlert(getActivity(), message);
            }
        });

    }

    private void callSubmitLoan(LoanApplyRequest loanApplyRequest) {

        final AlertDialog dialog = AlertUtils.showLoader(getActivity());

        EasyLoanBusiness business = new EasyLoanBusiness();


        business.applyForLoan(loanApplyRequest, new ResponseCallBack<LoanApplyResponse>() {
            @Override
            public void onSuccess(LoanApplyResponse body) {

                dialog.dismiss();

                ApplicationSubmitSuccessDialog dialog1 =
                        new ApplicationSubmitSuccessDialog(context);

                dialog1.show(getChildFragmentManager(),
                        ApplicationSubmitSuccessDialog.class.getName());
            }

            @Override
            public void onFailure(String message) {

                dialog.dismiss();

                AlertUtils.showAlert(getActivity(), message);
            }
        });
    }


}