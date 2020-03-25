package com.xware.instasurvey;

public class Answer{
    int id ;
    int surveyId;
    int questionId;
    String answer;
    public int getSurveyId() {
        return this.surveyId;
    }

    public void setSurveyId(final int surveyId) {
        this.surveyId = surveyId;
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(final int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }



   public  Answer(int sid,int qid,String a){
        surveyId=sid;
        questionId=qid;
        answer =a;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public  Answer(int id, int sid, int qid, String a){
       this.id =id;
        surveyId=sid;
        questionId=qid;
        answer =a;
    }
    public static  Answer makeAnswer(Integer sid,Integer qid,String a){

        Answer res= new Answer(sid ,qid ,a);
        return res;
    }
}
