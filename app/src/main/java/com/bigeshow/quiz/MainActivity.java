package com.bigeshow.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private Button mCheatBtn;
    private static final String TAG="QuizActivity";
    private static final String KEY_INDEX="index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private boolean mIsCheater;
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
        Log.d(TAG, "onCreate: called");
        setContentView(R.layout.activity_main);
        mTrueBtn=(Button) findViewById(R.id.true_button);
        mFalseBtn=(Button) findViewById(R.id.false_button);
        mQuestionTextView=(TextView) findViewById(R.id.question_text_view);
        mIsCheater=false;
        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX);
        }
        UpdateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+1)%mQuestions.length;
                UpdateQuestion();
                mIsCheater=false;
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
                mIsCheater=false;
            }
        });

        mPreviousBtn=(ImageButton)findViewById(R.id.previous_button);
        mPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex=(mCurrentIndex+mQuestions.length-1)%mQuestions.length;
                UpdateQuestion();
                mIsCheater=false;
            }
        });

        mCheatBtn=(Button) findViewById(R.id.cheat_button);
        mCheatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=CheatActivity.newIntent(MainActivity.this,mQuestions[mCurrentIndex].isAnswerTrue());
                startActivityForResult(i,REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: called");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    private void UpdateQuestion() {
        //Log.d(TAG, "UpdateQuestion: "+mCurrentIndex,new Exception());
        mQuestionTextView.setText(mQuestions[mCurrentIndex].getTextResId());
    }

    private void CheckAnswer(boolean userPressTrue)
    {
        boolean answer=mQuestions[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if(mIsCheater){
            messageResId=R.string.judgment_toast;
        }else{
            if (answer==userPressTrue){
                messageResId=R.string.correct_toast;
            }else{
                messageResId=R.string.incorrect_toast;
            }
        }

        Toast.makeText(MainActivity.this,messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK)
        {
            return;
        }
        if (requestCode==REQUEST_CODE_CHEAT){
            if(data==null){
                return;
            }
            mIsCheater=CheatActivity.wasAnswerShown(data);
        }
    }
}
