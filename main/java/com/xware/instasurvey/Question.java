package com.xware.instasurvey;



import java.util.ArrayList;
import java.util.List;

public class Question  {
    String question=null;
    int id=0;
    int getId(){
        return id;
    }
    void setId(int pid){
        this.id=pid;
    }
    ArrayList<String> answers= null;
    Question() {
    }
    Question(String s){
        this.question=s;
    }
    String getQuestion(){
      return this.question;
    };
    void setQuestion(String q){
             this.question=q;
    };
    ArrayList<String> getAnswers(){
         return answers;
    };
    void setAnswers(ArrayList<String> l){
           this.answers=l;
    };
}
