package com.xware.instasurvey;



import java.util.ArrayList;
import java.util.List;

public class Question  {
    String question=null;
    Long id=0L;
    Long getId(){
        return id;
    }

    void setId(Long pid){
        this.id=pid;
    }
    ArrayList<String> answers= null;
    Question() {
    }
       public Question(Long id, String question, String a1, String a2,String a3,String a4 ,String a5) {
        this.id = id;
        this.question = question;
        ArrayList<String> ala = new ArrayList<String>();
        ala.add(a1);
        ala.add(a2);
        ala.add(a3);
        ala.add(a4);
        ala.add(a5);
        this.setAnswers(ala);

    };


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
