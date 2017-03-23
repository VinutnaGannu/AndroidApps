package com.example.vinutna.trivia;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by vinutna on 09-02-2017.
 */

public class Questions {
    String question,imageUrl;
    ArrayList<String> choices;
    String answer;
    String id;
    String givenAnswer;

    public Questions() {
    }

    public Questions(String question, String imageUrl, ArrayList<String> choices, String answer, String id, String givenAnswer) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.choices = choices;
        this.answer = answer;
        this.id = id;
        this.givenAnswer = givenAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    static public Questions createQuestion(JSONObject js) throws JSONException {
        Questions q=new Questions();
        q.setId(js.getString("id"));
        q.setQuestion(js.getString("text"));
        JSONObject c=js.getJSONObject("choices");
        JSONArray choice=c.getJSONArray("choice");
        ArrayList<String> choices=new ArrayList<String>();
        for(int i=0;i<choice.length();i++){
            choices.add(choice.getString(i));
        }

        q.setChoices(choices);
        q.setAnswer(c.getString("answer"));
        if(js.has("image")) {
            q.setImageUrl(js.getString("image"));
        }
        else{
            q.setImageUrl("");
        }
        return q;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "question='" + question + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", choices=" + choices +
                ", answer='" + answer + '\'' +
                ", id='" + id + '\'' +
                ", givenAnswer='" + givenAnswer + '\'' +
                '}';
    }

    public static Comparator<Questions> sortByAnswers=new Comparator<Questions>() {
        @Override
        public int compare(Questions q1, Questions q2) {
            if(!q1.getAnswer().equals(q1.getGivenAnswer()))
                return -1;
            else
                return 0;
        }
    };
}
