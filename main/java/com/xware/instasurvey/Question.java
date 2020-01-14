package com.xware.instasurvey;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Question  {
    Integer id=0;
    Integer surveyId =null;
    Integer seqId =null;
    String question=null;
    ArrayList<String> answers= null;
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question1 = (Question) o;
        return Objects.equals(getQuestion(), question1.getQuestion()) &&
                Objects.equals(getId(), question1.getId()) &&
                Objects.equals(getSurveyId(), question1.getSurveyId()) &&
                Objects.equals(getAnswers(), question1.getAnswers());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getQuestion(), getId(), getSurveyId(), getAnswers());
    }
*/


       public Question() {
       }
       public Question(String s){
        this.question=s;
    }

       public Question(Integer id, Integer surveyId,Integer seqId,String question, String a1, String a2,String a3,String a4 ,String a5) {
        this.id = id;
        this.seqId=seqId;
        this.question = question;
        this.surveyId= surveyId;

        ArrayList<String> ala = new ArrayList<String>();
        ala.add(a1);
        ala.add(a2);
        ala.add(a3);
        ala.add(a4);
        ala.add(a5);
        this.setAnswers(ala);

    };

    public Integer getId(){
        return id;
    }
    public void setId(Integer pid){
        this.id=pid;
    }
    public Integer getSurveyId() {   return surveyId; }
    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }
    public Integer getSeqId(){
        return seqId;
    }
    public void setSeqId(Integer sid){
        this.id=sid;
    }

    public String getQuestion(){
      return this.question;
    };
    public void setQuestion(String q){

        this.question=q;
    };
   public  ArrayList<String> getAnswers(){


        return answers;
    };
    public void setAnswers(ArrayList<String> l){
           this.answers=l;
    };
}
