package com.xware.instasurvey;

import java.util.List;

interface IQuestion {
    String Question=null;
    List<String> Answers= null;
    String getQuestion();
    void setQuestion(String q);
    List<String> getAnswers();
    void setAnswers(List<String> l);

}
