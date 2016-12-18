package com.bigeshow.quiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueBtn;
    private Button mFalseBtn;
    private ImageButton mNextBtn;
    private ImageButton mPreviousBtn;
    private TextView mQuestionTextView;
    private Question[] mQuestions=new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTrueBtn=(Button) findViewById(R.id.true_button);
        mFalseBtn=(Button) findViewById(R.id.false_button);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        UpdateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestions.length;
                UpdateQuestion();
            }
        });
        mTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(true);
            }
        });

        mFalseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckAnswer(false);
            }
        });

        mNextBtn=(ImageButton) findViewById(R.id.next_button);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestions.length;
                UpdateQuestion();
            }
        });

        mPreviousBtn=(ImageButton)findViewById(R.id.previous_button);
        mPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+mQuestions.length-1)%mQuestions.length;
                UpdateQuestion();
            }
        });
    }

    private void UpdateQuestion() {
        mQuestionTextView.setText(mQuestions[mCurrentIndex].getTextResId());
    }

    private void CheckAnswer(boolean userPressTrue)
    {
        boolean answer=mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (answer==userPressTrue){
            messageResId=R.string.correct_toast;
        }else{
            messageResId=R.string.incorrect_toast;
        }
        Toast.makeText(MainActivity.this,messageResId,Toast.LENGTH_SHORT).show();
    }
}
