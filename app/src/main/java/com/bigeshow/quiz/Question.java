package com.bigeshow.quiz;

/**
 * Created by YinFei on 2016/12/18 0018.
 */

public class Question {
    private int mTextResId;

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    private boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public Question(int textResId, boolean answerTrue)
    {
        mTextResId=textResId;
        mAnswerTrue=answerTrue;
    }
}
