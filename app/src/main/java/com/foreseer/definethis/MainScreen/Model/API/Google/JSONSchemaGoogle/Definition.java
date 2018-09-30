package com.foreseer.definethis.MainScreen.Model.API.Google.JSONSchemaGoogle;

import android.os.Parcel;
import android.os.Parcelable;

import com.foreseer.definethis.Storage.Models.Word;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Definition implements Parcelable {
    private String partOfSpeech;
    private String definition;
    private List<String> synonyms = null;
    private String example;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(partOfSpeech);
        parcel.writeString(definition);
        parcel.writeList(synonyms);
        parcel.writeString(example);
    }


    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Definition> CREATOR = new Parcelable.Creator<Definition>() {
        @Override
        public Definition createFromParcel(Parcel in) {
            //doesn't work, fix
            return new Definition();
        }

        @Override
        public Definition[] newArray(int size) {
            return new Definition[size];
        }
    };
}
