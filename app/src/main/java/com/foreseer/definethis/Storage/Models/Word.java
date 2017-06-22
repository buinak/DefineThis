package com.foreseer.definethis.Storage.Models;

import com.orm.SugarRecord;

/**
 * Created by Konstantin "Foreseer" Buinak on 22.06.2017.
 */

public class Word extends SugarRecord<Word>{
    String word;
    String jsonDefinitions;

    public Word(){}

    public Word(String word, String jsonDefinitions) {
        this.word = word;
        this.jsonDefinitions = jsonDefinitions;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getJsonDefinitions() {
        return jsonDefinitions;
    }

    public void setJsonDefinitions(String jsonDefinitions) {
        this.jsonDefinitions = jsonDefinitions;
    }
}
