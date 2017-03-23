package com.example.vinutna.trivia;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by vinutna on 09-02-2017.
 */

public class QuestionsUtil {
    static public class QuestionsJSONParser{
        static ArrayList<Questions> parseQuestions(String input) throws JSONException {
            ArrayList<Questions> questionsList=new ArrayList<Questions>();
            JSONObject root=new JSONObject(input);
            JSONArray questionsArray=root.getJSONArray("questions");
            for(int i=0;i<questionsArray.length();i++){
                JSONObject questionJSONObject=questionsArray.getJSONObject(i);
                Questions question=Questions.createQuestion(questionJSONObject);
                questionsList.add(question);
            }
            return questionsList;
        }
    }
}
