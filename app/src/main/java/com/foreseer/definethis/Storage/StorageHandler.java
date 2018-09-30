package com.foreseer.definethis.Storage;

import android.util.Log;

import com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Definition;
import com.foreseer.definethis.MainScreen.Model.API.Google.WordDeserializer;
import com.foreseer.definethis.Storage.Models.Word;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static void save(com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Word word){
        if (Word.find(Word.class, "word = ?", word.getWord()).size() != 0){
            System.out.println();
            return;
        }
        try {
            Word dbWord = new Word(word.getWord(), word.getJsonString(), new Date());
            dbWord.save();
        } catch (Exception e){
            Log.d("EXCEPTION STORAGE", e.getMessage());
        }
    }

    public static List<Word> getAllWords(){
        List<Word> words = Word.listAll(Word.class);
        return Word.listAll(Word.class);
    }

    public static boolean wasWordPreviouslyRequested(String word){
        if (Word.find(Word.class, "word = ?", word).size() == 0){
            return false;
        }
        return true;
    }

    public static com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Word getWord(String word){
        if (!wasWordPreviouslyRequested(word)){
            return null;
        }

        Word savedWord = Word.find(Word.class, "word = ?", word).get(0);
        JsonObject object = new JsonParser().parse(savedWord.getJsonWord()).getAsJsonObject();
        com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle.Word result =
                new WordDeserializer().deserialize(object, null, null);
        return result;
    }

    public static void resetAllHistory(){
        Word.deleteAll(Word.class);
    }
}
