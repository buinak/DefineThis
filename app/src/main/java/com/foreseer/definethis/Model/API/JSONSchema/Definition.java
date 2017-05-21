package com.foreseer.definethis.Model.API.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Konstantin "Foreseer" Buinak on 21.05.2017.
 */

public class Definition {

    @SerializedName("definition")
    @Expose
    private String definition;
    @SerializedName("partOfSpeech")
    @Expose
    private Object partOfSpeech;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Object getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(Object partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }
}
