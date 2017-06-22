package com.foreseer.definethis.Storage;

import android.util.Log;

import com.foreseer.definethis.MainScreen.Model.API.JSONSchema.Definition;
import com.foreseer.definethis.Storage.Models.Word;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            Word dbWord = new Word(word, jsonDefinitions);
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
            jsonDefinition.put("partOfSpeech", definition.getDefinition());
            jsonArray.put(jsonDefinition);
        }
        jsonDefinitions.put("definitions", jsonArray);
        return jsonDefinitions.toString();
    }
}
