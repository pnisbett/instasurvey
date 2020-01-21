package com.xware.instasurvey;

public class Answer{
    int surveyId;
    int questionId;
    String answer;

    Answer(int sid,int qid,String a){
        surveyId=sid;
        questionId=qid;
        answer =a;
    }
    public static  Answer makeAnswer(Integer sid,Integer qid,String a){

        Answer res= new Answer(sid ,qid ,a);
        return res;
    }
}
