package com.foreseer.definethis.Model.API.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class Word {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("definitions")
    @Expose
    private List<Definition> definitions = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }
}
