package com.bigeshow.quiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CheatActivity extends AppCompatActivity {
    private static final String  EXTRA_ANSWER_IS_TRUE =
            "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN =
            "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String KEY_INDEX="index";
    private boolean mAnswerIsTrue;
    private boolean mIsCheated;
    private TextView mAnswerTextView;
    private Button mShowBtn;
    private TextView mSDKVerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        if(savedInstanceState!=null){
            mIsCheated=savedInstanceState.getBoolean(KEY_INDEX,false);
        }
        mAnswerIsTrue=getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView=(TextView) findViewById(R.id.answerTextView);
        if(mIsCheated) {
            showAnswer();
        }
        mShowBtn=(Button) findViewById(R.id.showAnswerButton);
        mShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();
                mIsCheated=true;
                SetAnswerShownResult(true);
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP)
                {
                    int cx=mShowBtn.getWidth()/2;
                    int cy=mShowBtn.getHeight()/2;
                    float radius=mShowBtn.getWidth();
                    Animator animator= ViewAnimationUtils.createCircularReveal(mShowBtn,cx,cy,radius,0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowBtn.setVisibility(View.INVISIBLE);
                        }
                    });
                    animator.start();
                }
                else{
                    mShowBtn.setVisibility(View.INVISIBLE);
                }

            }
        });
        mSDKVerTextView=(TextView) findViewById(R.id.sdkverTextView);
        int ver=Build.VERSION.SDK_INT;
        mSDKVerTextView.setText(String.format(Locale.getDefault(),"API Level %d",ver));
    }

    private void showAnswer() {
        if(mAnswerIsTrue)
        {
            mAnswerTextView.setText(R.string.true_button);
        }else
        {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    private void SetAnswerShownResult(boolean isAnswerShown) {
        Intent data=new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }

    public static Intent newIntent(Context packageContext,boolean answerIsTrue){
        Intent i=new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_INDEX,mIsCheated);
    }
}
