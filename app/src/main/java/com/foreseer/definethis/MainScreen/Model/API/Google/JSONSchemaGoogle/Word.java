package com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Word {

    private String jsonString;

    private String word;
    private List<String> phonetic;
    private List<Definition> definitions;

    public Word(String jsonString, String word, List<String> phonetic, List<Definition> definitions) {
        this.jsonString = jsonString;
        this.word = word;
        this.phonetic = phonetic;
        this.definitions = definitions;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(List<String> phonetic) {
        this.phonetic = phonetic;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }
}