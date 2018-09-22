package com.foreseer.definethis.Storage;

import android.util.Log;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Storage.Models.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class StorageHandler {

    public static void save(String word, List<Definition> definitions){
        if (Word.find(Word.class, "word = ?", word).size() != 0){
            System.out.println();
            return;
        }
        try {
            String jsonDefinitions = convertDefinitionsToJSON(definitions);
            Word dbWord = new Word(word, jsonDefinitions, new Date());
            dbWord.save();
        } catch (Exception e){
            Log.d("EXCEPTION STORAGE", e.getMessage());
        }
    }

    public static List<Word> getAllWords(){
        return Word.listAll(Word.class);
    }

    private static String convertDefinitionsToJSON(List<Definition> definitionList) throws JSONException {
        JSONObject jsonDefinitions = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for (Definition definition : definitionList) {
            JSONObject jsonDefinition = new JSONObject();
            jsonDefinition.put("definition", definition.getDefinition());
            if (definition.getPartOfSpeech() != null) {
                jsonDefinition.put("partOfSpeech", definition.getPartOfSpeech());
            } else {
                jsonDefinition.put("partOfSpeech", "unknown");
            }
            jsonArray.put(jsonDefinition);
        }
        jsonDefinitions.put("definitions", jsonArray);
        return jsonDefinitions.toString();
    }

    public static List<Definition> convertJSONToDefinitions(String jsonString){
        List<Definition> definitions = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray array = jsonObject.getJSONArray("definitions");
            for (int i = 0; i < array.length(); i++) {
                JSONObject definition = array.getJSONObject(i);
                String definitionString = definition.getString("definition");
                String partOfSpeechString = definition.getString("partOfSpeech");
                definitions.add(new Definition(definitionString, partOfSpeechString));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return definitions;
    }

    public static boolean wasWordPreviouslyRequested(String word){
        if (Word.find(Word.class, "word = ?", word).size() == 0){
            return false;
        }
        return true;
    }

    public static List<Definition> getDefinitions(String word){
        if (!wasWordPreviouslyRequested(word)){
            return null;
        }

        Word savedWord = Word.find(Word.class, "word = ?", word).get(0);
        return convertJSONToDefinitions(savedWord.getJsonDefinitions());
    }

    public static void resetAllHistory(){
        Word.deleteAll(Word.class);
    }
}
