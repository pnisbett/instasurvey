package com.xware.instasurvey;



import java.util.List;

public class Question  {
    String question=null;
    List<String> answers= null;
    String getQuestion(){
      return this.question;
    };
    void setQuestion(String q){
             this.question=q;
    };
    List<String> getAnswers(){
         return answers;
    };
    void setAnswers(List<String> l){
           this.answers=l;
    };
}
